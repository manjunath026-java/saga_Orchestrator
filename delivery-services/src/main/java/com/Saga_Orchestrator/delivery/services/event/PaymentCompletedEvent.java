package com.Saga_Orchestrator.delivery.services.event;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCompletedEvent {
    private Long orderId;
    private String address;
}