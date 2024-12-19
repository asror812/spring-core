package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenericService<ENTITY, CREATE_DTO> {
    public Optional<ENTITY> findById(UUID id);
    public List<ENTITY> findAll() ;
    public  ENTITY create(CREATE_DTO createDTO);
}
