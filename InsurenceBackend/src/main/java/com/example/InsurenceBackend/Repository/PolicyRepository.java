package com.example.InsurenceBackend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.InsurenceBackend.model.Policy;
import java.util.List;
public interface PolicyRepository extends JpaRepository<Policy, Long>   {
 List<Policy> findByPolicyholder_Id(Long userId);
    
}
  