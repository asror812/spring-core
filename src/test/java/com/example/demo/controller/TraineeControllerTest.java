package com.example.demo.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.example.demo.dto.response.TraineeResponseDTO;
import com.example.demo.dto.response.TrainerResponseDTO;
import com.example.demo.dto.response.TrainingTypeResponseDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.dto.response.UserUpdateResponseDTO;
import com.example.demo.exceptions.CustomException;
import com.example.demo.security.JwtService;
import com.example.demo.service.TraineeService;
import com.google.gson.Gson;
import io.jsonwebtoken.lang.Collections;

@SpringBootTest
@AutoConfigureMockMvc
class TraineeControllerTest {

    @MockitoBean
    private TraineeService traineeService;

    private final String endpoint = "/trainee";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private JwtService jwtService;

    @Test
    void testDelete_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(endpoint + "/delete/{username}", "qwerty")
                .header("Authorization", "Bearer " + jwtService.generateToken("a.a"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetNotAssignedTrainers_200() throws Exception {
        when(traineeService.getNotAssignedTrainers("a.a")).thenReturn(getNotAssignedTrainers());

        mockMvc.perform(MockMvcRequestBuilders
                .get(endpoint + "/{username}/not-assigned-trainers", "a.a")
                .header("Authorization", "Bearer " + jwtService.generateToken("a.a"))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }

    @Test
    void testGetProfile_404() throws Exception {
        when(traineeService.findByUsername("qwerty"))
                .thenThrow(new CustomException.EntityNotFoundException("Trainee", "username",
                        "qwerty"));

        mockMvc.perform(MockMvcRequestBuilders
                .get(endpoint + "/profile/{username}", "qwerty")
                .header("Authorization", "Bearer " + jwtService.generateToken("qwerty")))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testGetProfile_200() throws Exception {
        TraineeResponseDTO traineeResponseDTO = new TraineeResponseDTO(
                new UserResponseDTO("q", "q", true), new Date(), "T", Collections.emptyList());

        when(traineeService.findByUsername("q.q"))
                .thenReturn(traineeResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders
                .get(endpoint + "/profile/{username}", "q.q")
                .header("Authorization", "Bearer " + jwtService.generateToken("q.q"))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.firstName").value("q"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.lastName").value("q"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.active").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").exists());
    }

    @Test
    void testStatus_404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .patch(endpoint + "/{username}", "asror")
                .header("Authorization", "Bearer " + jwtService.generateToken("asror"))
                .accept(MediaType.APPLICATION_JSON)
                .param("status", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testStatus_400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .patch(endpoint + "/{username}", "asror")
                .header("Authorization", "Bearer " + jwtService.generateToken("asror"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testStatus_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .patch(endpoint + "/{username}", "asror")
                .header("Authorization", "Bearer " + jwtService.generateToken("asror"))
                .accept(MediaType.APPLICATION_JSON)
                .param("status", "true"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdate_400() throws Exception {

        TraineeUpdateRequestDTO updateDTO = new TraineeUpdateRequestDTO(
                "a.a", "", "", null, new Date(), "T");

                
        mockMvc.perform(MockMvcRequestBuilders
                .put(endpoint + "/update")
                .content(gson.toJson(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testUpdate_200() throws Exception {
        TraineeUpdateRequestDTO updateDTO = new TraineeUpdateRequestDTO(
                "a.a", "a", "a", true, new Date(), "T");

        mockMvc.perform(MockMvcRequestBuilders
                .put(endpoint + "/update")
                .content(gson.toJson(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private List<TrainerResponseDTO> getNotAssignedTrainers() {
        List<TrainerResponseDTO> trainerResponseDTOs = new ArrayList<>();

        trainerResponseDTOs.add(new TrainerResponseDTO(
                new UserUpdateResponseDTO("a.a", "a", "a", true),
                new TrainingTypeResponseDTO(UUID.randomUUID(), "Swimming")));

        trainerResponseDTOs.add(new TrainerResponseDTO(
                new UserUpdateResponseDTO("b", "b", "b", false),
                new TrainingTypeResponseDTO(UUID.randomUUID(), "Cycling")));

        return trainerResponseDTOs;
    }
}
