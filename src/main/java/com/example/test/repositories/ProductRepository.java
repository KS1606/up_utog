package com.example.test.repositories;

import com.example.test.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    List<ProductModel> findByProductNameContaining(String query);
}
