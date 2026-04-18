package com.example.InsurenceBackend.service;

import java.util.List;
import com.example.InsurenceBackend.model.User;

public interface UserService {
    List<User> findByRole(String role);
    List<User> findAll();
    User saveUser(User user);
}
