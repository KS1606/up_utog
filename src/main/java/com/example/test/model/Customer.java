package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(max = 100, message = "Имя пользователя не должно превышать 100 символов")
    @Column(name = "name_user", nullable = false, length = 100)
    private String name_user;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Неправильный формат email")
    @Size(max = 100, message = "Email не должен превышать 100 символов")
    @Column(name = "email_user", nullable = false, unique = true, length = 100)
    private String email_user;

    @NotBlank(message = "Телефон не может быть пустым")
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Неправильный формат номера телефона")
    @Size(max = 15, message = "Телефон не должен превышать 15 символов")
    @Column(name = "phone_user", nullable = false, length = 15)
    private String phone_user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Purchase> purchases;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Имя пользователя не может быть пустым") @Size(max = 100, message = "Имя пользователя не должно превышать 100 символов") String getName_user() {
        return name_user;
    }

    public void setName_user(@NotBlank(message = "Имя пользователя не может быть пустым") @Size(max = 100, message = "Имя пользователя не должно превышать 100 символов") String name_user) {
        this.name_user = name_user;
    }

    public @NotBlank(message = "Email не может быть пустым") @Email(message = "Неправильный формат email") @Size(max = 100, message = "Email не должен превышать 100 символов") String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(@NotBlank(message = "Email не может быть пустым") @Email(message = "Неправильный формат email") @Size(max = 100, message = "Email не должен превышать 100 символов") String email_user) {
        this.email_user = email_user;
    }

    public @NotBlank(message = "Телефон не может быть пустым") @Pattern(regexp = "\\+?\\d{10,15}", message = "Неправильный формат номера телефона") @Size(max = 15, message = "Телефон не должен превышать 15 символов") String getPhone_user() {
        return phone_user;
    }

    public void setPhone_user(@NotBlank(message = "Телефон не может быть пустым") @Pattern(regexp = "\\+?\\d{10,15}", message = "Неправильный формат номера телефона") @Size(max = 15, message = "Телефон не должен превышать 15 символов") String phone_user) {
        this.phone_user = phone_user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}
