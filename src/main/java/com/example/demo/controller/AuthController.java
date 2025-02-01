package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.ChangePasswordRequestDTO;
import com.example.demo.dto.request.SignInRequestDTO;
import com.example.demo.dto.request.TraineeSignUpRequestDTO;
import com.example.demo.dto.request.TrainerSignUpRequestDTO;
import com.example.demo.dto.response.SignInResponseDTO;
import com.example.demo.dto.response.SingUpResponseDTO;
import com.example.demo.service.AuthService;
import com.example.demo.service.TraineeService;
import com.example.demo.service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final AuthService authService;

    @PostMapping("/trainee/sign-up")
    public ResponseEntity<?> signUpTrainee(@Valid @RequestBody TraineeSignUpRequestDTO requestDTO) {
        SingUpResponseDTO register = traineeService.register(requestDTO);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }

    @PostMapping("/trainer/sign-up")
    public ResponseEntity<?> signUpTrainer(@Valid @RequestBody TrainerSignUpRequestDTO requestDTO) {
        SingUpResponseDTO register = trainerService.register(requestDTO);
        return ResponseEntity.ok(register);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDTO> signIn(@Valid @RequestBody SignInRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.login(requestDTO));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changeLogin(@Valid @RequestBody ChangePasswordRequestDTO requestDTO) {
        authService.changePassword(requestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
