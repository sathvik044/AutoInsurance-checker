package com.example.InsurenceBackend.Dtos;

import com.example.InsurenceBackend.enums.DocumentType;
import lombok.Data;

@Data
public class ClaimDocumentRequestDto {

    private Long claimId;
    private DocumentType documentType;
    private String documentUrl;
    private Long uploadedById;

}