package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.request.TrainingCreateRequestDTO;
import com.example.demo.dto.response.TrainingResponseDTO;
import com.example.demo.service.TrainingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping("/create-training")
    public ResponseEntity<?> addTraining(@Valid @RequestBody TrainingCreateRequestDTO requestDTO) {
        trainingService.create(requestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/trainee-trainings/{username}")
    public List<TrainingResponseDTO> getTraineeTrainers(@PathVariable String username) {
        return trainingService.getTraineeTrainings(username);
    }

    @GetMapping("/trainer-trainings/{username}")
    public List<TrainingResponseDTO> getTrainerTrainers(@PathVariable String username) {
        return trainingService.getTrainerTrainings(username);
    }

}
