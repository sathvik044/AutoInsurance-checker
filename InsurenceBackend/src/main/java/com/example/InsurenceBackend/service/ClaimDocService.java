package com.example.InsurenceBackend.service;

import java.util.List;
import com.example.InsurenceBackend.Dtos.ClaimDocumentRequestDto;
import com.example.InsurenceBackend.Dtos.ClaimDocumentResponseDto;
import com.example.InsurenceBackend.enums.ClaimDocVerificationStatus;

public interface ClaimDocService {
    List<ClaimDocumentResponseDto> uploadDocuments(Long claimId, List<ClaimDocumentRequestDto> docs);
    List<ClaimDocumentResponseDto> getDocumentsByClaimId(Long claimId);
    List<ClaimDocumentResponseDto> getDocumentsByUserId(Long userId);
    ClaimDocumentResponseDto verifyDocument(Long documentId, ClaimDocVerificationStatus status);
    void deleteDocument(Long documentId);
}
