package com.saga.SagaOrchestratorService.controller;


import com.Saga_Orchestrator.order_service.dto.OrderRequest;
import com.Saga_Orchestrator.order_service.dto.OrderResponse;
import com.Saga_Orchestrator.payment_service.dto.PaymentRequest;

import com.saga.SagaOrchestratorService.dto.InventoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;




@RestController
@RequestMapping("/saga")
@RequiredArgsConstructor
@Slf4j
public class OrderSagaOrchestratorController {

    private final RestTemplate restTemplate;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;


    @PostMapping("/createorder")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest request) {
        try {
            ResponseEntity<OrderResponse> orderResponse = restTemplate.postForEntity(
                    orderServiceUrl + "/create", request, OrderResponse.class
            );

            if (!orderResponse.getStatusCode().is2xxSuccessful() || orderResponse.getBody() == null) {
                log.error(" Order creation failed. Status: {}", orderResponse.getStatusCode());
                return ResponseEntity.internalServerError().body(" Order creation failed.");
            }

            Long orderId = orderResponse.getBody().getOrderId();
            log.info(" Order created with ID: {}", orderId);

            //  Step 2: Process Payment
            PaymentRequest paymentRequest = PaymentRequest.builder()
                    .orderId(orderId)
                    .userId(request.getUserId())
                    .amount(request.getAmount())
                    .build();

            ResponseEntity<String> paymentResponse = restTemplate.postForEntity(
                    paymentServiceUrl + "/process", paymentRequest, String.class
            );

            if (!paymentResponse.getStatusCode().is2xxSuccessful()) {
                log.warn("Payment failed for Order ID: {}", orderId);
                rollbackOrder(orderId); // rollback on failure
                return ResponseEntity.internalServerError().body(" Payment failed. Order rolled back.");
            }

            log.info("Payment processed for Order ID: {}", orderId);
            return ResponseEntity.ok(" Order placed and payment processed successfully.");

        } catch (HttpStatusCodeException ex) {
            log.error("HTTP error: {}", ex.getResponseBodyAsString());
            return ResponseEntity.status(ex.getStatusCode())
                    .body(" Saga failed: " + ex.getResponseBodyAsString());
        } catch (Exception e) {
            log.error(" Unexpected error during Saga: ", e);
            return ResponseEntity.internalServerError().body(" Saga failed due to internal error: " + e.getMessage());
        }
    }

    private void rollbackOrder(Long orderId) {
        try {
            String cancelUrl = orderServiceUrl + "/cancel/" + orderId;
            restTemplate.postForEntity(cancelUrl, null, Void.class);
            log.info(" Rolled back order ID: {}", orderId);
        } catch (Exception e) {
            log.error("Rollback failed for order ID {}: {}", orderId, e.getMessage());
        }
    }
}
