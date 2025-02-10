package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.request.TrainingCreateRequestDTO;
import com.example.demo.dto.response.TrainingResponseDTO;
import com.example.demo.service.TrainingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping("/trainings")
    public ResponseEntity<?> addTraining(@Valid @RequestBody TrainingCreateRequestDTO requestDTO) {
        trainingService.create(requestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //Correct path of trainee trainings

    @GetMapping("/trainers/{username}/trainings")
    public ResponseEntity<List<TrainingResponseDTO>> getTrainerTrainers(@PathVariable String username,
            @RequestParam(required = false) Date from,
            @RequestParam(required = false) Date to,
            @RequestParam(required = false) String traineeName) {

        List<TrainingResponseDTO> trainerTrainings = trainingService.getTrainerTrainings(username, from, to,
                traineeName);
        return ResponseEntity.ok(trainerTrainings);
    }

    @GetMapping("/trainees/{username}/trainings")
    public ResponseEntity<List<TrainingResponseDTO>> getTraineeTrainings(@PathVariable String username,
            @RequestParam(required = false) Date from,
            @RequestParam(required = false) Date to,
            @RequestParam(required = false) String trainerName,
            @RequestParam(required = false) String trainingTypeName) {

        List<TrainingResponseDTO> traineeTrainings = trainingService.getTraineeTrainings(username, from, to,
                trainerName, trainingTypeName);

        return ResponseEntity.ok(traineeTrainings);
    }

}
