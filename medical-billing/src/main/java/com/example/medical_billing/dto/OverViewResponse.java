package com.example.medical_billing.dto;

import java.util.List;

import lombok.Data;

@Data
public class OverViewResponse {
    private double totalRevenue;
    private int totalStock;
    private int outOfStock;
    private int expiredProducts;
    private int todayOrderedCount;
    private double todayRevenue;
    private List<Double> monthlyRevenue;
}
