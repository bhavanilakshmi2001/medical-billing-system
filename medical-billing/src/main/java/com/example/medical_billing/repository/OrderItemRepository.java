package com.example.medical_billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.medical_billing.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    
}
