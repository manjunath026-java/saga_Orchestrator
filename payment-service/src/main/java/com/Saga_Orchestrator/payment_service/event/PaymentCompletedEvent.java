package com.Saga_Orchestrator.payment_service.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCompletedEvent {
    private Long userId;
    private double amount;
    private String status; // "SUCCESS"
}
