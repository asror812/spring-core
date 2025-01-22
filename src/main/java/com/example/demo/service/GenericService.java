package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

public interface GenericService<ENTITY, CREATE_DTO, UPDATE_DTO> {
    Optional<ENTITY> create(CREATE_DTO entity);

    Optional<ENTITY> findById(UUID id);

    void update(UPDATE_DTO updateDTO);
}