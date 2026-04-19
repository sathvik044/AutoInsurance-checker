package com.example.InsurenceBackend.Dtos;

import java.time.LocalDateTime;

import com.example.InsurenceBackend.enums.ClaimStatus;
import com.example.InsurenceBackend.enums.ClaimType;

import lombok.Data;
import lombok.Builder;
@Data
@Builder
public class ClaimResponseDto {
     private Long id;
    private Long policyId;
    private Long claimantId;
    private ClaimType claimType;
    private String description;
    private LocalDateTime incidentDate;
    private LocalDateTime filingDate;
    private ClaimStatus status;
    private Long adjusterId;
    private double approvedAmount;
    private LocalDateTime settledAt;
}
