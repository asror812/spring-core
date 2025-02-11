package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.dto.request.ChangePasswordRequestDTO;
import com.example.demo.dto.request.SignInRequestDTO;
import com.example.demo.dto.request.SignUpRequestDTO;
import com.example.demo.dto.response.SignInResponseDTO;
import com.example.demo.dto.response.SignUpResponseDTO;

@Service
public interface AuthService {
    public SignInResponseDTO login(SignInRequestDTO requestDTO);

    public void changePassword(ChangePasswordRequestDTO requestDTO);

    public SignUpResponseDTO register(SignUpRequestDTO requestDTO);
}