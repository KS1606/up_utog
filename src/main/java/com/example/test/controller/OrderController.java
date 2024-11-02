package com.example.test.controller;

import com.example.test.model.CustomerModel;
import com.example.test.model.OrderModel;
import com.example.test.model.ProductModel;
import com.example.test.repositories.CustomerRepository;
import com.example.test.repositories.OrderRepository;
import com.example.test.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;


    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<OrderModel> orders = orderRepository.findByOrderNumberContaining(query);
        model.addAttribute("orders", orders);
        return "orders/index";
    }


    @Autowired
    public OrderController(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<OrderModel> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        OrderModel order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));

        String formattedDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        model.addAttribute("order", order);
        model.addAttribute("formattedOrderDate", formattedDate);
        return "orders/show";
    }


    @GetMapping("/new")
    public String newOrder(Model model) {
        model.addAttribute("order", new OrderModel());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "orders/new";
    }

    @PostMapping
    public String create(@ModelAttribute("order") OrderModel order,
                         @RequestParam("customerId") Long customerId,
                         @RequestParam("productIds") List<Long> productIds,
                         Model model) {





        List<ProductModel> products = productRepository.findAllById(productIds);

        order.setProducts(products);

        orderRepository.save(order);

        for (ProductModel product : products) {
            product.setOrder(order);
            productRepository.save(product);
        }

        return "redirect:/orders";
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        OrderModel order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));

        model.addAttribute("order", order);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        return "orders/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @RequestParam("customerId") Long customerId,
                         @RequestParam("productIds") List<Long> productIds,
                         @ModelAttribute("order") OrderModel order,
                         Model model) {
        OrderModel existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));



        existingOrder.setOrderNumber(order.getOrderNumber());
        existingOrder.setTotalAmount(order.getTotalAmount());

        List<ProductModel> newProducts = productRepository.findAllById(productIds);

        if (newProducts.isEmpty()) {
            model.addAttribute("errorMessage", "Выберите хотя бы один продукт.");
            return "orders/edit";
        }



        List<ProductModel> oldProducts = new ArrayList<>(existingOrder.getProducts());
        for (ProductModel product : oldProducts) {
            product.setOrder(null);
            productRepository.save(product);
        }

        existingOrder.getProducts().clear();

        for (ProductModel product : newProducts) {
            product.setOrder(existingOrder);
            existingOrder.getProducts().add(product);
        }

        orderRepository.save(existingOrder);

        return "redirect:/orders";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        OrderModel order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));

        for (ProductModel product : order.getProducts()) {
            product.setOrder(null);
            productRepository.save(product);
        }

        orderRepository.delete(order);
        return "redirect:/orders";
    }

}
