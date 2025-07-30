package com.Saga_Orchestrator.common.services.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private Long paymentId;
    private Long orderId;
    private Double amount;
    private String PaymentStatus;
}

