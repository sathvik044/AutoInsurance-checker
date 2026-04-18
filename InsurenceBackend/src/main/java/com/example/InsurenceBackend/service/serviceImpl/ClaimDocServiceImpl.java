package com.example.InsurenceBackend.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.InsurenceBackend.Dtos.ClaimDocumentMapper;
import com.example.InsurenceBackend.Dtos.ClaimDocumentRequestDto;
import com.example.InsurenceBackend.Dtos.ClaimDocumentResponseDto;
import com.example.InsurenceBackend.Exception.ClaimDocNotFoundException;
import com.example.InsurenceBackend.Exception.ClaimNotFoundException;
import com.example.InsurenceBackend.Exception.UserNotFoundException;
import com.example.InsurenceBackend.Repository.ClaimDocumentRepository;
import com.example.InsurenceBackend.Repository.ClaimInsurenceRepository;
import com.example.InsurenceBackend.Repository.UserRepository;
import com.example.InsurenceBackend.enums.ClaimDocVerificationStatus;
import com.example.InsurenceBackend.enums.ClaimStatus;
import com.example.InsurenceBackend.model.ClaimDocument;
import com.example.InsurenceBackend.model.ClaimInsurence;
import com.example.InsurenceBackend.model.User;
import com.example.InsurenceBackend.service.ClaimDocService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClaimDocServiceImpl implements ClaimDocService {

    private final ClaimDocumentRepository claimDocRepo;
    private final ClaimInsurenceRepository claimRepo;
    private final UserRepository userRepo;

    @Override
    public List<ClaimDocumentResponseDto> uploadDocuments(Long claimId, List<ClaimDocumentRequestDto> docs) {
        ClaimInsurence claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found with id: " + claimId));

        List<ClaimDocument> entities = docs.stream().map(dto -> {
            ClaimDocument doc = ClaimDocumentMapper.toEntity(dto);
            doc.setClaim(claim);
            doc.setUploadedAt(LocalDateTime.now());
            doc.setVerificationStatus(ClaimDocVerificationStatus.PENDING);
            
            if (dto.getUploadedById() != null) {
                User user = userRepo.findById(dto.getUploadedById())
                        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + dto.getUploadedById()));
                doc.setUploadedBy(user);
            }
            return doc;
        }).collect(Collectors.toList());

        List<ClaimDocument> savedDocs = claimDocRepo.saveAll(entities);

        if (claim.getStatus() == ClaimStatus.CREATED) {
            claim.setStatus(ClaimStatus.SUBMITTED);
            claimRepo.save(claim);
        }

        return savedDocs.stream()
                .map(ClaimDocumentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaimDocumentResponseDto> getDocumentsByClaimId(Long claimId) {
        return claimDocRepo.findByClaimId(claimId).stream()
                .map(ClaimDocumentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClaimDocumentResponseDto verifyDocument(Long documentId, ClaimDocVerificationStatus status) {
        ClaimDocument doc = claimDocRepo.findById(documentId)
                .orElseThrow(() -> new ClaimDocNotFoundException("Document not found with id: " + documentId));
        
        doc.setVerificationStatus(status);
        doc.setVerifiedAt(LocalDateTime.now());
        
        return ClaimDocumentMapper.toDto(claimDocRepo.save(doc));
    }
    @Override
    public List<ClaimDocumentResponseDto> getDocumentsByUserId(Long userId) {
        return claimDocRepo.findByUploadedById(userId).stream()
                .map(ClaimDocumentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDocument(Long documentId) {
        if (!claimDocRepo.existsById(documentId)) {
            throw new ClaimDocNotFoundException("Document not found with id: " + documentId);
        }
        claimDocRepo.deleteById(documentId);
    }
}
