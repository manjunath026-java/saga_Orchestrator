package com.Saga_Orchestrator.delivery.services.services;

import com.Saga_Orchestrator.delivery.services.entity.Delivery;
import com.Saga_Orchestrator.delivery.services.event.DeliveryCompletedEvent;
import com.Saga_Orchestrator.delivery.services.event.PaymentCompletedEvent;
import com.Saga_Orchestrator.delivery.services.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final KafkaTemplate<String, DeliveryCompletedEvent> kafkaTemplate;
    private final Map<Long, Delivery> deliveries = new ConcurrentHashMap<>();

    public Delivery createDelivery(Long orderId, String address) {
        Delivery delivery = Delivery.builder()
                .orderId(orderId)
                .deliveryAddress(address)
                .deliveryStatus("IN_PROGRESS")
                .build();
        deliveries.put(orderId, delivery);
        return deliveryRepository.save(delivery);
    }

    public void updateDeliveryStatus(Long orderId, String status) {
        Delivery delivery = deliveryRepository.findByOrderId(orderId);
        if (delivery != null) {
            delivery.setDeliveryStatus(status);
            deliveryRepository.save(delivery);
            deliveries.put(orderId, delivery);
        }
    }

    public Delivery getDelivery(Long orderId) {
        // First check the in-memory store, fallback to DB
        return deliveries.getOrDefault(orderId, deliveryRepository.findByOrderId(orderId));
    }

    @KafkaListener(topics = "payment-completed", groupId = "delivery-group")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        System.out.println("Received payment completed event: " + event);
        // Create delivery
        Delivery delivery = Delivery.builder()
                .orderId(event.getOrderId())
                .deliveryAddress(event.getAddress())
                .deliveryStatus("IN_PROGRESS")
                .build();

        deliveries.put(event.getOrderId(), delivery);
        deliveryRepository.save(delivery);

        // Simulate delivery completion (e.g., after some process or time)
        notifyDeliveryCompleted(new DeliveryCompletedEvent(event.getOrderId(), "DELIVERED"));
    }

    public void notifyDeliveryCompleted(DeliveryCompletedEvent event) {
        kafkaTemplate.send("delivery-completed", event);
        System.out.println("Sent delivery completed event: " + event);
    }
}
