package com.example.demo.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.demo.dto.response.TrainingTypeResponseDTO;
import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.security.JwtService;
import com.example.demo.service.TrainingTypeService;

@WebMvcTest(TrainingTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
class TrainingTypeControllerTest {

    @MockitoBean
    private TrainingTypeService trainingTypeService;

    @Mock
    private JwtService jwtService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll_ShouldReturn_200() throws Exception {

        List<TrainingTypeResponseDTO> mockResponse = Arrays.asList(
                new TrainingTypeResponseDTO(UUID.randomUUID(), "Strength"),
                new TrainingTypeResponseDTO(UUID.randomUUID(), "Cardio"));
        when(trainingTypeService.getAll()).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/training-types")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtService.generateToken("a.a")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(mockResponse.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainingTypeName").value("Strength"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].trainingTypeName").value("Cardio"));

        verify(trainingTypeService, times(1)).getAll();
    }
}
