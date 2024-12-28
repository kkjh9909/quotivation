package com.example.quotivation.service.log;

import com.example.quotivation.dto.log.request.LogRequest;
import com.example.quotivation.exception.CommonHttpException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogSendService {

    private final RestTemplate restTemplate;
    private final HttpServletRequest request;

    @Value("${mongo.log.uri}")
    private String LOG_SERVER_URL;

    public void sendLog(ProceedingJoinPoint joinPoint) {
        LogRequest log = createLog(joinPoint, null);
        restTemplate.postForObject(LOG_SERVER_URL, log, Void.class);
    }

    public void sendLog(ProceedingJoinPoint joinPoint, Exception e) {
        LogRequest log = createLog(joinPoint, e);
        restTemplate.postForObject(LOG_SERVER_URL, log, Void.class);
    }

    public LogRequest createLog(ProceedingJoinPoint joinPoint, Exception e) {
        String ip = getClientIp();
        String path = getRequestUrl();
        String method = request.getMethod();
        Map<String, String> parameters = extractParameters(joinPoint);

        int status = (e instanceof CommonHttpException)
                ? ((CommonHttpException) e).getHttpStatus().value()
                : (e != null ? 500 : 200);

        String message = e != null ? e.getMessage() : "Success";

        return new LogRequest(
                ip,
                path,
                status,
                method,
                message,
                LocalDateTime.now(),
                parameters
        );
    }

    private Map<String, String> extractParameters(ProceedingJoinPoint joinPoint) {
        Map<String, String> parameters = new HashMap<>();

        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method methodObject = signature.getMethod();
        Parameter[] methodParameters = methodObject.getParameters();

        for (int i = 0; i < args.length; i++) {
            Parameter parameter = methodParameters[i];

            // @RequestBody 처리
            if (parameter.isAnnotationPresent(RequestBody.class))
                extractRequestBody(parameters, args[i]);

            // @RequestParam 처리
            if (parameter.isAnnotationPresent(RequestParam.class))
                extractRequestParam(parameters, parameter, args[i]);
        }

        return parameters;
    }

    private void extractRequestBody(Map<String, String> parameters, Object requestBody) {
        if (requestBody != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> bodyMap = objectMapper.convertValue(requestBody, new TypeReference<Map<String, Object>>() {});
            for (Map.Entry<String, Object> entry : bodyMap.entrySet()) {
                parameters.put(entry.getKey(), entry.getValue() != null ? entry.getValue().toString() : "null");
            }
        }
    }

    private void extractRequestParam(Map<String, String> parameters, Parameter parameter, Object paramValue) {
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        String paramName = requestParam.value().isEmpty() ? parameter.getName() : requestParam.value();
        parameters.put(paramName, paramValue != null ? paramValue.toString() : "null");
    }

    private String getClientIp() {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    private String getRequestUrl() {
        return request.getRequestURI();
    }
}
