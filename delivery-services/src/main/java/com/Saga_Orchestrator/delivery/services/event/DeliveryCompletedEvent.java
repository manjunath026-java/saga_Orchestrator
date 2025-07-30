package com.Saga_Orchestrator.delivery.services.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryCompletedEvent {
    private Long orderId;
    private String status;
}