package com.Saga_Orchestrator.payment_service.service;

import com.Saga_Orchestrator.common.services.event.UserBalanceUpdateEvent;

import com.Saga_Orchestrator.common.services.event.UserBalanceUpdateEvent;
import com.Saga_Orchestrator.payment_service.dto.PaymentRequest;
import com.Saga_Orchestrator.payment_service.entity.Payment;
import com.Saga_Orchestrator.payment_service.event.PaymentCompletedEvent;
import com.Saga_Orchestrator.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Qualifier("userBalanceKafkaTemplate")
    private final KafkaTemplate<String, UserBalanceUpdateEvent> balanceUpdateKafkaTemplate;

    @Qualifier("paymentCompletedKafkaTemplate")
    private final KafkaTemplate<String, PaymentCompletedEvent> paymentCompletedKafkaTemplate;

    public void processPayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setStatus("SUCCESS");
        payment.setUserId(request.getUserId());
        paymentRepository.save(payment);

        UserBalanceUpdateEvent event = new UserBalanceUpdateEvent(request.getUserId(), request.getAmount());
        balanceUpdateKafkaTemplate.send("user-balance-update-topic", event);
        log.info("Sent balance update event: {}", event);
    }

    public void makePayment(Long userId, Double amount) {
        Payment payment = Payment.builder()
                .userId(userId)
                .amount(amount)
                .status("SUCCESS")
                .build();

        paymentRepository.save(payment);

        paymentCompletedKafkaTemplate.send(
                "payment-completed-topic",
                new PaymentCompletedEvent(userId, amount, "SUCCESS")
        );
        log.info("Sent payment completed event: userId={}, amount={}", userId, amount);
    }
}

