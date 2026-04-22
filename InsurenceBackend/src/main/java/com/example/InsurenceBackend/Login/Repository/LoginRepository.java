package com.example.InsurenceBackend.Login.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.InsurenceBackend.model.User;

public interface LoginRepository extends JpaRepository<User, Long> {
    
    
}
