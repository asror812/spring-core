package com.example.demo.controller;


import static org.mockito.Mockito.when;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.demo.dto.request.ChangePasswordRequestDTO;
import com.example.demo.dto.request.SignInRequestDTO;
import com.example.demo.dto.request.TraineeSignUpRequestDTO;
import com.example.demo.dto.request.TrainerSignUpRequestDTO;
import com.example.demo.dto.response.SignInResponseDTO;
import com.example.demo.dto.response.SignUpResponseDTO;
import com.example.demo.security.JwtService;
import com.example.demo.service.AuthService;
import com.example.demo.service.TraineeService;
import com.example.demo.service.TrainerService;
import com.google.gson.Gson;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @MockitoBean
    private TraineeService traineeService;

    @MockitoBean
    private TrainerService trainerService;

    @MockitoBean
    private AuthService authService;

    @InjectMocks
    private Gson gson;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    private final String endpoint = "/auth";

    @Test
    void signUpTrainee_201() throws Exception {
        TraineeSignUpRequestDTO requestDTO = new TraineeSignUpRequestDTO("asror", "abror", new Date(),
                "Tashkent");

        when(traineeService
                .register(Mockito.any(TraineeSignUpRequestDTO.class)))
                .thenReturn(new SignUpResponseDTO("asror.abror", "1234567890", "a"));

        mockMvc.perform(MockMvcRequestBuilders
                .post(endpoint + "/trainees/sign-up")
                .header("Authorization", "Bearer " + jwtService.generateToken("a.a"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(requestDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("a"));
    }

    @Test
    void signUpTrainee_400() throws Exception {
        TraineeSignUpRequestDTO requestDTO = new TraineeSignUpRequestDTO("", "", new Date(), "T");

        mockMvc.perform(MockMvcRequestBuilders
                .post(endpoint + "/trainees/sign-up")
                .header("Authorization", "Bearer " + jwtService.generateToken("a.a"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(requestDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void changePassword_200() throws Exception {
        ChangePasswordRequestDTO requestDTO = new ChangePasswordRequestDTO("a.a", "qwerty12345", "12345678910");

        mockMvc.perform(MockMvcRequestBuilders
                .put(endpoint + "/change-password")
                .header("Authorization", "Bearer " + jwtService.generateToken("a.a"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void changePassword_400() throws Exception {
        ChangePasswordRequestDTO requestDTO = new ChangePasswordRequestDTO("", "", "123456");

        mockMvc.perform(MockMvcRequestBuilders
                .put(endpoint + "/change-password")
                .header("Authorization", "Bearer " + jwtService.generateToken("a.a"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void signIn_200() throws Exception {
        SignInRequestDTO requestDTO = new SignInRequestDTO("a.a", "qwerty12345");
        when(authService.login(Mockito.any(SignInRequestDTO.class))).thenReturn(new SignInResponseDTO("a"));

        mockMvc.perform(MockMvcRequestBuilders.post(endpoint + "/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(requestDTO))
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtService.generateToken("a.a")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token")
                        .value("a"));
    }

    @Test
    void signUpTrainer_201() throws Exception {
        TrainerSignUpRequestDTO requestDTO = new TrainerSignUpRequestDTO("a", "a", UUID.randomUUID());

        when(trainerService.register(
                Mockito.any(TrainerSignUpRequestDTO.class)))
                .thenReturn(new SignUpResponseDTO("a.a", "1234567890000", "a"));

        mockMvc.perform(MockMvcRequestBuilders
                .post(endpoint + "/trainers/sign-up")
                .header("Authorization", "Bearer " + jwtService.generateToken("a.a"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(requestDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token")
                        .value("a"));
    }

    @Test
    void signUpTrainer_400() throws Exception {
            TrainerSignUpRequestDTO requestDTO = new TrainerSignUpRequestDTO("", "", UUID.randomUUID());

            mockMvc.perform(MockMvcRequestBuilders.post(endpoint + "/trainers/sign-up")
                            .content(gson.toJson(requestDTO))
                            .header("Authorization", "Bearer " + jwtService.generateToken("a.a"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andDo(MockMvcResultHandlers.print())
                            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    
}
