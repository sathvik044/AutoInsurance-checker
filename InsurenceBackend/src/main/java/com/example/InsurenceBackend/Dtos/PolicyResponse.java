package com.example.InsurenceBackend.Dtos;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyResponse {
private Long id;    
private Long policyholderId;
private String policyType;
private double premiumAmount;
private String startDate;
private String endDate;
private String status;
    
}
