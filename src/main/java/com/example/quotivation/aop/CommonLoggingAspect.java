package com.example.quotivation.aop;

import com.example.quotivation.exception.GlobalExceptionController;
import com.example.quotivation.service.log.LogSendService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CommonLoggingAspect {

    private final HttpServletRequest request;
    private final LogSendService logSendService;


    @Pointcut("within(@org.springframework.stereotype.Controller *) || within(@org.springframework.web.bind.annotation.RestController *)")
    private void generalRequest() {}

    @Pointcut("execution(* com.example.quotivation.controller.SearchController.*(..))")
    private void searchRequest() {}


    @Around("generalRequest() && !searchRequest()")
    public Object generalLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        String clientIp = getClientIp();
//        String requestUrl = getRequestUrl();
//
//        logSendService.sendLog(proceedingJoinPoint);
//
//        log.info("{} - {}", clientIp, requestUrl);
//
//        return proceedingJoinPoint.proceed();

        try {
            Object result = proceedingJoinPoint.proceed();


            logSendService.sendLog(proceedingJoinPoint);

            return result;
        } catch (Exception e) {
            log.info("error 발생");

            logSendService.sendLog(proceedingJoinPoint, e);
            throw e;
        }
    }

    @Around("searchRequest()")
    public Object searchLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String clientIp = getClientIp();
        String requestUrl = getRequestUrl();

        logSendService.sendLog(proceedingJoinPoint);

        log.info("{} - {}", clientIp, requestUrl);

        return proceedingJoinPoint.proceed();
    }

    private String getRequestUrl() {
        return request.getRequestURI();
    }

    private String getClientIp() {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }
}
