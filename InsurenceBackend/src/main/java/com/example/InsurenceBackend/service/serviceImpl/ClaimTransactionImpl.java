package com.example.InsurenceBackend.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.example.InsurenceBackend.model.ClaimTransaction;
import com.example.InsurenceBackend.service.ClaimTransactionService;
import com.example.InsurenceBackend.Exception.ClaimTransactionNotFoundException;
import com.example.InsurenceBackend.Repository.ClaimTransactionRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimTransactionImpl implements ClaimTransactionService {
    
    private final ClaimTransactionRepository claimTransactionRepo;

    @Override
    public ClaimTransaction getClaimTransaction(Long id){
        return claimTransactionRepo.findById(id)
                .orElseThrow(() -> new ClaimTransactionNotFoundException("Claim transaction not found with id: " + id));
    }

    @Override
    public List<ClaimTransaction> getAllClaimTransactions(){
        return claimTransactionRepo.findAll();
    }
}
