package com.example.InsurenceBackend.Dtos;
import com.example.InsurenceBackend.enums.PolicyType;
import lombok.Data;
@Data
public class PolicyRequest {
    private Long policyholderId;
    private Long managerId;
    private PolicyType policyType;
    private double premiumAmount;
    private String startDate;
    private String endDate;
}