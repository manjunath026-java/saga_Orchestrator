package com.Saga_Orchestrator.user_service.controller;

import com.Saga_Orchestrator.user_service.entity.User;
import com.Saga_Orchestrator.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/{id}/balance-check")
    public ResponseEntity<String> checkBalance(@PathVariable Long id, @RequestParam double amount) {
        boolean hasBalance = userService.hasSufficientBalance(id, amount);
        return ResponseEntity.ok(hasBalance ? "Sufficient balance" : "Insufficient balance");
    }

    @PostMapping("/{id}/deduct")
    public ResponseEntity<Map<String, Object>> deductBalance(@PathVariable Long id, @RequestParam double amount) {
        Map<String, Object> response = new HashMap<>();
        response.put("userId", id);
        response.put("requestedAmount", amount);

        if (userService.hasSufficientBalance(id, amount)) {
            userService.deductBalance(id, amount);
            response.put("status", "Success");
            response.put("message", "Balance deducted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "Failed");
            response.put("message", "Insufficient balance");
            return ResponseEntity.badRequest().body(response);
        }
    }

}

