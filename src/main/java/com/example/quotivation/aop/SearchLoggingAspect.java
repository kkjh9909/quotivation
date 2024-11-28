package com.example.quotivation.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SearchLoggingAspect {

    @Around("@annotation(LogQuery)")
    public Object searchLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();

        if (args.length > 0 && args[0] instanceof String query) {
            log.info("query: {}", query);
        }

        return proceedingJoinPoint.proceed();
    }
}
