package com.Saga_Orchestrator.user_service.service;

import com.Saga_Orchestrator.common.services.dto.OrderRequest;
import com.Saga_Orchestrator.common.services.event.UserBalanceUpdateEvent;
import com.Saga_Orchestrator.user_service.entity.User;
import com.Saga_Orchestrator.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    @KafkaListener(
            topics = "order-topic",
            groupId = "user-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(OrderRequest request) {
        log.info("Order Received in UserService: {}", request);
        log.info("Status: CREATED > Awaiting Payment > Will deduct balance after payment SUCCESS");
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

        log.info(" Balance deducted for userId={} by amount={}", userId, amount);
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

//import com.Saga_Orchestrator.common.services.dto.OrderRequest;
//import com.Saga_Orchestrator.common.services.event.UserBalanceUpdateEvent;
//import com.Saga_Orchestrator.user_service.entity.User;
//import com.Saga_Orchestrator.user_service.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final KafkaTemplate<String, UserBalanceUpdateEvent> userBalanceKafkaTemplate;
//
//    public Optional<User> getUserById(Long id) {
//        return userRepository.findById(id);
//    }
//
//    // Consumes order creation event
//    @KafkaListener(
//            topics = "order-topic",
//            groupId = "user-group",
//            containerFactory = "kafkaListenerContainerFactory"
//    )
//    public void consume(OrderRequest request) {
//        log.info("Received order: {}", request);
//
//        // Send balance deduction event to Kafka
//        UserBalanceUpdateEvent event = new UserBalanceUpdateEvent(request.getUserId(), request.getAmount());
//        userBalanceKafkaTemplate.send("user-balance-update-topic", event);
//        log.info("Published UserBalanceUpdateEvent: {}", event);
//    }
//
//    public boolean hasSufficientBalance(Long userId, double amount) {
//        return userRepository.findById(userId)
//                .map(user -> user.getBalance() >= amount)
//                .orElse(false);
//    }
//
//    public void deductBalance(Long userId, Double amount) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        if (user.getBalance() < amount) {
//            throw new RuntimeException("Insufficient balance for user ID: " + userId);
//        }
//
//        user.setBalance(user.getBalance() - amount);
//        userRepository.save(user);
//    }
//
//    // Listener for balance update events
//    @KafkaListener(topics = "user-balance-update-topic", groupId = "user-group")
//    @Transactional
//    public void handleBalanceUpdate(UserBalanceUpdateEvent event) {
//        Long userId = event.getUserId();
//        Double amount = event.getAmount();
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        if (user.getBalance() < amount) {
//            throw new RuntimeException("Insufficient balance for user ID: " + userId);
//        }
//
//        user.setBalance(user.getBalance() - amount);
//        userRepository.save(user);
//
//        log.info("User balance updated for userId={} by amount={}", userId, amount);
//    }
//
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public User createUser(User user) {
//        return userRepository.save(user);
//    }
//
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }
//}


//
//
//import com.Saga_Orchestrator.common.services.dto.OrderRequest;
//import com.Saga_Orchestrator.common.services.event.UserBalanceUpdateEvent;
//import com.Saga_Orchestrator.payment_service.event.PaymentCompletedEvent;
//import com.Saga_Orchestrator.user_service.entity.User;
//import com.Saga_Orchestrator.user_service.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//    public Optional<User> getUserById(Long id) {
//        return userRepository.findById(id);
//    }
//
//
//    @KafkaListener(
//            topics = "order-topic",
//            groupId = "user-group",
//            containerFactory = "kafkaListenerContainerFactory"
//    )
//    public void consume(OrderRequest request) {
//        System.out.println("Received order: " + request);
//
//        // simulate status update
//        System.out.println("Status: CREATED > CREDITTED");
//    }
//
//
//    public boolean hasSufficientBalance(Long userId, double amount) {
//        return userRepository.findById(userId)
//                .map(user -> user.getBalance() >= amount)
//                .orElse(false);
//    }
//
//
//
//    public void deductBalance(Long userId, Double amount) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        if (user.getBalance() < amount) {
//            throw new RuntimeException("Insufficient balance for user ID: " + userId);
//        }
//
//        user.setBalance(user.getBalance() - amount);
//        userRepository.save(user);
//    }
//    @KafkaListener(topics = "user-balance-update-topic", groupId = "user-group")
//    @Transactional
//    public void handleBalanceUpdate(UserBalanceUpdateEvent event) {
//        Long userId = event.getUserId();
//        Double amount = event.getAmount();
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        if (user.getBalance() < amount) {
//            throw new RuntimeException("Insufficient balance for user ID: " + userId);
//        }
//
//        user.setBalance(user.getBalance() - amount);
//        userRepository.save(user);
//    }
//
//
//
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public User createUser(User user) {
//        return userRepository.save(user);
//    }
//
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }
//}
