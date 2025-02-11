package com.example.demo.service;

import com.example.demo.dao.TraineeDAO;
import com.example.demo.dao.TrainerDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.request.TraineeSignUpRequestDTO;
import com.example.demo.dto.request.TraineeTrainersUpdateRequestDTO;
import com.example.demo.dto.request.TraineeUpdateRequestDTO;
import com.example.demo.dto.request.TraineeTrainersUpdateRequestDTO.TrainerDTO;
import com.example.demo.dto.response.SignUpResponseDTO;
import com.example.demo.dto.response.TraineeResponseDTO;
import com.example.demo.dto.response.TraineeUpdateResponseDTO;
import com.example.demo.dto.response.TrainerResponseDTO;
import com.example.demo.mapper.TraineeMapper;
import com.example.demo.mapper.TrainerMapper;
import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
import com.example.demo.model.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Getter
public class TraineeService
        extends
        AbstractGenericService<Trainee, TraineeSignUpRequestDTO, TraineeUpdateRequestDTO, TraineeResponseDTO, TraineeUpdateResponseDTO> {

    private final TraineeDAO dao;
    private final TrainerDAO trainerDAO;
    private final UserDAO userDAO;
    private final AuthService authService;
    private final TraineeMapper mapper;
    private final TrainerMapper trainerMapper;
    private final Class<Trainee> entityClass = Trainee.class;
    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeService.class);

    private static final String TRAINEE_NOT_FOUND_WITH_USERNAME = "Trainee with username %s not found";
    private static final String TRAINER_NOT_FOUND_WITH_USERNAME = "Trainer with username %s not found";

    @Transactional
    public SignUpResponseDTO register(TraineeSignUpRequestDTO requestDTO) {
        SignUpResponseDTO register = authService.register(requestDTO);

        User user = new User(requestDTO.getFirstName(), requestDTO.getLastName(),
                register.getUsername(), register.getPassword(), true);

        Trainee trainee = new Trainee();
        trainee.setAddress(requestDTO.getAddress());
        trainee.setDateOfBirth(requestDTO.getDateOfBirth());
        trainee.setUser(user);

        dao.create(trainee);

        return register;
    }

    @Override
    protected TraineeUpdateResponseDTO internalUpdate(TraineeUpdateRequestDTO updateDTO) {
        String username = updateDTO.getUsername();

        Trainee trainee = dao.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(TRAINEE_NOT_FOUND_WITH_USERNAME.formatted(username)));

        mapper.toEntity(updateDTO, trainee);
        dao.update(trainee);

        return mapper.toUpdateResponseDTO(trainee);
    }

    public Optional<TraineeResponseDTO> findByUsername(String username) {
        return dao.findByUsername(username)
                .map(mapper::toResponseDTO);
    }

    @Transactional
    public void delete(String username) {
        Trainee trainee = dao.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(TRAINEE_NOT_FOUND_WITH_USERNAME.formatted(username)));
        dao.delete(trainee);
    }

    @Transactional
    public void setStatus(String username, Boolean status) {
        Trainee trainee = dao.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(TRAINEE_NOT_FOUND_WITH_USERNAME.formatted(username)));

        User user = trainee.getUser();

        if (Objects.equals(user.getActive(), status)) {
            LOGGER.warn("'{}' already {}", trainee, status);
            throw new IllegalStateException(String.format("'%s' is already %s", username, status));
        }

        user.setActive(status);
        userDAO.update(user);
    }

    public List<TrainerResponseDTO> getNotAssignedTrainers(String username) {
        Trainee trainee = dao.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(TRAINEE_NOT_FOUND_WITH_USERNAME.formatted(username)));

        Set<Trainer> traineeTrainers = Set.copyOf(trainee.getTrainers());

        return trainerDAO.getAll().stream()
                .filter(trainer -> !traineeTrainers.contains(trainer))
                .map(trainerMapper::toResponseDTO)
                .toList();
    }

    public List<TrainerResponseDTO> updateTraineeTrainers(String username, TraineeTrainersUpdateRequestDTO requestDTO) {
        Trainee trainee = dao.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(TRAINEE_NOT_FOUND_WITH_USERNAME.formatted(username)));

        List<Trainer> trainers = new ArrayList<>();
        for (TrainerDTO dto : requestDTO.getTrainers()) {
            Trainer trainer = trainerDAO.findByUsername(dto.getUsername()).orElseThrow(
                    () -> new ResourceNotFoundException(
                            TRAINER_NOT_FOUND_WITH_USERNAME.formatted(dto.getUsername())));

            trainers.add(trainer);
        }

        trainee.setTrainers(trainers);

        dao.update(trainee);

        return trainers.stream()
                .map(trainerMapper::toResponseDTO)
                .toList();

    }

}
