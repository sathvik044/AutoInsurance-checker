package com.example.InsurenceBackend.Login.Controller;

import com.example.InsurenceBackend.Login.Service.LoginService;
import com.example.InsurenceBackend.model.User;
import com.example.InsurenceBackend.Repository.UserRepository;
import com.example.InsurenceBackend.Login.Dtos.*;
import com.example.InsurenceBackend.Login.Dtos.LoginResponse;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;
   @Autowired   
   private UserRepository userRepository;

    // ✅ LOGIN
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return loginService.login(request.getEmail(), request.getPassword());
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        // check email exists
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists ❌");
        }

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
}