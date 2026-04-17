package com.example.InsurenceBackend.Dtos;
import com.example.InsurenceBackend.enums.PolicyStatus;
import com.example.InsurenceBackend.model.Policy;
import com.example.InsurenceBackend.model.User;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class PolicyMapper {

    private static LocalDateTime parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            // This handles both offset-aware (Z, +05:30) and local date times
            return ZonedDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME).toLocalDateTime();
        } catch (Exception e) {
            try {
                return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (Exception e2) {
                return null;
            }
        }
    }

    public static Policy toEntity(PolicyRequest request, User user) {
        if (request == null) return null;
        
        return Policy.builder()
                .policyType(request.getPolicyType())
                .premiumAmount(request.getPremiumAmount())
                .startDate(parseDate(request.getStartDate()))
                .endDate(parseDate(request.getEndDate()))
                .policyholder(user)
                .status(PolicyStatus.ACTIVATE)
                .build();
    }

    public static PolicyResponse toDto(Policy policy) {
        if (policy == null) return null;
        return PolicyResponse.builder()
                .id(policy.getId())
                .policyholderId(policy.getPolicyholder() != null ? policy.getPolicyholder().getId() : null)
                .policyType(policy.getPolicyType() != null ? policy.getPolicyType().name() : null)
                .premiumAmount(policy.getPremiumAmount())
                .startDate(policy.getStartDate() != null ? policy.getStartDate().toString() : null)
                .endDate(policy.getEndDate() != null ? policy.getEndDate().toString() : null)
                .status(policy.getStatus() != null ? policy.getStatus().name() : null)
                .build();
    }
}

