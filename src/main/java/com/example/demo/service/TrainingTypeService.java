package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.dao.TrainingTypeDAO;
import com.example.demo.dto.response.TrainingTypeResponseDTO;
import com.example.demo.mapper.TrainingTypeMapper;
import com.example.demo.model.TrainingType;

import com.example.demo.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainingTypeService {

    private final TrainingTypeDAO trainingTypeDAO;
    private final TrainingTypeMapper mapper;

    public TrainingType findByName(String name) {
        return trainingTypeDAO.findByName(name)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<TrainingTypeResponseDTO> getAll() {
        List<TrainingType> all = trainingTypeDAO.getAll();

        return all.stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

}
