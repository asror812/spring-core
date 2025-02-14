package com.example.demo.utils;

import com.example.demo.exceptions.ErrorResponseDTO;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component
public class BruteForceProtectorService {

    @Value("${security.failure.count:5}")
    private Integer MAX_FAILURE_COUNTS;

    @Value("${security.failure.duration:300}")
    private Integer BLOCK_TIME;

    private final static Logger LOGGER = LoggerFactory.getLogger(BruteForceProtectorService.class);
    private static final Map<String, IpAddressInfo> failureCountsWithIp = new ConcurrentHashMap<>();
    private final Gson gson;

    public void incrementFailureCount(String ip, HttpServletResponse response) {
        IpAddressInfo info = failureCountsWithIp.get(ip);

        if (info != null) {
            LOGGER.info("Ip: {} Date: {} Count: {}", ip, failureCountsWithIp.get(ip).getDateTime(), failureCountsWithIp.get(ip).getCount());
        }

        if (info == null) {
            failureCountsWithIp.put(ip, new IpAddressInfo(1, LocalDateTime.now()));
        } else if (info.getCount() == MAX_FAILURE_COUNTS - 1) {
            info.setCount(info.getCount() + 1);
            info.setDateTime(LocalDateTime.now());
            failureCountsWithIp.put(ip, info);
            sendErrorResponse(response, "Please try after %s".formatted(info.getDateTime().plusSeconds(BLOCK_TIME)));

        } else if (MAX_FAILURE_COUNTS.equals(info.getCount())) {
            if (LocalDateTime.now().isAfter(info.getDateTime().plusSeconds(BLOCK_TIME))) {
                sendErrorResponse(response, "Please try after %s".formatted(info.getDateTime().plusSeconds(BLOCK_TIME)));
            }
            info.setDateTime(LocalDateTime.now());
            info.setCount(1);
        } else if (info.getCount() >= 1) {
            info.setCount(info.getCount() + 1);
            failureCountsWithIp.put(ip, info);
        }
    }


    private void sendErrorResponse(HttpServletResponse response, String message) {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(HttpStatus.TOO_MANY_REQUESTS.value(), message);
        try {
            response.getWriter().write(gson.toJson(errorResponse));
            response.getWriter().flush();
            response.getWriter().close();
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
