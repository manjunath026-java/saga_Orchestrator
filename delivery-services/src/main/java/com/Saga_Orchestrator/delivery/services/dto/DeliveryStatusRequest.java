package com.Saga_Orchestrator.delivery.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusRequest {
    private Long orderId;
    private String status;
}
