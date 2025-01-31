package com.example.demo.dto.response;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingResponseDTO {

    private String trainingName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date trainingDate;

    private TrainingTypeResponseDTO trainingType;

    private Double duration;
}
