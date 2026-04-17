package com.example.InsurenceBackend.model;

import java.time.LocalDate;

import com.example.InsurenceBackend.enums.TransactionStatus;
import com.example.InsurenceBackend.enums.TransactionType;

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
@Entity
@Data
@Table(name = "claim_transactions")
public class ClaimTransaction {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "claim_id")
    private ClaimInsurence claim;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    private String transactionReference;
    private LocalDate createdAt;                                    
}
