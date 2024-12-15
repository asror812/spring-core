package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;


@Repository
public interface GenericDAO<ENTITY , ID> {
     
    void create(ID id , ENTITY entity);
    List<ENTITY> select();
    
    Optional<ENTITY> selectById(ID id);
    
} 