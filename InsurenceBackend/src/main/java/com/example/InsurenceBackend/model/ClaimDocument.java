package com.example.InsurenceBackend.model;


import java.time.LocalDateTime;

import com.example.InsurenceBackend.enums.ClaimDocVerificationStatus;
import com.example.InsurenceBackend.enums.DocumentType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table; 
import lombok.Data;
@Data
@Entity
@Table(name = "claim_documents")
public class ClaimDocument {
@Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "claim_id")
    private ClaimInsurence claim;
   @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    private String documentUrl;
    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;
    @Enumerated(EnumType.STRING)
    private ClaimDocVerificationStatus verificationStatus;
    private LocalDateTime uploadedAt;
    private LocalDateTime verifiedAt;

}
