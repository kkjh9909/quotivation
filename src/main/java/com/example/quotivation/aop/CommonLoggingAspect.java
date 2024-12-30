package com.example.quotivation.aop;

import com.example.quotivation.service.log.LogSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CommonLoggingAspect {

    private final LogSendService logSendService;


    @Pointcut("within(@org.springframework.stereotype.Controller *) || within(@org.springframework.web.bind.annotation.RestController *)")
    private void generalRequest() {}

    @Around("generalRequest()")
    public Object generalLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Object result = proceedingJoinPoint.proceed();

            logSendService.sendLog(proceedingJoinPoint);

            return result;
        } catch (Exception e) {
            logSendService.sendLog(proceedingJoinPoint, e);
            throw e;
        }
    }
}
