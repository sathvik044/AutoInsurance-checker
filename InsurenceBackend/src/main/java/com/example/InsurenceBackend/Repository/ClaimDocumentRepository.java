package com.example.InsurenceBackend.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.InsurenceBackend.model.ClaimDocument;

public interface ClaimDocumentRepository extends JpaRepository<ClaimDocument, Long>   {
    List<ClaimDocument> findByClaimId(Long claimId);
    List<ClaimDocument> findByUploadedById(Long userId);

}
