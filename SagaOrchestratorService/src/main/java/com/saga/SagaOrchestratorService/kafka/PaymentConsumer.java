package com.saga.SagaOrchestratorService.kafka;

import com.saga.SagaOrchestratorService.event.SagaEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentConsumer {

    private final KafkaTemplate<String, SagaEvent> kafkaTemplate;

    public PaymentConsumer(KafkaTemplate<String, SagaEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "${payment.topic.process}", groupId = "saga-group")
    public void consumePayment(SagaEvent event) {
        try {
            log.info("Processing payment for order: {}", event.getOrderId());

            boolean paymentSuccess = mockPayment(event.getAmount());

            if (paymentSuccess) {
                log.info("Payment success for order {}", event.getOrderId());
                // Continue saga or publish delivery event
            } else {
                log.warn("Payment failed for order {}", event.getOrderId());
                event.setEventType("PAYMENT_FAILED");
                kafkaTemplate.send("order.rollback", event);
            }

        } catch (Exception e) {
            log.error("Payment processing failed: {}", e.getMessage());
        }
    }

    private boolean mockPayment(Double amount) {
        return amount < 1000.0; // simulate failure if >1000
    }
}

