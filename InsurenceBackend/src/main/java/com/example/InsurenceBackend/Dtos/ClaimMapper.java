package com.example.InsurenceBackend.Dtos;

import java.time.LocalDateTime;

import com.example.InsurenceBackend.model.ClaimInsurence;
import com.example.InsurenceBackend.model.Policy;
import com.example.InsurenceBackend.model.User;

public class ClaimMapper {
    public static ClaimInsurence toEntity(ClaimRequestDto dto, Policy policy, User claimant) {

    return ClaimInsurence.builder()
            .insurancePolicy(policy)
            .claimant(claimant)
            .claimType(dto.getClaimType())
            .description(dto.getDescription())
            .incidentDate(dto.getIncidentDate())
            .filingDate(LocalDateTime.now())
            .build();
}

public static ClaimResponseDto toDto(ClaimInsurence claim) {

    return ClaimResponseDto.builder()
            .id(claim.getId())
            .policyId(claim.getInsurancePolicy().getId())
            .claimantId(claim.getClaimant().getId())
            .claimType(claim.getClaimType())
            .description(claim.getDescription())
            .incidentDate(claim.getIncidentDate())
            .filingDate(claim.getFilingDate())
            .status(claim.getStatus())
            .adjusterId(
                claim.getAdjuster() != null ? claim.getAdjuster().getId() : null
            )
            .approvedAmount(claim.getApprovedAmount())
            .settledAt(claim.getSettledAt())
            .build();
}
}
