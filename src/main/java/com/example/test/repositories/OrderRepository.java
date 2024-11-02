package com.example.test.repositories;

import com.example.test.model.CustomerModel;
import com.example.test.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    List<OrderModel> findByOrderNumberContaining(String orderNumber);
    List<OrderModel> findByCustomer(CustomerModel customer);
}
