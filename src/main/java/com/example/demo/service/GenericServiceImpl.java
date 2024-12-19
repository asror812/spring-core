package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.demo.dao.GenericDAO;


@Service
public abstract class GenericServiceImpl<ENTITY, CREATE_DTO > implements GenericService<ENTITY, CREATE_DTO> {
    private final static Logger LOGGER = LoggerFactory.getLogger(GenericService.class);
    protected abstract GenericDAO<ENTITY> getGenericDao();

    public Optional<ENTITY> findById(UUID id) {
        Optional<ENTITY> existingEntity = getGenericDao().selectById(id);

        if(existingEntity.isEmpty()){            
            LOGGER.warn("Trainer with id {} not found", id);
            return Optional.empty() ;
        }
        
        return existingEntity;
    }

    public List<ENTITY> findAll() {
        List<ENTITY> values = new ArrayList<>();
        for (ENTITY entity : values) {
            LOGGER.info("{}", entity);
        }  

        return values;
    }


}
