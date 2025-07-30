package com.Saga_Orchestrator.order_service.service;



import com.Saga_Orchestrator.common.services.dto.BookDTO;
import com.Saga_Orchestrator.common.services.enums.OrderStatus;
import com.Saga_Orchestrator.common.services.event.OrderCreatedEvent;
import com.Saga_Orchestrator.order_service.entity.Order;
import com.Saga_Orchestrator.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void createOrder(Long userId, List<BookDTO> books) {
        double totalAmount = books.stream()
                .mapToDouble(BookDTO::getPrice)
                .sum();

        // save to DB (example)
        Order order = Order.builder()
                .userId(userId)
                .amount(totalAmount)
                .status("PENDING")
                .build();

        orderRepository.save(order);

        // Optionally, send PaymentEvent to Kafka
    }

//    public void createOrder(Long userId, List<BookDTO> books) {
//        Order order = Order.builder()
//                .userId(userId)
//                .status(OrderStatus.PENDING)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        Order savedOrder = orderRepository.save(order);
//
//        OrderCreatedEvent event = new OrderCreatedEvent(
//                savedOrder.getId(),
//                userId,
//                books
//        );
//
//        kafkaTemplate.send("order.created", event);
//    }


}
