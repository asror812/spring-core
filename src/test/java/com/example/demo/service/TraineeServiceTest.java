package com.example.demo.service;

import com.example.demo.dao.TraineeDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.ChangePasswordDTO;
import com.example.demo.dto.TraineeCreateDTO;
import com.example.demo.dto.TraineeUpdateDTO;
import com.example.demo.model.Trainee;
import com.example.demo.model.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
public class TraineeServiceTest {

}
