package com.example.InsurenceBackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.InsurenceBackend.model.ClaimTransaction;
import com.example.InsurenceBackend.service.ClaimTransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/claim-transactions")
@RequiredArgsConstructor
public class ClaimTransactionController {

    private final ClaimTransactionService claimTransactionService;

    @GetMapping("/{id}")
    public ResponseEntity<ClaimTransaction> getClaimTransaction(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(claimTransactionService.getClaimTransaction(id));
    }

    @GetMapping
    public ResponseEntity<List<ClaimTransaction>> getAllClaimTransactions() {
        return ResponseEntity.ok(claimTransactionService.getAllClaimTransactions());
    }
}
