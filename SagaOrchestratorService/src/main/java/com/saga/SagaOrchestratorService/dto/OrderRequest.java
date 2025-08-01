package com.saga.SagaOrchestratorService.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long orderId;    // optional (could be auto-generated)
    private Long userId;
    private Double amount;
    private String bookCode;
}

