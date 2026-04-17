package com.example.InsurenceBackend.model;

import lombok.Data;
import java.time.LocalDateTime;

import com.example.InsurenceBackend.enums.FraudIndicator;
import com.example.InsurenceBackend.enums.FraudStatus;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "fraud_alerts")
public class FraudAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "claim_id")
    private ClaimInsurence claim;
    @ManyToOne
    @JoinColumn(name = "reported_by")
    private User raisedBy;
    private String reason;
    private int fraudScore;
    @Enumerated(EnumType.STRING)
    private FraudIndicator indicator;
   @Enumerated(EnumType.STRING)
    private FraudStatus status;
   @ManyToOne
   @JoinColumn(name= "resolved_by")
   private User resolvedBy;
   private LocalDateTime createdAt;
   private LocalDateTime resolvedAt;   
}
