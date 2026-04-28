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
            // Handle ISO_DATE_TIME (e.g., 2023-10-27T10:30:00)
            return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            try {
                // Handle ISO_DATE (e.g., 2023-10-27)
                return java.time.LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
            } catch (Exception e2) {
                try {
                    return ZonedDateTime.parse(dateStr).toLocalDateTime();
                } catch (Exception e3) {
                    return null;
                }
            }
        }
    }

    public static Policy toEntity(PolicyRequest request, User user, User manager) {
        if (request == null) return null;
        
        return Policy.builder()
                .policyType(request.getPolicyType())
                .premiumAmount(request.getPremiumAmount())
                .startDate(parseDate(request.getStartDate()))
                .endDate(parseDate(request.getEndDate()))
                .policyholder(user)
                .assignedManager(manager)
                .status(PolicyStatus.ACTIVATE)
                .build();
    }

    public static PolicyResponse toDto(Policy policy) {
        if (policy == null) return null;
        return PolicyResponse.builder()
                .id(policy.getId())
                .policyholderId(policy.getPolicyholder() != null ? policy.getPolicyholder().getId() : null)
                .managerId(policy.getAssignedManager() != null ? policy.getAssignedManager().getId() : null)
                .managerName(policy.getAssignedManager() != null ? policy.getAssignedManager().getName() : null)
                .policyType(policy.getPolicyType() != null ? policy.getPolicyType().name() : null)
                .premiumAmount(policy.getPremiumAmount())
                .startDate(policy.getStartDate() != null ? policy.getStartDate().toString() : null)
                .endDate(policy.getEndDate() != null ? policy.getEndDate().toString() : null)
                .status(policy.getStatus() != null ? policy.getStatus().name() : null)
                .build();
    }
}

