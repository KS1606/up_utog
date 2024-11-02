package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    private LocalDate orderDate;

    private BigDecimal totalAmount;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    @OneToMany(mappedBy = "order", orphanRemoval = false)
    private List<ProductModel> products = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public CustomerModel getCustomer() { return customer; }
    public void setCustomer(CustomerModel customer) { this.customer = customer; }

    public List<ProductModel> getProducts() { return products; }
    public void setProducts(List<ProductModel> products) { this.products = products; }
}
