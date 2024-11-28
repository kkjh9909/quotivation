package com.example.quotivation.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CommonLoggingAspect {

    private final HttpServletRequest request;

    @Pointcut("execution(* com.example.quotivation.controller..*.*(..))")
    private void cut(){}

    @Around("cut()")
    public Object aroundLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String clientIp = getClientIp();
        String requestUrl = getRequestUrl();

        log.info("{} - {}", clientIp, requestUrl);

        return proceedingJoinPoint.proceed();
    }


    private Method getMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        return signature.getMethod();
    }

    private String getRequestUrl() {
        return request.getRequestURI();
    }

    private String getClientIp() {
        String clientIp = request.getHeader("X-Forwarded-For"); // 프록시나 로드밸런서가 추가한 헤더
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr(); // 기본 클라이언트 IP
        }
        return clientIp;
    }
}
