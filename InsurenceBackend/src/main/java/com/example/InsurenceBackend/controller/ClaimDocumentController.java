package com.example.InsurenceBackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.InsurenceBackend.Dtos.ClaimDocumentRequestDto;
import com.example.InsurenceBackend.Dtos.ClaimDocumentResponseDto;
import com.example.InsurenceBackend.enums.ClaimDocVerificationStatus;
import com.example.InsurenceBackend.service.ClaimDocService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ClaimDocumentController {

    private final ClaimDocService claimDocService;

    @PostMapping("/claim/{claimId}")
    public ResponseEntity<List<ClaimDocumentResponseDto>> uploadDocuments(
            @PathVariable(name = "claimId") Long claimId,
            @RequestBody List<ClaimDocumentRequestDto> docs) {
        return ResponseEntity.ok(claimDocService.uploadDocuments(claimId, docs));
    }

    @GetMapping("/claim/{claimId}")
    public ResponseEntity<List<ClaimDocumentResponseDto>> getDocumentsByClaimId(@PathVariable(name = "claimId") Long claimId) {
        return ResponseEntity.ok(claimDocService.getDocumentsByClaimId(claimId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClaimDocumentResponseDto>> getDocumentsByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(claimDocService.getDocumentsByUserId(userId));
    }

    @PutMapping("/{documentId}/verify")
    public ResponseEntity<ClaimDocumentResponseDto> verifyDocument(
            @PathVariable(name = "documentId") Long documentId,
            @RequestParam(name = "status") ClaimDocVerificationStatus status) {
        return ResponseEntity.ok(claimDocService.verifyDocument(documentId, status));
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable(name = "documentId") Long documentId) {
        claimDocService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }
}
