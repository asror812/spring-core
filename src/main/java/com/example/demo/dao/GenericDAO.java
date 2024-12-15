package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;


@Repository
public interface GenericDAO<ENTITY , ID> {
     
    void create(ENTITY entity , ID id);
    List<ENTITY> select();
    
    Optional<ENTITY> selectById(ID id);
    
} 