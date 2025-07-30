package com.Saga_Orchestrator.payment_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    private Long orderId;   // As Long (convert in orchestrator)
    private Long userId;
    private Double amount;
}
