package com.example.demo.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;


@Repository
public interface GenericDAO<ENTITY> {
     
    void create(UUID id, ENTITY entity);
    List<ENTITY> select();
    Optional<ENTITY> selectById(UUID id);
    
} 