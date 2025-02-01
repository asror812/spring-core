package com.example.demo.service;

import com.example.demo.dao.TrainerDAO;
import com.example.demo.dao.TrainingTypeDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.request.TrainerSignUpRequestDTO;
import com.example.demo.dto.request.TrainerUpdateRequestDTO;
import com.example.demo.dto.response.SingUpResponseDTO;
import com.example.demo.dto.response.TrainerResponseDTO;
import com.example.demo.dto.response.TrainerUpdateResponseDTO;
import com.example.demo.mapper.TrainerMapper;
import com.example.demo.model.Trainer;
import com.example.demo.model.TrainingType;
import com.example.demo.model.User;
import com.example.demo.exceptions.CustomException.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class TrainerService extends
        AbstractGenericService<Trainer, TrainerSignUpRequestDTO, TrainerUpdateRequestDTO, TrainerResponseDTO, TrainerUpdateResponseDTO> {

    private final UsernameGeneratorService usernameGeneratorService;
    private final AuthService authService;
    private final TrainerDAO dao;
    private final Class<Trainer> entityClass = Trainer.class;
    private final TrainerMapper mapper;
    private final UserDAO userDAO;
    private final TrainingTypeDAO trainingTypeDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerService.class);

    public TrainerResponseDTO findByUsername(String username) {
        Trainer trainer = getTrainerByUsername(username);
        return mapper.toResponseDTO(trainer);
    }

    @Transactional
    public void setStatus(String username, Boolean status) {
        Trainer trainer = getTrainerByUsername(username);
        User user = trainer.getUser();

        if (user.getActive().equals(status)) {
            LOGGER.error("'{}' already {}", trainer, status);
            throw new IllegalStateException(String.format("'%s' is already %s", username, status));
        }

        user.setActive(status);
        userDAO.update(user);
    }

    @Transactional
    public SingUpResponseDTO register(TrainerSignUpRequestDTO requestDTO) {
        SingUpResponseDTO register = authService.register(requestDTO);

        User user = new User(requestDTO.getFirstName(), requestDTO.getLastName(), register.getUsername(),
                register.getPassword(), true);

        UUID specialization = requestDTO.getSpecialization();

        TrainingType trainingType = trainingTypeDAO.findById(specialization)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "TrainingType", "id", specialization.toString()));

        Trainer trainer = new Trainer();
        trainer.setSpecialization(trainingType);
        trainer.setUser(user);

        dao.create(trainer);
        return register;
    }

    @Override
    @Transactional
    protected TrainerUpdateResponseDTO internalUpdate(TrainerUpdateRequestDTO updateDTO) {
        Trainer trainer = getTrainerByUsername(updateDTO.getUsername());
        mapper.toEntity(updateDTO, trainer);
        dao.update(trainer);

        return mapper.toUpdateResponseDTO(trainer);
    }

    private Trainer getTrainerByUsername(String username) {
        return dao.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainer", "username", username));
    }

}
