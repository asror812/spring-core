package com.example.demo;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import com.example.demo.model.Trainee;
import com.example.demo.service.TraineeService;



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


        //ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

    }


    @Override
    public void run(String[] args){

    TraineeService traineeService = context.getBean(TraineeService.class) ;
       
     List<Trainee> all = traineeService.findAll();

     LOGGER.info("Size of trainee list: {}" , all.size());

     for (Trainee trainee : all) {
         LOGGER.info(" {} ", trainee);
     }
  

    }

}
