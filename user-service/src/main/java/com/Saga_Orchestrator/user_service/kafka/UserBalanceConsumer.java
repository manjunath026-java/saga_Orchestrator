package com.Saga_Orchestrator.user_service.kafka;



import com.Saga_Orchestrator.common.services.event.UserBalanceUpdateEvent;
import com.Saga_Orchestrator.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserBalanceConsumer {

    private final UserRepository userRepository;

    @KafkaListener(topics = "user-balance-update-topic", groupId = "user-service-group")
    public void consume(UserBalanceUpdateEvent event) {
        log.info("Received payment event: {}", event);

        userRepository.findById(event.getUserId()).ifPresent(user -> {
            double updatedBalance = user.getBalance() - event.getAmount();

            if (updatedBalance >= 0) {
                user.setBalance(updatedBalance);
                userRepository.save(user);
                log.info("Updated balance for user {} to {}", user.getId(), updatedBalance);
            } else {
                log.warn("Insufficient balance for user {}", user.getId());
            }
        });
    }
}
