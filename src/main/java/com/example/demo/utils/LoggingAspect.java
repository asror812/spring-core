package com.example.demo.utils;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.example.demo.controller..*(..))")
    public void loggingRequests() {
    }

    @Before("loggingRequests()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        Object[] arguments = joinPoint.getArgs();
        String authorization = MDC.get("transactionID");

        LOGGER.info("Transaction ID: {} | Incoming request to {}: {}",
                authorization != null ? authorization : "No Auth Header", method,
                Arrays.toString(arguments));
    }

    @AfterReturning(pointcut = "loggingRequests()", returning = "response")
    public void logAfterMethod(JoinPoint joinPoint, Object response) {
        String method = joinPoint.getSignature().toShortString();
        String authorization = MDC.get("transactionID");

        LOGGER.info("Transaction ID: {} | Response from {}: {}",
                authorization != null ? authorization : "No Auth Header", method, response);
    }
}
