package com.Saga_Orchestrator.order_service.controller;

import com.Saga_Orchestrator.order_service.dto.OrderRequest;
import com.Saga_Orchestrator.order_service.dto.OrderResponse;
import com.Saga_Orchestrator.order_service.entity.Order;
import com.Saga_Orchestrator.order_service.repository.OrderRepository;
import com.Saga_Orchestrator.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final Map<String, String> orders = new ConcurrentHashMap<>();

@PostMapping("/create")
public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest req) {
    Order order = new Order();
    order.setUserId(req.getUserId());
    order.setAmount(req.getAmount());
    order.setStatus("CREATED");

    Order savedOrder = orderRepository.save(order);

    return ResponseEntity.ok(new OrderResponse(savedOrder.getId(), savedOrder.getStatus()));
}

    @PostMapping("/update-stock/{orderId}")
    public ResponseEntity<String> updateStock(@PathVariable Long orderId) {
        return ResponseEntity.ok("Stock updated");
    }
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<Void> cancel(@PathVariable String orderId) {
        orders.remove(orderId);
        return ResponseEntity.ok().build();
    }


}

