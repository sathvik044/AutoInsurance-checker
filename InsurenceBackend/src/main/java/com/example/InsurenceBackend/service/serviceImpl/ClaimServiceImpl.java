package com.example.InsurenceBackend.service.serviceImpl;

import com.example.InsurenceBackend.Dtos.*;
import com.example.InsurenceBackend.Exception.BadRequestException;
import com.example.InsurenceBackend.Exception.ClaimNotFoundException;
import com.example.InsurenceBackend.Exception.PolicyNotFoundException;
import com.example.InsurenceBackend.Exception.UserNotFoundException;
import com.example.InsurenceBackend.Repository.ClaimInsurenceRepository;
import com.example.InsurenceBackend.Repository.PolicyRepository;
import com.example.InsurenceBackend.Repository.UserRepository;
import com.example.InsurenceBackend.enums.ClaimStatus;
import com.example.InsurenceBackend.model.ClaimInsurence;
import com.example.InsurenceBackend.model.Policy;
import com.example.InsurenceBackend.model.User;
import com.example.InsurenceBackend.service.ClaimDocService;
import com.example.InsurenceBackend.service.ClaimService;

import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimInsurenceRepository claimRepo;
    private final PolicyRepository policyRepo;
    private final UserRepository userRepo;
    private final ClaimDocService claimDocService;

    @Override
    public ClaimResponseDto findById(Long id){
        ClaimInsurence claim = getClaim(id);
        return ClaimMapper.toDto(claim);
    }

    @Override
    public List<ClaimResponseDto> findByClaimantId(Long claimantId){
        return claimRepo.findByClaimantId(claimantId).stream()
                .map(ClaimMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaimResponseDto> findAll(){
        return claimRepo.findAll().stream()
                .map(ClaimMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClaimResponseDto createClaim(ClaimRequestDto claimDto) {
        Policy policy = policyRepo.findById(claimDto.getPolicyId())
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found with id: " + claimDto.getPolicyId()));
        
        User claimant = userRepo.findById(claimDto.getClaimantId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + claimDto.getClaimantId()));

        ClaimInsurence claim = ClaimMapper.toEntity(claimDto, policy, claimant);
        claim.setStatus(ClaimStatus.CREATED);
        
        return ClaimMapper.toDto(claimRepo.save(claim));
    }

    
    @Override
    public ClaimResponseDto submitClaim(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.CREATED) {
            throw new BadRequestException("Only CREATED claims can be submitted");
        }

        claim.setStatus(ClaimStatus.SUBMITTED);
        return ClaimMapper.toDto(claimRepo.save(claim));
    }

    @Override
    public ClaimResponseDto verifyDocuments(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.SUBMITTED) {
            throw new BadRequestException("Invalid state for verification");
        }

        claim.setStatus(ClaimStatus.DOCUMENT_VERIFICATION);
        return ClaimMapper.toDto(claimRepo.save(claim));
    }

    @Override
    public ClaimResponseDto moveToReview(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.DOCUMENT_VERIFICATION) {
            throw new BadRequestException("Invalid state for review");
        }

        claim.setStatus(ClaimStatus.UNDER_REVIEW);
        return ClaimMapper.toDto(claimRepo.save(claim));
    }

    @Override
    public ClaimResponseDto autoValidate(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.UNDER_REVIEW) {
            throw new BadRequestException("Invalid state for auto validation");
        }

        claim.setStatus(ClaimStatus.AUTO_VALIDATED);
        return ClaimMapper.toDto(claimRepo.save(claim));
    }

    @Override
    public ClaimResponseDto adjusterApprove(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.AUTO_VALIDATED) {
            throw new BadRequestException("Invalid state for adjuster approval");
        }

        claim.setStatus(ClaimStatus.ADJUSTER_APPROVED);
        return ClaimMapper.toDto(claimRepo.save(claim));
    }

    @Override
    public ClaimResponseDto managerApprove(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.ADJUSTER_APPROVED) {
            throw new BadRequestException("Invalid state for manager approval");
        }

        claim.setStatus(ClaimStatus.MANAGER_APPROVAL);
        return ClaimMapper.toDto(claimRepo.save(claim));
    }

   
    @Override
    public ClaimResponseDto approve(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.MANAGER_APPROVAL) {
            throw new BadRequestException("Invalid state for approval");
        }

        claim.setStatus(ClaimStatus.APPROVED);
        return ClaimMapper.toDto(claimRepo.save(claim));
    }

    @Override
    public ClaimResponseDto settle(Long id) {
        ClaimInsurence claim = getClaim(id);

        if (claim.getStatus() != ClaimStatus.APPROVED) {
            throw new BadRequestException("Invalid state for settlement");
        }

        claim.setStatus(ClaimStatus.SETTLED);
        return ClaimMapper.toDto(claimRepo.save(claim));
    }

    @Override
    public ClaimResponseDto reject(Long id) {
        ClaimInsurence claim = getClaim(id);

        claim.setStatus(ClaimStatus.REJECTED);
        return ClaimMapper.toDto(claimRepo.save(claim));
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