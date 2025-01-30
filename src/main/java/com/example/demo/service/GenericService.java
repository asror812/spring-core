package com.example.demo.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public interface GenericService<UPDATE_REQUEST_DTO, RESPONSE_DTO, UPDATE_RESPONSE_DTO> {

    RESPONSE_DTO findById(UUID id);

    UPDATE_RESPONSE_DTO update(UPDATE_REQUEST_DTO updateDTO);
}