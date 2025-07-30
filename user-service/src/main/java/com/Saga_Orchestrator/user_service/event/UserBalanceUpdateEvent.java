package com.Saga_Orchestrator.user_service.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceUpdateEvent {
    private Long userId;
    private double amount;
}
