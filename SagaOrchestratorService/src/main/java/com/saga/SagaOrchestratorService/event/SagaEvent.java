package com.saga.SagaOrchestratorService.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SagaEvent {
    private String eventType;
    private Long orderId;
    private Long userId;
    private Double amount;
    private String bookCode;
}
