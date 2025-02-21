package com.example.demo.security;

import java.io.IOException;
import java.util.Set;
import com.example.demo.exceptions.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
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
@Order(2)
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;

    private final Gson gson;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final Set<String> EXCLUDED_URLS = Set.of(
            "/auth/trainers/sign-up",
            "/auth/trainees/sign-up",
            "/auth/sign-in");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getServletPath();

        if (EXCLUDED_URLS.stream().anyMatch(path::contains)) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader(HEADER_NAME);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            sendErrorResponse(res, HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.claims(token);
            if (claims == null) {
                sendErrorResponse(res, HttpStatus.UNAUTHORIZED, "Invalid or expired token");
            }
            req.setAttribute("claims", claims);
            chain.doFilter(request, response);

        } catch (Exception e) {
            LOGGER.error("Authentication error: ", e.getCause());
            sendErrorResponse(res, HttpStatus.UNAUTHORIZED, "Authentication failed");
        }

    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(status.value(), message);
        response.getWriter().write(gson.toJson(errorResponse));
    }
}
