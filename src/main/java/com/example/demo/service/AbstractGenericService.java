package com.example.demo.service;

import java.util.UUID;
import com.example.demo.dao.GenericDAO;
import com.example.demo.mapper.GenericMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;

@Getter
public abstract class AbstractGenericService<ENTITY, CREATE_DTO, UPDATE_DTO, RESPONSE_DTO, UPDATE_RESPONSE_DTO>
        implements GenericService<UPDATE_DTO, RESPONSE_DTO, UPDATE_RESPONSE_DTO> {

    protected abstract GenericDAO<ENTITY> getDao();

    protected abstract Class<ENTITY> getEntityClass();

    protected abstract GenericMapper<ENTITY, CREATE_DTO, RESPONSE_DTO, UPDATE_DTO,UPDATE_RESPONSE_DTO> getMapper();

    protected abstract UPDATE_RESPONSE_DTO internalUpdate(UPDATE_DTO updateDTO);

    @Transactional
    public UPDATE_RESPONSE_DTO update(UPDATE_DTO updateDto) {
        return internalUpdate(updateDto);
    }

    @Override
    public RESPONSE_DTO findById(UUID id) {
        ENTITY entity = getDao().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        ("%s with id  %s not found").formatted(getEntityClass(), id)));

        return getMapper().toResponseDTO(entity);
    }

}
