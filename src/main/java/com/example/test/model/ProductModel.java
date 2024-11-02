package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название продукта не должно быть пустым")
    @Size(max = 100, message = "Название продукта должно быть меньше 100 символов")
    private String productName;

    @Min(value = 0, message = "Цена должна быть положительной")
    private double price;

    @Min(value = 0, message = "Количество должно быть положительным")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;  // Связь с заказом

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public OrderModel getOrder() { return order; }
    public void setOrder(OrderModel order) { this.order = order; }
}
