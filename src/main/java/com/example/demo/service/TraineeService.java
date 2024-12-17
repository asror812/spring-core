package com.example.demo.service;

import com.example.demo.dao.TraineeDAO;
import com.example.demo.dto.TraineeCreateDTO;
import com.example.demo.dto.TraineeUpdateDTO;
import com.example.demo.model.Trainee;
import com.example.demo.utils.PasswordGenerator;
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
public class TraineeService extends GenericService<Trainee , UUID , TraineeCreateDTO> {


    private final TraineeDAO genericDao; 
    private static final  Logger LOGGER = LoggerFactory.getLogger(TraineeService.class);
    private PasswordGenerator passwordGenerator;

    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }


    @Autowired
    public TraineeService(TraineeDAO dao) {
        this.genericDao = dao;
    }

    @Override
    public Trainee create(TraineeCreateDTO createDTO) {

        if(createDTO == null){
            throw new IllegalArgumentException();
        }

        Trainee newTrainee = new Trainee();

        UUID id = UUID.randomUUID();
        String userName = createDTO.getFirstName()  + "." + createDTO.getLastName();
        String password = passwordGenerator.generate();    
        
        newTrainee.setUserId(id);
        newTrainee.setFirstName(createDTO.getFirstName());
        newTrainee.setLastName(createDTO.getLastName());
        newTrainee.setUsername(userName);
        newTrainee.setActive(true);
        newTrainee.setAddress(createDTO.getAddress());
        newTrainee.setDateOfBirth(createDTO.getDateOfBirth());
        newTrainee.setPassword(password);
                

        for (Trainee trainee1 : genericDao.select()) {
            if(Objects.equals(trainee1.getFirstName(), createDTO.getFirstName())
                && Objects.equals(trainee1.getLastName(), createDTO.getLastName())){

                newTrainee.setUsername(userName + newTrainee.getUserId());
                genericDao.create(id, newTrainee);

                LOGGER.info("{}  successfully created" , newTrainee);

                return newTrainee;
            }
        }
        genericDao.create(id , newTrainee );

        LOGGER.info("{} successfully created",  newTrainee);

        return newTrainee;
    }
        

   
    public void update(UUID id , TraineeUpdateDTO updateDTO) {


        if(id == null || updateDTO == null){
            throw new IllegalArgumentException();
        }
        
        Optional<Trainee> existingTrainee = genericDao.selectById(id);

        if(existingTrainee.isEmpty()){
           LOGGER.error("Trainee with id {} not found", id);
           throw new IllegalArgumentException("Trainee with id " + id + " not found");
        }
            
            Trainee trainee  = existingTrainee.get();

            trainee.setFirstName(updateDTO.getFirstName());
            trainee.setLastName(updateDTO.getLastName());
            trainee.setPassword(updateDTO.getPassword());
            trainee.setAddress(updateDTO.getAddress());
            trainee.setDateOfBirth(updateDTO.getDateOfBirth());

            genericDao.update(trainee);

            LOGGER.info("{}  successfully updated " , trainee);
    }



    public void delete(UUID id) {
        genericDao.delete(id);
    }

}
