package com.example.InsurenceBackend.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.InsurenceBackend.enums.UserRole;
import com.example.InsurenceBackend.model.User;
public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findByRole(UserRole role);
}
