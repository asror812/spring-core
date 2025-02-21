package com.example.demo.dto.response;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraineeResponseDTO {
    private UserResponseDTO user;

    private Date dateOfBirth;

    private String address;

    private List<TrainerResponseDTO> trainers;
}
