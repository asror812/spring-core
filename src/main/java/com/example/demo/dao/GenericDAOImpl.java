package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;


@Repository
public abstract class GenericDAOImpl<ENTITY , ID> implements GenericDAO<ENTITY , ID>{

    protected abstract Map<ID, ENTITY> getStorageMap();


     @Override
     public void create(ID id ,ENTITY entity) {
         getStorageMap().put(id , entity);
         
     }

    @Override
    public List<ENTITY> select() {
        return new ArrayList<>(getStorageMap().values());
    }

    @Override
    public Optional<ENTITY> selectById(ID id) {
        return Optional.ofNullable(getStorageMap().get(id)) ;
    }

}
