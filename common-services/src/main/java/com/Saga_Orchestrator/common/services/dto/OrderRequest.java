package com.Saga_Orchestrator.common.services.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long orderId;
    private Long userId;
    private Double amount;
    private List<BookDTO> books;
}
