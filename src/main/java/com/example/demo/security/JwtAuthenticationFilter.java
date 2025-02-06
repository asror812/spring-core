package com.example.demo.security;

import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.exceptions.AuthenticationException;

import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@WebFilter(urlPatterns = { "/*" })
@Order(2)
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final Set<String> EXCLUDED_URLS = Set.of(
            "/auth/trainers/sign-up",
            "/auth/trainees/sign-up",
            "/auth/sign-in");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String path = req.getServletPath();

        if (EXCLUDED_URLS.stream().anyMatch(path::contains)) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader(HEADER_NAME);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            throw new AuthenticationException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.claims(token);
            if (claims == null) {
                throw new AuthenticationException("Invalid or expired token");
            }
            req.setAttribute("claims", claims);
            chain.doFilter(request, response);

        } catch (Exception e) {
            LOGGER.error("Authentication error: ", e.getCause());
            throw new AuthenticationException("Authentication failed");
        }

    }
}
