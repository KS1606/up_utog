package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "Номер места должен быть положительным")
    private int seatNumber;

    @Positive(message = "Цена должна быть положительным числом")
    private double price;

    @NotBlank(message = "Статус не может быть пустым")
    @Pattern(regexp = "доступен|зарезервирован|продан",
            message = "Статус должен быть одним из: доступен, зарезервирован, продан")
    private String status = "доступен";

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @ManyToMany(mappedBy = "tickets")
    private List<Purchase> purchases;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Positive(message = "Номер места должен быть положительным")
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(@Positive(message = "Номер места должен быть положительным") int seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Positive(message = "Цена должна быть положительным числом")
    public double getPrice() {
        return price;
    }

    public void setPrice(@Positive(message = "Цена должна быть положительным числом") double price) {
        this.price = price;
    }

    public @NotBlank(message = "Статус не может быть пустым") @Pattern(regexp = "доступен|зарезервирован|продан",
            message = "Статус должен быть одним из: доступен, зарезервирован, продан") String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank(message = "Статус не может быть пустым") @Pattern(regexp = "доступен|зарезервирован|продан",
            message = "Статус должен быть одним из: доступен, зарезервирован, продан") String status) {
        this.status = status;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}
