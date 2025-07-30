package com.saga.SagaOrchestratorService.dto;


import com.Saga_Orchestrator.common.services.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long orderId;
    private Long userId;
    private Double amount;
    private List<BookDTO> books;
}

