package com.example.InsurenceBackend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.InsurenceBackend.model.FraudAlert;

public interface FraudTransactionRepository extends JpaRepository<FraudAlert, Long>   {
    
}
