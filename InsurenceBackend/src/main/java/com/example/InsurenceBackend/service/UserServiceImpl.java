package com.example.InsurenceBackend.service;
import java.util.List;
import com.example.InsurenceBackend.service.serviceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.InsurenceBackend.Repository.UserRepository;
import com.example.InsurenceBackend.model.User;
import com.example.InsurenceBackend.enums.UserRole;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public List<User> findByRole(String role){
        if (role == null || role.isEmpty()) {
            return userRepo.findAll();
        }
        try {
            UserRole userRole = UserRole.valueOf(role.toUpperCase());
            return userRepo.findByRole(userRole);
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<User> findAll(){
        return userRepo.findAll();
    }

    @Override
    public User saveUser(User user) {
        user.setId(null); 
        user.setCreatedAt(java.time.LocalDateTime.now());
        return userRepo.save(user);
    }
}
