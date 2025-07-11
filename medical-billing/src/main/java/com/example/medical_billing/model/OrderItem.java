package com.example.medical_billing.model;

import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class OrderItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String batchNo;
   private double price;
    private int quantity;
    private double total;

        @ManyToOne
        @JoinColumn(name="order_id")
    @JsonBackReference
        private CustomerOrder order;
    
}                                