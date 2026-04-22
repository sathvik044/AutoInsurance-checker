package com.example.InsurenceBackend.Login.Dtos;
import com.example.InsurenceBackend.model.User;
public class LoginMapper {
    //toEntity
    public static User toEntity(User request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
    //toDto
    public static LoginResponse toDto(User user) {
        return LoginResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .message("Login successful")
                .build();
    }
    public static User toEntity(String email, String password) {
      
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }
}
