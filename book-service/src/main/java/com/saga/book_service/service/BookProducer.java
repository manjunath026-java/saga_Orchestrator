package com.saga.book_service.service;

import com.Saga_Orchestrator.common.services.event.BookSelectedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookProducer {

    private final KafkaTemplate<String, BookSelectedEvent> kafkaTemplate;

    public BookProducer(KafkaTemplate<String, BookSelectedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookSelection(BookSelectedEvent event) {
        kafkaTemplate.send("book-selected-topic", event);
    }
}
