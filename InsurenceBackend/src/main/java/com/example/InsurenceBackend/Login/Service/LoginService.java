package com.example.InsurenceBackend.Login.Service;

import org.springframework.stereotype.Service;
import com.example.InsurenceBackend.Login.Dtos.LoginResponse;
import com.example.InsurenceBackend.model.User;
import com.example.InsurenceBackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.InsurenceBackend.config.JwtUtil;     

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse login(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found ❌");
        }

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password ❌");
        }

        // ✅ GENERATE TOKEN
        String roleStr = user.getRole() != null ? user.getRole().name() : "USER";
        String token = jwtUtil.generateToken(user.getEmail(), roleStr);

        return LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .message("Login successful")
                .token(token)
                .role(roleStr)
                .build();
    }
}