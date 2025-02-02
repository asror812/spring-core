package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TrainingTypeDAO;
import com.example.demo.dto.response.TrainingTypeResponseDTO;
import com.example.demo.mapper.TrainingTypeMapper;
import com.example.demo.model.TrainingType;

import com.example.demo.exceptions.CustomException.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainingTypeService {

    private final TrainingTypeDAO trainingTypeDAO;
    private final TrainingTypeMapper mapper;

    public Optional<TrainingType> findById(UUID id) {
        TrainingType trainee = trainingTypeDAO.findById(id)
                .orElse(null);

        return Optional.ofNullable(trainee);
    }

    public TrainingType findByName(String name) {
        return trainingTypeDAO.findByName(name)
                .orElseThrow(
                        () -> new EntityNotFoundException("Training type", "name", name));
    }

    
    public List<TrainingTypeResponseDTO> getAll() {
        List<TrainingType> all = trainingTypeDAO.getAll();

        return all.stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

}
