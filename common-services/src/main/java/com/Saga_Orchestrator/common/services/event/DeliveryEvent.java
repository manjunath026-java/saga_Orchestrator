package com.Saga_Orchestrator.common.services.event;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryEvent {
    private Long orderId;
    private Long deliveryAgentId;
    private String deliveryStatus;
}
