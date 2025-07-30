package com.saga.book_service.consumer;


import com.saga.book_service.config.OrderEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventConsumer {

    @KafkaListener(topics = "order-topic", groupId = "bookstore-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(OrderEventListener event) {
        log.info("Received event: {}", event);

    }
}
