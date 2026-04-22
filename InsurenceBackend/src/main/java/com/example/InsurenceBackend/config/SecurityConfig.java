package com.example.InsurenceBackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
@Autowired
   private JwtFilter jwtFilter;

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests(auth -> auth

            // ✅ Preflight
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            // ✅ Public APIs
            .requestMatchers("/", "/api/login", "/api/register", "/api/home").permitAll()

            // 👑 ADMIN only
            .requestMatchers("/api/admin/**").hasRole("ADMIN")

            // 👤 USER + ADMIN
            .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")

            // policyHolder
            .requestMatchers("/api/policyHolder/**").hasAnyRole("POLICYHOLDER", "ADMIN")
            
            //ClaimAdjustert
            .requestMatchers("/api/claimAdjuster/**").hasAnyRole("CLAIMADJUSTER", "ADMIN")

            //FraudAnalyst
            .requestMatchers("/api/fraudAnalyst/**").hasAnyRole("FRAUDANALYST", "ADMIN")

            //Manager
            .requestMatchers("/api/manager/**").hasAnyRole("MANAGER", "ADMIN")

            // 🔐 Everything else
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
}
