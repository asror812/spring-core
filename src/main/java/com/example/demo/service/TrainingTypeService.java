package com.example.demo.service;

import com.example.demo.dto.TrainingTypeCreateDTO;
import com.example.demo.model.TrainingType;
import com.example.demo.dto.AuthDTO;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public interface TrainingTypeService {

    Optional<TrainingType> create(AuthDTO authDTO, TrainingTypeCreateDTO createDTO);

    Optional<TrainingType> findById(UUID id);

    Optional<TrainingType> findByUsername(AuthDTO authDTO, String username);
}