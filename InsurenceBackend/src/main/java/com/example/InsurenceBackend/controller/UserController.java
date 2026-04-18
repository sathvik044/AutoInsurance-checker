package com.example.InsurenceBackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.InsurenceBackend.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.InsurenceBackend.model.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestParam(name = "role", required = false) String role) {
        if (role != null && !role.isEmpty()) {
            return ResponseEntity.ok(userService.findByRole(role));
        }
        return ResponseEntity.ok(userService.findAll());
    }

     @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }
}
