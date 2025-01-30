package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.example.demo.dto.request.TrainingCreateRequestDTO;
import com.example.demo.dto.request.TrainingUpdateRequestDTO;
import com.example.demo.dto.response.TrainingResponseDTO;
import com.example.demo.model.Training;
import com.example.demo.service.TrainingUpdateResponseDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TrainingMapper
        implements
        GenericMapper<Training, TrainingCreateRequestDTO, TrainingResponseDTO, TrainingUpdateRequestDTO, TrainingUpdateResponseDTO> {


    private final ModelMapper modelMapper;
    @Override
    public Training toEntity(TrainingCreateRequestDTO createDto) {
        return modelMapper.map(createDto, Training.class);
    }

    @Override
    public TrainingResponseDTO toResponseDTO(Training training) {
        return modelMapper.map(training, TrainingResponseDTO.class);
    }

    @Override
    public void toEntity(TrainingUpdateRequestDTO updateDto, Training training) {
        modelMapper.map(training, updateDto);
    }

}