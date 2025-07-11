package com.example.medical_billing.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String batchNo;
    private String productName;
    private String itemDescription;
    private int quantity;
    private double price;
    private LocalDate expiryDate;
    private int stock;
    private double mrp;
    private String hsn;
}
