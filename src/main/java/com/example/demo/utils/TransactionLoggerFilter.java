package com.example.demo.utils;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = { "/*" })
@Order(1)
public class TransactionLoggerFilter implements Filter {

    private static final String TRANSACTION_ID = "transactionID";
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLoggerFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String transactionID = UUID.randomUUID().toString();
        MDC.put(TRANSACTION_ID, transactionID);

        long startTime = System.currentTimeMillis();
        LOGGER.info("Transaction ID: {} | HTTP {} - {}", transactionID, req.getMethod(), req.getRequestURI());
        LOGGER.info("Authorization: {}",
                req.getHeader("Authorization") != null ? req.getHeader("Authorization") : "No Auth Header");

        chain.doFilter(request, response);

        long duration = System.currentTimeMillis() - startTime;
        LOGGER.info("Transaction ID: {} | Response Status: {} | Time Taken: {}ms",
                transactionID, res.getStatus(), duration);

        MDC.remove(TRANSACTION_ID);
    }

}