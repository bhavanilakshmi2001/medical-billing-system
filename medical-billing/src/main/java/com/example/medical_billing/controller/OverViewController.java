package com.example.medical_billing.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.medical_billing.dto.OverViewResponse;
import com.example.medical_billing.repository.CustomerOrderRepository;
import com.example.medical_billing.repository.ProductRepository;

@RestController
@RequestMapping("/overview")
public class OverViewController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CustomerOrderRepository orderRepo;

    @GetMapping("/dashboard")
    public OverViewResponse getDashboardData() {
        OverViewResponse response = new OverViewResponse();

        // Total Revenue
        Double revenue = orderRepo.getTotalRevenue();
        response.setTotalRevenue(revenue != null ? revenue : 0);

        // Total Stock
        Integer stock = productRepo.getTotalStock();
        response.setTotalStock(stock != null ? stock : 0);

        // Out of Stock
        Integer out = productRepo.getOutOfStock();
        response.setOutOfStock(out != null ? out : 0);

        // Expired Products
        Integer expired = productRepo.countExpiredProducts(LocalDate.now());
        response.setExpiredProducts(expired != null ? expired : 0);

        // Today Orders & Revenue
        Integer todayCount = orderRepo.getTodayOrders();
        Double todayRev = orderRepo.getTodayRevenue();
        response.setTodayOrderedCount(todayCount != null ? todayCount : 0);
        response.setTodayRevenue(todayRev != null ? todayRev : 0);

        // Monthly Revenue
        List<Object[]> monthlyResult = orderRepo.getMonthlyRevenue();
        List<Double> monthlyList = new ArrayList<>(Collections.nCopies(12, 0.0));

        for (Object[] row : monthlyResult) {
            int month = ((Number) row[0]).intValue();
            double amount = ((Number) row[1]).doubleValue();
            monthlyList.set(month - 1, amount);
        }

        response.setMonthlyRevenue(monthlyList);

        return response;
    }
}
