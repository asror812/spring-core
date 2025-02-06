package com.example.demo.controller;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.demo.dto.request.TraineeUpdateRequestDTO;
import com.example.demo.dto.request.TrainerUpdateRequestDTO;
import com.example.demo.dto.response.TrainerResponseDTO;
import com.example.demo.dto.response.TrainingTypeResponseDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.security.JwtService;
import com.example.demo.service.TrainerService;
import com.google.gson.Gson;

@SpringBootTest
@AutoConfigureMockMvc
class TrainerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TrainerService trainerService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private Gson gson;

    private final String endpoint = "/trainers";

    @Test
    void getProfile_200() throws Exception {
        TrainerResponseDTO responseDTO = new TrainerResponseDTO(
                new UserResponseDTO("asror", "r", true),
                new TrainingTypeResponseDTO(UUID.randomUUID(), "Swimming"));

        when(trainerService.findByUsername("asror.r")).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(MockMvcRequestBuilders
                .get(endpoint + "/profiles/{username}", "asror.r")
                .header("Authorization", "Bearer " + jwtService.generateToken("a.a"))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.firstName").value("asror"));
    }

    @Test
    void getProfile_404() throws Exception {
        when(trainerService.findByUsername("asror.r")).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders
                .get(endpoint + "/profiles/{username}", "asror.r")
                .header("Authorization", "Bearer " + jwtService.generateToken("asror.r"))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void status_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .patch(endpoint + "/{username}", "asror")
                .header("Authorization", "Bearer " + jwtService.generateToken("asror"))
                .accept(MediaType.APPLICATION_JSON)
                .param("status", "true"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void status_400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .patch(endpoint + "/{username}", "asror")
                .header("Authorization", "Bearer " + jwtService.generateToken("asror"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void update_400() throws Exception {

        TraineeUpdateRequestDTO updateDTO = new TraineeUpdateRequestDTO(
                "a.a", "", "", null, new Date(), "T");

        mockMvc.perform(MockMvcRequestBuilders
                .put(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtService.generateToken("q.q"))
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void update_200() throws Exception {
        TrainerUpdateRequestDTO updateDTO = new TrainerUpdateRequestDTO(
                "a.a", "a", "a", true, UUID.randomUUID());

        mockMvc.perform(MockMvcRequestBuilders
                .put(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtService.generateToken("a.a"))
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
