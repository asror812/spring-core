package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import com.example.demo.dao.GenericDAO;

import lombok.Getter;

@Getter
public abstract class AbstractGenericService<ENTITY, CREATE_DTO, UPDATE_DTO>
        implements GenericService<ENTITY, CREATE_DTO, UPDATE_DTO> {

    protected abstract GenericDAO<ENTITY> genericDao();

    public Optional<T> create(CREATE_DTO createDto) {
        return internalCreate(createDto);
    }

    public Optional<T> update(UPDATE_DTO updateDto) {
        return internalUpdate(updateDto);
    }

    protected AbstractGenericService(GenericDAO<T> dao) {
        this.dao = dao;
    }

    @Override
    public Optional<T> create(T entity) {
        return dao.create(entity);
    }

    @Override
    public Optional<T> findById(UUID id) {
        return dao.findById(id);
    }

    @Override
    public void update(T entity) {
        dao.update(entity);
    }

}
