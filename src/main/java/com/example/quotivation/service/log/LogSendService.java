package com.example.quotivation.service.log;

import com.example.quotivation.dto.log.request.LogRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        LogRequest log = createLog(joinPoint);

        restTemplate.postForObject(LOG_SERVER_URL, log, Void.class);
    }

    public LogRequest createLog(ProceedingJoinPoint joinPoint) {
        String ip = getClientIp();
        String path = getRequestUrl();
        String method = request.getMethod();

        Map<String, String> parameters = new HashMap<>();

        Object[] args = joinPoint.getArgs();

        if (args.length > 0 && args[0] instanceof String query) {
            parameters.put("query", query);
        }

        return new LogRequest(
                ip,
                path,
                200,
                method,
                "message",
                LocalDateTime.now(),
                parameters
        );
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
