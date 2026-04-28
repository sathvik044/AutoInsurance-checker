package com.example.InsurenceBackend.service.serviceImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.InsurenceBackend.Dtos.PolicyMapper;
import com.example.InsurenceBackend.Dtos.PolicyRequest;
import com.example.InsurenceBackend.Dtos.PolicyResponse;
import com.example.InsurenceBackend.Exception.PolicyNotFoundException;
import com.example.InsurenceBackend.Exception.UserNotFoundException;
import com.example.InsurenceBackend.model.Policy;
import com.example.InsurenceBackend.model.User;
import com.example.InsurenceBackend.service.PolicyService;
import com.example.InsurenceBackend.Repository.PolicyRepository;
import com.example.InsurenceBackend.Repository.UserRepository;


import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {
@Autowired  
public PolicyRepository policyRepo;

@Autowired
public UserRepository userRepo;

 @Override
    public List<PolicyResponse> findAll(){
        return policyRepo.findAll().stream()
                .map(PolicyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
     public PolicyResponse createPolicy(PolicyRequest request) {
        User user = userRepo.findById(request.getPolicyholderId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.getPolicyholderId()));
        
        User manager = null;
        if (request.getManagerId() != null) {
            manager = userRepo.findById(request.getManagerId())
                    .orElseThrow(() -> new UserNotFoundException("Manager not found with id: " + request.getManagerId()));
        }
        
        Policy policy = PolicyMapper.toEntity(request, user, manager);
        if (policy.getStartDate() == null) {
            policy.setStartDate(java.time.LocalDateTime.now());
        }
        Policy savedPolicy = policyRepo.save(policy);
        return PolicyMapper.toDto(savedPolicy);
    }
     @Override
    public PolicyResponse findById(Long id) {
        Policy policy = policyRepo.findById(id)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found with id: " + id));
        return PolicyMapper.toDto(policy);
    }

    @Override
    public List<PolicyResponse> findByUserId(Long userId){
        return policyRepo.findByPolicyholder_Id(userId).stream()
                .map(PolicyMapper::toDto)
                .collect(Collectors.toList());
    }
    
}


