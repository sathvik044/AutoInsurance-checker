package com.example.InsurenceBackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.example.InsurenceBackend.enums.PolicyStatus;
import com.example.InsurenceBackend.enums.PolicyType;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "policies")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "policyholder_id")
    @JsonIgnore
    private User policyholder;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User assignedManager;

    @Enumerated(EnumType.STRING)
    private PolicyType policyType;
    private double premiumAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)    
    private PolicyStatus status;
    
}
