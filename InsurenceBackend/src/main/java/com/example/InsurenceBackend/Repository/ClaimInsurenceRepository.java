package com.example.InsurenceBackend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.InsurenceBackend.model.ClaimInsurence;

public interface ClaimInsurenceRepository extends JpaRepository<ClaimInsurence, Long>    {
    
}
