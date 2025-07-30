package com.Saga_Orchestrator.common.services.dto;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;           // Ensure this is used for linking to payments
    private Long userId;
    private Double amount;
    private List<BookDTO> books;
}

