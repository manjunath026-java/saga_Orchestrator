package com.saga.SagaOrchestratorService.kafka;

import com.saga.SagaOrchestratorService.event.SagaEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderRollbackListener {

    @KafkaListener(topics = "${order.topic.rollback}", groupId = "saga-group")
    public void rollbackOrder(SagaEvent event) {
        log.warn("Rolling back order {}", event.getOrderId());

        // Call orderService to cancel order
        // Optionally publish rollback inventory/payment events
    }
}
