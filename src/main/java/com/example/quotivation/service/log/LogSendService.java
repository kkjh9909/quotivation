package com.example.quotivation.service.log;

import com.example.quotivation.dto.log.request.LogRequest;
import com.example.quotivation.exception.CommonHttpException;
import com.example.quotivation.exception.ExceptionMessages;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogSendService {

    private final ObjectMapper objectMapper;
    private final WebClient webClient;
    private final RestTemplate restTemplate;
    private final HttpServletRequest request;

    @Value("${mongo.log.uri}")
    private String LOG_SERVER_URL;

    private String createLogMutation;
    @PostConstruct
    private void init() {
        try {
            ClassPathResource resource = new ClassPathResource("graphql/mutation/createLog.graphql");
            this.createLogMutation = new String(resource.getInputStream().readAllBytes());
        } catch(IOException e) {
            log.error("Failed to load GraphQL Mutation file");
        }
    }

    public void sendLog(ProceedingJoinPoint joinPoint) {
        try {
            Map<String, Object> requestBody = Map.of(
                    "query", createLogMutation,
                    "variables", Map.of(
                            "input", objectMapper.convertValue(createLog(joinPoint, null), Map.class)
                    )
            );

            webClient.post()
                    .uri(LOG_SERVER_URL)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .doOnError(error -> log.error("❌ Failed to send log to GraphQL server: {}", error.getMessage()))
                    .subscribe();
        } catch (Exception e) {
            log.info(ExceptionMessages.LOG_SERVER_NOT_WORKING, e.getMessage());
        }
    }

    public void sendLog(ProceedingJoinPoint joinPoint, Exception e) {
        try {
            Map<String, Object> requestBody = Map.of(
                    "query", createLogMutation,
                    "variables", Map.of(
                            "input", objectMapper.convertValue(createLog(joinPoint, e), Map.class)
                    )
            );

            webClient.post()
                    .uri(LOG_SERVER_URL)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .doOnError(error -> log.error("❌ Failed to send log to GraphQL server: {}", error.getMessage()))
                    .subscribe();
        } catch (Exception error) {
            log.info(ExceptionMessages.LOG_SERVER_NOT_WORKING, error.getMessage());
        }
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

        LogRequest logRequest = new LogRequest(
                ip,
                path,
                String.valueOf(status),
                method,
                message,
                LocalDateTime.now().toString(),
                parameters
        );

        log.info(logRequest.toString());

        return logRequest;
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
