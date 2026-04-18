package com.example.InsurenceBackend.Dtos;

import java.time.LocalDateTime;

import com.example.InsurenceBackend.enums.ClaimDocVerificationStatus;
import com.example.InsurenceBackend.enums.DocumentType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClaimDocumentResponseDto {

    private Long id;
    private Long claimId;

    private DocumentType documentType;
    private String documentUrl;

    private Long uploadedById;
    private String uploadedByName;

    private ClaimDocVerificationStatus verificationStatus;

    private LocalDateTime uploadedAt;
    private LocalDateTime verifiedAt;
}