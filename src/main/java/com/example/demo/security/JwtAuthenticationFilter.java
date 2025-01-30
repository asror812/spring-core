package com.example.demo.security;

import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.exceptions.JwtAuthenticationException;

import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@WebFilter(urlPatterns = { "/*" })
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;

    private static final Set<String> EXCLUDED_URLS = Set.of("/auth/trainer/sign-up", "/auth/trainee/sign-up", "/auth/sign-in");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getServletPath();

        LOGGER.info(path);
        if (EXCLUDED_URLS.contains(path)) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader(HEADER_NAME);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String token = authHeader.substring(7);
        Claims claims = jwtService.claims(token);

        if (claims == null) {
            throw new JwtAuthenticationException("Token is invalid or expired");
        }
        req.setAttribute("claims", claims);

        chain.doFilter(request, response);
    }
}
