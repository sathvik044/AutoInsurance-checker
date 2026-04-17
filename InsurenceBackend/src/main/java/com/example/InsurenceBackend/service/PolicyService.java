package com.example.InsurenceBackend.service;
import java.util.List;
import com.example.InsurenceBackend.Dtos.PolicyRequest;
import com.example.InsurenceBackend.Dtos.PolicyResponse;

public interface PolicyService {
    public List<PolicyResponse> findAll();
    public PolicyResponse createPolicy(PolicyRequest request);
    public PolicyResponse findById(Long id);
    public List<PolicyResponse> findByUserId(Long userId);
}
