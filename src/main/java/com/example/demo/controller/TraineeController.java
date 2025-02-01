package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.TraineeUpdateRequestDTO;
import com.example.demo.dto.response.TraineeResponseDTO;
import com.example.demo.dto.response.TraineeUpdateResponseDTO;
import com.example.demo.dto.response.TrainerResponseDTO;
import com.example.demo.service.TraineeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainee")
public class TraineeController {

    private final TraineeService traineeService;

    @GetMapping("/profile/{username}")
    public ResponseEntity<TraineeResponseDTO> getProfile(@PathVariable("username") String username) {
        TraineeResponseDTO profileInfo = traineeService.findByUsername(username);
        return new ResponseEntity<>(profileInfo, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<TraineeUpdateResponseDTO> update(@Valid @RequestBody TraineeUpdateRequestDTO requestDTO) {
        TraineeUpdateResponseDTO update = traineeService.update(requestDTO);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> delete(@PathVariable String username) {
        traineeService.delete(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{username}")
    public ResponseEntity<?> status(@PathVariable String username, @RequestParam boolean status) {
        traineeService.setStatus(username, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}/not-assigned-trainers")
    public ResponseEntity<List<TrainerResponseDTO>> getNotAssignedTrainers(@PathVariable String username) {
        return new ResponseEntity<>(traineeService.getNotAssignedTrainers(username), HttpStatus.OK);
    }

   
    

}
