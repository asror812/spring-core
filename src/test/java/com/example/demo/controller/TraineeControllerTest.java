package com.example.demo.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.service.TraineeService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
public class TraineeControllerTest {

    @MockitoBean
    private TraineeService traineeService;

    private final String ENDPOINT = "/trainee";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testDelete() {
         
    }

    @Test
    void testGetNotAssignedTrainers() {

    }

    @Test
    void testGetProfile() throws Exception {
        when(traineeService.findByUsername("qwerty")).thenThrow(new EntityNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/profile/qwerty"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testStatus() {
         
        // when(traineeService.setStatus("asror" , true).thenReturn();   
    }

    @Test
    void testUpdate() {

    }
}
