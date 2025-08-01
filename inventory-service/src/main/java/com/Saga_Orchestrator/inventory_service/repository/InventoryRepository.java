package com.Saga_Orchestrator.inventory_service.repository;


import com.Saga_Orchestrator.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByBookCode(String bookCode);
}
