package com.Saga_Orchestrator.user_service.service;



import com.Saga_Orchestrator.common.services.event.UserBalanceUpdateEvent;
import com.Saga_Orchestrator.payment_service.event.PaymentCompletedEvent;
import com.Saga_Orchestrator.user_service.entity.User;
import com.Saga_Orchestrator.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }




    public boolean hasSufficientBalance(Long userId, double amount) {
        return userRepository.findById(userId)
                .map(user -> user.getBalance() >= amount)
                .orElse(false);
    }



    public void deductBalance(Long userId, Double amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (user.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance for user ID: " + userId);
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);
    }
    @KafkaListener(topics = "user-balance-update-topic", groupId = "user-group")
    @Transactional
    public void handleBalanceUpdate(UserBalanceUpdateEvent event) {
        Long userId = event.getUserId();
        Double amount = event.getAmount();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (user.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance for user ID: " + userId);
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);
    }



    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
