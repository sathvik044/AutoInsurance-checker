package com.example.InsurenceBackend.Dtos;

import java.time.LocalDateTime;
import lombok.Builder;

import com.example.InsurenceBackend.enums.ClaimType;

import lombok.Data;
@Builder
@Data
public class ClaimRequestDto {
    private Long policyId;
    private Long claimantId;
    private ClaimType claimType;
    private String description;
    private String incidentDate;
}
    

