package com.Saga_Orchestrator.common.services.event;



import com.Saga_Orchestrator.common.services.dto.PaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {
    private String eventType; // e.g., PAYMENT_REQUESTED, PAYMENT_SUCCESS, PAYMENT_FAILED
    private PaymentDTO payment;
}

