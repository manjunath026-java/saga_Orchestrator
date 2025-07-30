package com.Saga_Orchestrator.common.services.event;

import com.Saga_Orchestrator.common.services.dto.OrderDTO;
import com.Saga_Orchestrator.common.services.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private OrderDTO order;
    private OrderStatus status;
}




