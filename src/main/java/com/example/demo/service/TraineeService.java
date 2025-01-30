package com.example.demo.service;

import com.example.demo.dao.TraineeDAO;
import com.example.demo.dao.TrainerDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.request.TraineeSignUpRequestDTO;
import com.example.demo.dto.request.TraineeUpdateRequestDTO;
import com.example.demo.dto.response.SingUpResponseDTO;
import com.example.demo.dto.response.TraineeResponseDTO;
import com.example.demo.dto.response.TraineeUpdateResponseDTO;
import com.example.demo.dto.response.TrainerResponseDTO;
import com.example.demo.mapper.TraineeMapper;
import com.example.demo.mapper.TrainerMapper;
import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
import com.example.demo.model.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

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
    private static final String NO_TRAINEE_FOUND_WITH_USERNAME = "No Trainee found with username %s";

    @Transactional
    public SingUpResponseDTO register(TraineeSignUpRequestDTO requestDTO) {
        SingUpResponseDTO register = authService.register(requestDTO);
        User user = new User(requestDTO.getFirstName(), requestDTO.getLastName(),
                register.getUsername(), register.getPassword(), true);

        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setUsername(register.getUsername());
        user.setPassword(register.getPassword());
        user.setActive(true);

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
                .orElseThrow(
                        () -> new EntityNotFoundException(NO_TRAINEE_FOUND_WITH_USERNAME.formatted(username)));

        mapper.toEntity(updateDTO, trainee);
        dao.update(trainee);

        return mapper.toUpdateResponseDTO(trainee);
    }

    public TraineeResponseDTO findByUsername(String username) {
        Trainee trainee = dao
                .findByUsername(username)
                .orElseThrow(
                        () -> new EntityNotFoundException(NO_TRAINEE_FOUND_WITH_USERNAME.formatted(username)));

        return mapper.toResponseDTO(trainee);
    }

    @Transactional
    public void delete(String username) {
        Trainee trainee = dao.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(NO_TRAINEE_FOUND_WITH_USERNAME.formatted(username)));
        dao.delete(trainee);
    }

    @Transactional
    public void setStatus(String username, Boolean status) {
        Trainee trainee = dao.findByUsername(username)
                .orElseThrow(
                        () -> new EntityNotFoundException(NO_TRAINEE_FOUND_WITH_USERNAME.formatted(username)));

        User user = trainee.getUser();

        if (Objects.equals(user.getActive(), status)) {
            LOGGER.error("'{}' already {}", trainee, status);
            // TODO throw exception
            return;
        }

        user.setActive(status);
        userDAO.update(user);
    }

    public List<TrainerResponseDTO> getNotAssignedTrainers(String username) {
        Trainee trainee = dao.findByUsername(username)
                .orElseThrow(
                        () -> new EntityNotFoundException(NO_TRAINEE_FOUND_WITH_USERNAME.formatted(username)));

        List<Trainer> traineeTrainers = trainee.getTrainers();
        List<Trainer> all = trainerDAO.getAll();

        all.removeIf(traineeTrainers::contains);

        return all.stream()
                .map(trainerMapper::toResponseDTO)
                .toList();
    }

  

}
