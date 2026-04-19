package com.example.InsurenceBackend.service;

import com.example.InsurenceBackend.Dtos.ClaimDocumentRequestDto;
import com.example.InsurenceBackend.Dtos.ClaimDocumentResponseDto;
import com.example.InsurenceBackend.Dtos.ClaimRequestDto;
import com.example.InsurenceBackend.Dtos.ClaimResponseDto;
import java.util.List;

public interface ClaimService {

    ClaimResponseDto createClaim(ClaimRequestDto claim);
    ClaimResponseDto findById(Long id);
    List<ClaimResponseDto> findByClaimantId(Long claimantId);
    List<ClaimResponseDto> findAll();
    ClaimResponseDto verifyDocuments(Long id);
    ClaimResponseDto autoValidate(Long id);
    ClaimResponseDto adjusterApprove(Long id);
    ClaimResponseDto managerApprove(Long id);
    ClaimResponseDto moveToReview(Long id);
    ClaimResponseDto approve(Long id);          
    ClaimResponseDto settle(Long id);   
    ClaimResponseDto reject(Long id);
    ClaimResponseDto submitClaim(Long id);
    List<ClaimDocumentResponseDto> uploadDocuments(Long claimId, List<ClaimDocumentRequestDto> docs);
}