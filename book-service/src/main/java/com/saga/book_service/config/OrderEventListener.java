package com.saga.book_service.config;


import com.Saga_Orchestrator.common.services.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventListener {

    @KafkaListener(topics = "order-topic", groupId = "bookstore-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenOrderCreated(OrderCreatedEvent event) {
        System.out.println(" Received OrderCreatedEvent in Book Service:");
        System.out.println("User ID: " + event.getUserId());
        System.out.println("Books: " + event.getBooks());


    }
}
