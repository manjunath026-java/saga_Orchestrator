package com.Saga_Orchestrator.order_service.dto;



import com.Saga_Orchestrator.common.services.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private Long orderId;         // Consistently a String everywhere
    private Long userId;
    private Double amount;

}
