package com.example.demo;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.example.demo.dto.TraineeCreateDTO;
import com.example.demo.dto.TraineeUpdateDTO;
import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
import com.example.demo.service.TraineeService;
import com.example.demo.service.TrainerService;



@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    private final ApplicationContext context;

    @Autowired
    public DemoApplication(ApplicationContext context){
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... args) {

        TraineeService traineeService = context.getBean(TraineeService.class) ;
        List<Trainee> atTrainees = traineeService.findAll();

        TrainerService trainerService = context.getBean(TrainerService.class) ;
        List<Trainer> aTrainers = trainerService.findAll();

        LOGGER.info("Size of trainee list: {}" , atTrainees.size());
        LOGGER.info("Size of trainer list: {}" , aTrainers.size());

        Trainee trainee = traineeService.create(new TraineeCreateDTO("B", "B", "B", LocalDate.now()));;
      
        traineeService.findAll()
                                .forEach(p -> LOGGER.info(" {} ", p ));
              
        TraineeUpdateDTO updateDTO = new TraineeUpdateDTO( "Farhod" , "R" ,"12345678" , true , LocalDate.of(2000, 10, 10) ,"T");
      
        traineeService.update(trainee.getUserId(), updateDTO);
      
        Trainee updateTrainee = traineeService.findById(trainee.getUserId()).get();
        LOGGER.info("{}  successfully  updated", updateTrainee);  

        
    }

}
