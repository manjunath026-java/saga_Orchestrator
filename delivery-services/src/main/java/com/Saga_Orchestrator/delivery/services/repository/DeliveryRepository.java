package com.Saga_Orchestrator.delivery.services.repository;


import com.Saga_Orchestrator.delivery.services.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery findByOrderId(Long orderId);
}

