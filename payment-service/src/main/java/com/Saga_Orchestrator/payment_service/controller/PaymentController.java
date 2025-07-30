package com.Saga_Orchestrator.payment_service.controller;



import com.Saga_Orchestrator.payment_service.entity.Payment;
import com.Saga_Orchestrator.payment_service.repository.PaymentRepository;
import com.Saga_Orchestrator.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Saga_Orchestrator.payment_service.dto.PaymentRequest;


@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    // Make payment via request params
    @PostMapping
    public ResponseEntity<String> makePayment(@RequestParam Long userId, @RequestParam Double amount) {
        paymentService.makePayment(userId, amount);
        return ResponseEntity.ok("Payment initiated successfully.");
    }

    @PostMapping("/process")
    public ResponseEntity<String> process(@RequestBody PaymentRequest req) {
        System.out.println(" Payment received for orderId: " + req.getOrderId());

        Payment payment = Payment.builder()
                .orderId(req.getOrderId())
                .userId(req.getUserId())
                .amount(req.getAmount())
                .status("COMPLETED")
                .build();

        paymentRepository.save(payment);

        return ResponseEntity.ok(" Payment processed and saved to DB.");
    }


    @PostMapping("/deduct-balance/{userId}")
    public ResponseEntity<String> deductBalance(@PathVariable Long userId, @RequestBody PaymentRequest req) {
        // userService.deductAmount(userId, req.getAmount());
        return ResponseEntity.ok("User balance deducted");
    }



    // Refund logic
    @PostMapping("/refund")
    public ResponseEntity<String> refund(@RequestBody PaymentRequest req) {
        System.out.println("Refund issued for amount: " + req.getAmount());
        return ResponseEntity.ok("REFUNDED");
    }
}
