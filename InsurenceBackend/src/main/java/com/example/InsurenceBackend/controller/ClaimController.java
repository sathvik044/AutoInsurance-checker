package com.example.InsurenceBackend.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.InsurenceBackend.Dtos.*;
import com.example.InsurenceBackend.Dtos.ClaimDocumentResponseDto;
import com.example.InsurenceBackend.model.ClaimInsurence;
import com.example.InsurenceBackend.service.ClaimService;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "*")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    // CREATE CLAIM
    @PostMapping
    public ResponseEntity<ClaimInsurence> createClaim(@RequestBody ClaimInsurence claim) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(claimService.createClaim(claim));
    }

    // GET ALL CLAIMS
    @GetMapping
    public ResponseEntity<List<ClaimInsurence>> getAllClaims() {
        return ResponseEntity.ok(claimService.findAll());
    }

    // GET CLAIM BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ClaimInsurence> getClaimById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(claimService.findById(id));
    }

    // GET CLAIMS BY USER ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClaimInsurence>> getClaimsByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(claimService.findByClaimantId(userId));
    }

    // SUBMIT CLAIM
    @PutMapping("/{id}/submit")
    public ResponseEntity<ClaimInsurence> submit(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(claimService.submitClaim(id));
    }

    // VERIFY DOCUMENTS
    @PutMapping("/{id}/verify-documents")
    public ResponseEntity<ClaimInsurence> verify(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(claimService.verifyDocuments(id));
    }

    // MOVE TO REVIEW
    @PutMapping("/{id}/review")
    public ResponseEntity<ClaimInsurence> review(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(claimService.moveToReview(id));
    }

    // AUTO VALIDATE
    @PutMapping("/{id}/auto-validate")
    public ResponseEntity<ClaimInsurence> autoValidate(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(claimService.autoValidate(id));
    }

    // ADJUSTER APPROVE
    @PutMapping("/{id}/adjuster-approve")
    public ResponseEntity<ClaimInsurence> adjusterApprove(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(claimService.adjusterApprove(id));
    }

    // MANAGER APPROVE
    @PutMapping("/{id}/manager-approve")
    public ResponseEntity<ClaimInsurence> managerApprove(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(claimService.managerApprove(id));
    }

    // FINAL APPROVAL
    @PutMapping("/{id}/approve")
    public ResponseEntity<ClaimInsurence> approve(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(claimService.approve(id));
    }

    // SETTLE CLAIM
    @PutMapping("/{id}/settle")
    public ResponseEntity<ClaimInsurence> settle(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(claimService.settle(id));
    }

    // REJECT CLAIM
    @PutMapping("/{id}/reject")
    public ResponseEntity<ClaimInsurence> reject(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(claimService.reject(id));
    }

    @PostMapping("/{id}/documents")
    public ResponseEntity<List<ClaimDocumentResponseDto>> uploadDocs(
            @PathVariable(name = "id") Long id,
            @RequestBody List<ClaimDocumentRequestDto> docs) {
        return ResponseEntity.ok(claimService.uploadDocuments(id, docs));
    }
}
