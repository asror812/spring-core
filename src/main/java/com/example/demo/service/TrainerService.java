package com.example.demo.service;


import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.ChangePasswordDTO;
import com.example.demo.dto.TrainerCreateDTO;
import com.example.demo.dto.TrainerUpdateDTO;
import com.example.demo.model.Trainer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public interface TrainerService {
    Optional<Trainer> create(TrainerCreateDTO createDTO);

    Optional<Trainer> findByUsername(AuthDTO authDTO, String username);

    void update(AuthDTO authDTO, TrainerUpdateDTO updateDTO);

    void activate(AuthDTO authDTO, UUID id);

    void deactivate(AuthDTO authDTO, UUID id);

    void changePassword(AuthDTO authDTO, ChangePasswordDTO changePasswordDTO);
}