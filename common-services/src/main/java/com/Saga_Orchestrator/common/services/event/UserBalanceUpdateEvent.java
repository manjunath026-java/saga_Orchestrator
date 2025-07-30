package com.Saga_Orchestrator.common.services.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceUpdateEvent {
    private Long userId;
    private Double amount;
}
