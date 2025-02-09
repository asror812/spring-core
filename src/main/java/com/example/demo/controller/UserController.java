package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TraineeService;
import com.example.demo.service.TrainerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    @PatchMapping("/trainees/{username}")
    public ResponseEntity<?> setTraineeStatus(@PathVariable String username, @RequestParam boolean status) {
        traineeService.setStatus(username, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PatchMapping("/trainers/{username}")
    public ResponseEntity<?> setTrainerStatus(@PathVariable String username, @RequestParam boolean status) {
        trainerService.setStatus(username, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
