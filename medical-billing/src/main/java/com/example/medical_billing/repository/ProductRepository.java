package com.example.medical_billing.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.medical_billing.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT SUM(p.stock) FROM Product p")
    Integer getTotalStock();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.stock = 0")
    Integer getOutOfStock();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.expiryDate < :today")
    Integer countExpiredProducts(@Param("today") LocalDate today);
}
