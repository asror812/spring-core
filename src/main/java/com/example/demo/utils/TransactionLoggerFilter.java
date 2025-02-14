package com.example.demo.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.example.demo.exceptions.ErrorResponseDTO;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
@RequiredArgsConstructor
public class TransactionLoggerFilter extends OncePerRequestFilter {
    private static final String TRANSACTION_ID = "transactionID";
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLoggerFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String transactionID = UUID.randomUUID().toString();
        MDC.put(TRANSACTION_ID, transactionID);

        long startTime = System.currentTimeMillis();
        LOGGER.info("Transaction id {} | HTTP {} - {}", transactionID, request.getMethod(), request.getRequestURI());

        try {
            filterChain.doFilter(request, response);

            long duration = System.currentTimeMillis() - startTime;
            LOGGER.info("Transaction id {} | Response Status: {} | Time Taken: {}ms",
                    transactionID, response.getStatus(), duration);

        } finally {

            MDC.remove(TRANSACTION_ID);
        }

    }
}