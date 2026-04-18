package com.example.InsurenceBackend.Dtos;
import com.example.InsurenceBackend.model.ClaimDocument;

public class ClaimDocumentMapper {

    public static ClaimDocumentResponseDto toDto(ClaimDocument doc) {

        return ClaimDocumentResponseDto.builder()
                .id(doc.getId())
                .claimId(doc.getClaim() != null ? doc.getClaim().getId() : null)
                .documentType(doc.getDocumentType())
                .documentUrl(doc.getDocumentUrl())
                .uploadedById(doc.getUploadedBy() != null ? doc.getUploadedBy().getId() : null)
                .uploadedByName(doc.getUploadedBy() != null ? doc.getUploadedBy().getName() : null)
                .verificationStatus(doc.getVerificationStatus())
                .uploadedAt(doc.getUploadedAt())
                .verifiedAt(doc.getVerifiedAt())
                .build();
    }

    public static ClaimDocument toEntity(ClaimDocumentRequestDto dto) {
        if (dto == null) {
            return null;
        }
        ClaimDocument doc = new ClaimDocument();
        doc.setDocumentType(dto.getDocumentType());
        doc.setDocumentUrl(dto.getDocumentUrl());
        return doc;
    }
}