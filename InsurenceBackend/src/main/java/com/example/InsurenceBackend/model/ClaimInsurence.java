package com.example.InsurenceBackend.model;
import jakarta.persistence.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import  java.time.LocalDateTime;

import com.example.InsurenceBackend.enums.ClaimStatus;
import com.example.InsurenceBackend.enums.ClaimType;

import lombok.Data;

@Data
@Entity
@Table(name = "claims")
public class ClaimInsurence {
    
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy insurancePolicy;
    @ManyToOne
    @JoinColumn(name = "claimant_id")
    private User claimant;
    @Enumerated(EnumType.STRING)
    private ClaimType claimType;
    private String description;
    private LocalDateTime incidentDate;
    private LocalDateTime filingDate;
    @Enumerated(EnumType.STRING)
    private ClaimStatus status;
    @ManyToOne
    @JoinColumn(name = "adjuster_id")
    private User adjuster;
    private double approvedAmount;
    private LocalDateTime settledAt;


}
