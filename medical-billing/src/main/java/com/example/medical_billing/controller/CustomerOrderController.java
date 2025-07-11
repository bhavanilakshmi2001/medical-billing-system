package com.example.medical_billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.medical_billing.model.CustomerOrder;
import com.example.medical_billing.model.OrderItem;
import com.example.medical_billing.repository.CustomerOrderRepository;
import com.example.medical_billing.service.InvoiceService;

@RestController
@RequestMapping("/orders")
public class CustomerOrderController {
    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/create")
public ResponseEntity<?> addOrder(@RequestBody CustomerOrder customerOrder) {
    double subtotal = 0.0;

    for (OrderItem item : customerOrder.getItems()) {
        item.setId(null);
        item.setOrder(customerOrder);

        double itemTotal = item.getPrice() * item.getQuantity();
        item.setTotal(itemTotal);

        subtotal += itemTotal;
    }

    customerOrder.setSubtotal(subtotal); //Save subtotal
    customerOrder.setTotalAmount(subtotal); // if total == subtotal

    CustomerOrder savedOrder = customerOrderRepository.save(customerOrder);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
}



    @GetMapping("/allOrder")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(customerOrderRepository.findAll());
    }

    @GetMapping("/invoice-pdf")
    public ResponseEntity<byte[]> getInvoicePdf(@RequestParam Long id) {
        CustomerOrder order = customerOrderRepository.findById(id).orElse(null);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        byte[] pdfBytes = invoiceService.generateInvoicePdf(order);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=invoice_" + order.getInvoiceNumber() + ".pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

}
