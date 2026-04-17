package com.example.InsurenceBackend.service.serviceImpl;

import java.util.List;


import com.example.InsurenceBackend.model.User;

public interface UserService {
 public List<User> findByRole(String role);
 public List<User> findAll();
 public User saveUser(User user);
 
}