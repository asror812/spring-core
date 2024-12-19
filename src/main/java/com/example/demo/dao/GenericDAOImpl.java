package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;


@Repository
public abstract class GenericDAOImpl<ENTITY> implements GenericDAO<ENTITY> {

    protected abstract Map<UUID, ENTITY> getStorageMap();

    @Override
    public void create(UUID id, ENTITY entity) {
        getStorageMap().put(id, entity);         
    }

    @Override
    public List<ENTITY> select() {
        return new ArrayList<>(getStorageMap().values());
    }

    @Override
    public Optional<ENTITY> selectById(UUID id) {
        return Optional.ofNullable(getStorageMap().get(id)) ;
    }

}
