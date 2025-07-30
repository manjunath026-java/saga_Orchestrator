package com.Saga_Orchestrator.inventory_service.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequest {
    private String bookCode;
    private Integer quantity;
}
