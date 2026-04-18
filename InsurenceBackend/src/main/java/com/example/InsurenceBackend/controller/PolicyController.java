package com.example.InsurenceBackend.controller;

import com.example.InsurenceBackend.service.PolicyService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.InsurenceBackend.Dtos.PolicyRequest;
import com.example.InsurenceBackend.Dtos.*;


import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/policies")
@CrossOrigin(origins = "*")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping
    public ResponseEntity<List<PolicyResponse>> getPolicies(
            @RequestParam(name = "userId", required = false) Long userId) {

        if (userId != null) {
            return ResponseEntity.ok(policyService.findByUserId(userId));
        }
        return ResponseEntity.ok(policyService.findAll());
    }

    @PostMapping
    public ResponseEntity<PolicyResponse> createPolicy(
            @RequestBody PolicyRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(policyService.createPolicy(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponse> getPolicyById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(policyService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PolicyResponse>> getPoliciesByUserId(
            @PathVariable(name = "userId") Long userId) {

        return ResponseEntity.ok(policyService.findByUserId(userId));
    }
}
