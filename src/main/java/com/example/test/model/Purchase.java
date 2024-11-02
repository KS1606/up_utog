package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Дата покупки не может быть пустой")
    private LocalDateTime purchaseDate = LocalDateTime.now();

    @Min(value = 0, message = "Сумма покупки должна быть неотрицательной")
    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "purchase_ticket",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id")
    )
    private List<Ticket> tickets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Дата покупки не может быть пустой") LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(@NotNull(message = "Дата покупки не может быть пустой") LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Min(value = 0, message = "Сумма покупки должна быть неотрицательной")
    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(@Min(value = 0, message = "Сумма покупки должна быть неотрицательной") double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
