package com.Saga_Orchestrator.payment_service.event;


import com.Saga_Orchestrator.payment_service.dto.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentConsumer {

    @KafkaListener(topics = "payment-topic", groupId = "payment-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumePayment(PaymentRequest paymentRequest) {
        log.info("Received payment request for Order ID: {}", paymentRequest.getOrderId());

        if (paymentRequest.getAmount() != null && paymentRequest.getAmount() > 0) {
            log.info("Processing payment of ₹{} for User ID: {}", paymentRequest.getAmount(), paymentRequest.getUserId());

            boolean success = true; // Replace with real logic
            if (success) {
                log.info("Payment successful for Order ID: {}", paymentRequest.getOrderId());
            } else {
                log.warn("Payment failed for Order ID: {}", paymentRequest.getOrderId());
            }

        } else {
            log.error("Invalid payment amount for Order ID: {}", paymentRequest.getOrderId());
        }
    }
}
