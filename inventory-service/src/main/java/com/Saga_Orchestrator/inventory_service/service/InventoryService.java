package com.Saga_Orchestrator.inventory_service.service;


import com.Saga_Orchestrator.inventory_service.entity.Inventory;
import com.Saga_Orchestrator.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isBookInStock(String bookCode, int requiredQty) {
        return inventoryRepository.findByBookCode(bookCode)
                .map(inventory -> inventory.getQuantity() >= requiredQty)
                .orElse(false);
    }

    public void reduceStock(String bookCode, int qty) {
        Inventory inventory = inventoryRepository.findByBookCode(bookCode)
                .orElseThrow(() -> new RuntimeException("Book not found: " + bookCode));
        if (inventory.getQuantity() < qty) throw new RuntimeException("Insufficient stock");
        inventory.setQuantity(inventory.getQuantity() - qty);
        inventoryRepository.save(inventory);
    }

    public void addStock(String bookCode, int qty) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findByBookCode(bookCode);
        Inventory inventory = inventoryOpt.orElse(Inventory.builder()
                .bookCode(bookCode)
                .quantity(0)
                .build());
        inventory.setQuantity(inventory.getQuantity() + qty);
        inventoryRepository.save(inventory);
    }
}

