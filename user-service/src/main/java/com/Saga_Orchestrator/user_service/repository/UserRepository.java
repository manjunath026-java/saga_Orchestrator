package com.Saga_Orchestrator.user_service.repository;


import com.Saga_Orchestrator.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
