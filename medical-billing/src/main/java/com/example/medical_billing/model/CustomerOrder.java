package com.example.medical_billing.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class CustomerOrder{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceNumber;
    private LocalDate invoiceDate;
    private String dlNo;
    private String licenseNo;

    private String billFromName;
    private String billFromPhone;
    private String billFromAddress;

    private String billToName;
    private String billToPhone;
    private String billToAddress;

    private Double subtotal;
    private Double totalAmount;
    private String paymentMode;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items;

   
}