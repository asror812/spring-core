package com.example.demo.service;


import com.example.demo.dto.TraineeCreateDTO;
import com.example.demo.dto.TraineeUpdateDTO;
import com.example.demo.model.Trainee;
import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.ChangePasswordDTO;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public interface TraineeService extends GenericService<Trainee, TraineeCreateDTO, TraineeUpdateDTO>{
   
    Optional<Trainee> findByUsername(AuthDTO authDTO, String username);

    void update(AuthDTO authDTO, TraineeUpdateDTO updateDTO);

    void delete(AuthDTO authDTO, UUID id);
    
    void activate(AuthDTO authDTO, UUID id);

    void deactivate(AuthDTO authDTO, UUID id);

    void changePassword(AuthDTO authDTO, ChangePasswordDTO changePasswordDTO);
}
