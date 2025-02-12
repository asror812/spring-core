package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.request.ChangePasswordRequestDTO;
import com.example.demo.dto.request.SignInRequestDTO;
import com.example.demo.dto.request.SignUpRequestDTO;
import com.example.demo.dto.response.SignInResponseDTO;
import com.example.demo.dto.response.SignUpResponseDTO;
import com.example.demo.exceptions.InvalidCredentialsException;
import com.example.demo.model.User;
import com.example.demo.security.JwtService;
import com.example.demo.utils.PasswordGeneratorUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDAO userDAO;
    private final UsernameGeneratorService usernameGeneratorService;
    private final JwtService jwtService;

    private static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password: %s | %s";

    public SignUpResponseDTO register(SignUpRequestDTO requestDTO) {
        String username = usernameGeneratorService.generateUsername(requestDTO.getFirstName(),
                requestDTO.getLastName());
        String token = jwtService.generateToken(username);
        String password = PasswordGeneratorUtil.generate();

        return new SignUpResponseDTO(username, password, token);
    }

    @Transactional
    public void changePassword(ChangePasswordRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        String oldPassword = requestDTO.getOldPassword();

        User user = userDAO.findByUsernameAndPassword(username, oldPassword)
                .orElseThrow(() -> new InvalidCredentialsException(
                        INVALID_USERNAME_OR_PASSWORD.formatted(username, oldPassword)));

        user.setPassword(requestDTO.getNewPassword());
        userDAO.update(user);
    }

    public SignInResponseDTO login(SignInRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        String oldPassword = requestDTO.getPassword();

        userDAO.findByUsernameAndPassword(username, oldPassword)
                .orElseThrow(() -> new InvalidCredentialsException(
                        INVALID_USERNAME_OR_PASSWORD.formatted(username, oldPassword)));

        String token = jwtService.generateToken(requestDTO.getUsername());

        return new SignInResponseDTO(token);
    }
}
