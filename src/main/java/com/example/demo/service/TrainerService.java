package com.example.demo.service;


import com.example.demo.dao.TrainerDAO;
import com.example.demo.dto.TrainerCreateDTO;
import com.example.demo.dto.TrainerUpdateDTO;
import com.example.demo.model.Trainer;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Getter
public class TrainerService extends GenericServiceImpl<Trainer, TrainerCreateDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerService.class);
    private final TrainerDAO genericDao;
    private static long serialNumber = 1;

    @Autowired
    public TrainerService(TrainerDAO dao) {
        this.genericDao = dao;
    }

    public Trainer create(TrainerCreateDTO createDTO) {
        if (createDTO == null) {
           throw new NullPointerException("Trainer create data cannot be null.");
        }

        Trainer newTrainer = new Trainer();
        UUID id = UUID.randomUUID();
        String username = createDTO.getFirstName()+ "." + createDTO.getLastName();

        newTrainer.setUserId(id);
        newTrainer.setFirstName(createDTO.getFirstName());
        newTrainer.setLastName(createDTO.getLastName());
        newTrainer.setUsername(username);
        newTrainer.setSpecialization(createDTO.getSpecialization());

        for (Trainer trainer1 : genericDao.select()) {
             if(Objects.equals(trainer1.getFirstName(), createDTO.getFirstName())
                 && Objects.equals(trainer1.getLastName(), createDTO.getLastName())) {

                 newTrainer.setUsername(username + serialNumber++);
                 genericDao.create(id ,newTrainer);
                 LOGGER.info("{} successfully created."  , newTrainer);

                 return newTrainer;
             }
        }

        genericDao.create(id ,newTrainer);
        LOGGER.info("{} successfully created" , username  ,  newTrainer.getSpecialization());

        return newTrainer;
    }


    public void update(UUID id, TrainerUpdateDTO updateDTO) {
        if(id == null || updateDTO == null){
            throw new NullPointerException("ID and update data cannot be null."); 
        }

        Optional<Trainer> existingTrainer = genericDao.selectById(id);

        if (existingTrainer.isEmpty()) {
           LOGGER.error("Trainer with id {} not found", id);
           throw new IllegalArgumentException("Trainer with id " + id + " not found");
        }

        Trainer trainer = existingTrainer.get();

        trainer.setFirstName(updateDTO.getFirstName());
        trainer.setLastName(updateDTO.getLastName());
        trainer.setPassword(updateDTO.getPassword());
        trainer.setSpecialization(updateDTO.getSpecialization());
        genericDao.update(trainer);
    }
}
