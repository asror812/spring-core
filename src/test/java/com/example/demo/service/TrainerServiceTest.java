package com.example.demo.service;

import com.example.demo.dao.TrainerDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.dao.TrainingTypeDAO;
import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.ChangePasswordDTO;
import com.example.demo.dto.TrainerCreateDTO;
import com.example.demo.dto.TrainerUpdateDTO;
import com.example.demo.model.Trainer;
import com.example.demo.model.TrainingType;
import com.example.demo.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class TrainerServiceTest {

}