package com.example.test.repositories;

import com.example.test.model.EmployeeModel;
import com.example.test.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {
    List<EmployeeModel> findByNameContainingIgnoreCase(String name);
    List<EmployeeModel> findByProductsContaining(ProductModel product);
}
