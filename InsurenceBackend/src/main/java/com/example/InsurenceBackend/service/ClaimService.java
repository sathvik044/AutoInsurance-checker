package com.example.InsurenceBackend.service;

import com.example.InsurenceBackend.Dtos.ClaimDocumentRequestDto;
import com.example.InsurenceBackend.Dtos.ClaimDocumentResponseDto;
import com.example.InsurenceBackend.model.ClaimInsurence;
import java.util.List;

public interface ClaimService {

    ClaimInsurence createClaim(ClaimInsurence claim);
    ClaimInsurence findById(Long id);
    List<ClaimInsurence> findByClaimantId(Long claimantId);
    List<ClaimInsurence> findAll();
    ClaimInsurence verifyDocuments(Long id);
    ClaimInsurence autoValidate(Long id);
    ClaimInsurence adjusterApprove(Long id);
    ClaimInsurence managerApprove(Long id);
    ClaimInsurence moveToReview(Long id);
    ClaimInsurence approve(Long id);        
    ClaimInsurence settle(Long id);
    ClaimInsurence reject(Long id);
    ClaimInsurence submitClaim(Long id);
    List<ClaimDocumentResponseDto> uploadDocuments(Long claimId, List<ClaimDocumentRequestDto> docs);
}