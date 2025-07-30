package com.Saga_Orchestrator.delivery.services.controller;


import com.Saga_Orchestrator.delivery.services.dto.DeliveryRequest;
import com.Saga_Orchestrator.delivery.services.dto.DeliveryStatusRequest;
import com.Saga_Orchestrator.delivery.services.entity.Delivery;
import com.Saga_Orchestrator.delivery.services.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService service;

    @PostMapping("/create")
    public ResponseEntity<Delivery> createDelivery(@RequestBody DeliveryRequest request) {
        return ResponseEntity.ok(service.createDelivery(request.getOrderId(), request.getAddress()));
    }


    @PutMapping("/status")
    public ResponseEntity<Void> updateStatus(@RequestBody DeliveryStatusRequest request) {
        service.updateDeliveryStatus(request.getOrderId(), request.getStatus());
        return ResponseEntity.ok().build();
    }

}
