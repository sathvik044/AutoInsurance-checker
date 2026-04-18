package com.example.InsurenceBackend.service;

import com.example.InsurenceBackend.model.ClaimTransaction;
import java.util.*;

public interface ClaimTransactionService {
    public ClaimTransaction getClaimTransaction(Long id);
    public List<ClaimTransaction> getAllClaimTransactions();
}
