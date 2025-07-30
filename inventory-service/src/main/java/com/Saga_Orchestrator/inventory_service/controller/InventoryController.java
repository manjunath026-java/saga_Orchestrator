package com.Saga_Orchestrator.inventory_service.controller;


import com.Saga_Orchestrator.inventory_service.dto.InventoryRequest;
import com.Saga_Orchestrator.inventory_service.service.InventoryService;
import com.Saga_Orchestrator.order_service.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    @PostMapping("/reserve")
    public ResponseEntity<String> reserve(@RequestBody OrderRequest req) {
        System.out.println("Reserved books: " + req.getOrderId());
        return ResponseEntity.ok("RESERVED");
    }
    @GetMapping("/{bookCode}")
    public boolean isInStock(@PathVariable String bookCode, @RequestParam int qty) {
        return inventoryService.isBookInStock(bookCode, qty);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addInventory(@RequestBody InventoryRequest request) {
        inventoryService.addStock(request.getBookCode(), request.getQuantity());
        return ResponseEntity.ok("Inventory updated successfully");
    }

    @PostMapping("/reduce")
    public String reduceInventory(@RequestBody InventoryRequest request) {
        inventoryService.reduceStock(request.getBookCode(), request.getQuantity());
        return "Stock reduced";
    }

}

