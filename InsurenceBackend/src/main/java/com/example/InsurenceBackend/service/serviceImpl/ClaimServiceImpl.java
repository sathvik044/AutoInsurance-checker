package com.example.InsurenceBackend.service.serviceImpl;

import com.example.InsurenceBackend.Dtos.ClaimDocumentRequestDto;
import com.example.InsurenceBackend.Dtos.ClaimDocumentResponseDto;
import com.example.InsurenceBackend.Exception.BadRequestException;
import com.example.InsurenceBackend.Exception.ClaimNotFoundException;
import com.example.InsurenceBackend.Repository.ClaimInsurenceRepository;
import com.example.InsurenceBackend.enums.ClaimStatus;
import com.example.InsurenceBackend.model.ClaimInsurence;
import com.example.InsurenceBackend.service.ClaimDocService;
import com.example.InsurenceBackend.service.ClaimService;

import lombok.RequiredArgsConstructor;

import java.util.*;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimInsurenceRepository claimRepo;
    private final ClaimDocService claimDocService;

    @Override
    public ClaimInsurence findById(Long id){
        return claimRepo.findById(id)
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found with id: " + id));

    }
    @Override
    public List<ClaimInsurence> findByClaimantId(Long claimantId){
        return claimRepo.findByClaimantId(claimantId);
    }
    @Override
    public List<ClaimInsurence> findAll(){
        return claimRepo.findAll();
    }   

    @Override
    public ClaimInsurence createClaim(ClaimInsurence claim) {
        claim.setStatus(ClaimStatus.CREATED);
        return claimRepo.save(claim);
    }

    
    @Override
    public ClaimInsurence submitClaim(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.CREATED) {
            throw new BadRequestException("Only CREATED claims can be submitted");
        }

        claim.setStatus(ClaimStatus.SUBMITTED);
        return claimRepo.save(claim);
    }

    @Override
    public ClaimInsurence verifyDocuments(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.SUBMITTED) {
            throw new BadRequestException("Invalid state for verification");
        }

        claim.setStatus(ClaimStatus.DOCUMENT_VERIFICATION);
        return claimRepo.save(claim);
    }

    @Override
    public ClaimInsurence moveToReview(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.DOCUMENT_VERIFICATION) {
            throw new BadRequestException("Invalid state for review");
        }

        claim.setStatus(ClaimStatus.UNDER_REVIEW);
        return claimRepo.save(claim);
    }

    @Override
    public ClaimInsurence autoValidate(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.UNDER_REVIEW) {
            throw new BadRequestException("Invalid state for auto validation");
        }

        claim.setStatus(ClaimStatus.AUTO_VALIDATED);
        return claimRepo.save(claim);
    }

    @Override
    public ClaimInsurence adjusterApprove(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.AUTO_VALIDATED) {
            throw new BadRequestException("Invalid state for adjuster approval");
        }

        claim.setStatus(ClaimStatus.ADJUSTER_APPROVED);
        return claimRepo.save(claim);
    }

    @Override
    public ClaimInsurence managerApprove(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.ADJUSTER_APPROVED) {
            throw new BadRequestException("Invalid state for manager approval");
        }

        claim.setStatus(ClaimStatus.MANAGER_APPROVAL);
        return claimRepo.save(claim);
    }

   
    @Override
    public ClaimInsurence approve(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.MANAGER_APPROVAL) {
            throw new BadRequestException("Invalid state for approval");
        }

        claim.setStatus(ClaimStatus.APPROVED);
        return claimRepo.save(claim);
    }

    @Override
    public ClaimInsurence settle(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.APPROVED) {
            throw new BadRequestException("Invalid state for settlement");
        }

        claim.setStatus(ClaimStatus.SETTLED);
        return claimRepo.save(claim);
    }

    @Override
    public ClaimInsurence reject(Long id) {
        ClaimInsurence claim = getClaim(id);

        claim.setStatus(ClaimStatus.REJECTED);
        return claimRepo.save(claim);
    }

    private ClaimInsurence getClaim(Long id) {
        return claimRepo.findById(id)
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found with id: " + id));
    }
     @Override
    public List<ClaimDocumentResponseDto> uploadDocuments(Long claimId, List<ClaimDocumentRequestDto> docs) {
        return claimDocService.uploadDocuments(claimId, docs);
    }
    
}