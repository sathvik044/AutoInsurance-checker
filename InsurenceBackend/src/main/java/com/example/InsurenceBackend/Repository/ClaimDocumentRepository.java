package com.example.InsurenceBackend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.InsurenceBackend.model.ClaimDocument;

public interface ClaimDocumentRepository extends JpaRepository<ClaimDocument, Long>   {
    
}
