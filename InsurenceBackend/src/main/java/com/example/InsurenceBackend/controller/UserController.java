package com.example.InsurenceBackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.InsurenceBackend.service.serviceImpl.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.InsurenceBackend.model.User;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
@RequestMapping("/api/users")


public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping
public List<User> getUsers(@RequestParam(required = false) String role) {
    if (role != null && !role.isEmpty()) {
        return userService.findByRole(role);
    }
    return userService.findAll();
    }

     @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}