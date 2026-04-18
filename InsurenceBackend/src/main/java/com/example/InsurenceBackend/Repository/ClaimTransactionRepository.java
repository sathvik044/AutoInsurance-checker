package com.example.InsurenceBackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.InsurenceBackend.model.ClaimTransaction;

public interface ClaimTransactionRepository extends JpaRepository<ClaimTransaction, Long> {
    
}
