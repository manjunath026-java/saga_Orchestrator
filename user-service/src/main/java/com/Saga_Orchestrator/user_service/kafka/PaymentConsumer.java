package com.Saga_Orchestrator.user_service.kafka;



import com.Saga_Orchestrator.payment_service.event.PaymentCompletedEvent;
import com.Saga_Orchestrator.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final UserService userService;

    @KafkaListener(topics = "payment-completed-topic", groupId = "user-service-group")
    public void handlePayment(PaymentCompletedEvent event) {
        log.info("Received payment event: {}", event);
        userService.deductBalance(event.getUserId(), Double.valueOf(event.getUserId()));
    }
}

