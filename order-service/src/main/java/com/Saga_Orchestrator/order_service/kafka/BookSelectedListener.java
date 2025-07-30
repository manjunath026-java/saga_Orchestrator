package com.Saga_Orchestrator.order_service.kafka;



import com.Saga_Orchestrator.common.services.event.BookSelectedEvent;
import com.Saga_Orchestrator.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class BookSelectedListener {

    private final OrderService orderService;

    @KafkaListener(topics = "book.selected", groupId = "order-group", containerFactory = "bookSelectedKafkaListenerFactory")
    public void listen(BookSelectedEvent event) {
        System.out.println("Received BookSelectedEvent: " + event);
        orderService.createOrder(event.getUserId(), event.getBooks());
    }
}
