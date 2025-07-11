package com.example.medical_billing.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.example.medical_billing.model.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

    @Query("SELECT SUM(c.totalAmount) FROM CustomerOrder c")
    Double getTotalRevenue();

    @Query("SELECT COUNT(c) FROM CustomerOrder c WHERE  DATE(c.invoiceDate) = CURRENT_DATE")
    Integer getTodayOrders();

    @Query("SELECT SUM(c.totalAmount) FROM CustomerOrder c WHERE  DATE(c.invoiceDate) = CURRENT_DATE")
    Double getTodayRevenue();

    @Query(value = """
                SELECT
                    MONTH(invoice_date) AS month,
                    SUM(total_amount) AS revenue
                FROM customer_order
                WHERE YEAR(invoice_date) = YEAR(CURDATE())
                GROUP BY MONTH(invoice_date)
                ORDER BY MONTH(invoice_date)
            """, nativeQuery = true)
    List<Object[]> getMonthlyRevenue();
}
