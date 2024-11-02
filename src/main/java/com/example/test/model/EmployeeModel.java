package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "employees")
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не должно быть пустым")
    @Size(max = 100, message = "Имя должно быть меньше 100 символов")
    private String name;

    @NotBlank(message = "Должность не должна быть пустой")
    @Size(max = 50, message = "Должность должна быть меньше 50 символов")
    private String position;

    @Min(value = 0, message = "Зарплата должна быть положительной")
    private double salary;

    @ManyToMany
    @JoinTable(
            name = "employee_product",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductModel> products;

    public EmployeeModel() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public List<ProductModel> getProducts() { return products; }
    public void setProducts(List<ProductModel> products) { this.products = products; }
}
