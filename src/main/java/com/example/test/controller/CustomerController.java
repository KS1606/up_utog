package com.example.test.controller;

import com.example.test.model.Customer;
import com.example.test.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    // Отображение списка клиентов
    @GetMapping
    public String index(Model model) {
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        return "customers/index"; // шаблон index.html
    }

    // Отображение формы для создания нового клиента
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/new"; // шаблон new.html
    }

    // Обработка создания нового клиента
    @PostMapping
    public String create(@ModelAttribute Customer customer) {
        customerRepository.save(customer);
        return "redirect:/customers"; // редирект на список клиентов
    }

    // Отображение деталей клиента
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return "customers/show"; // шаблон show.html
    }

    // Отображение формы для редактирования клиента
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return "customers/edit"; // шаблон edit.html
    }

    // Обработка редактирования клиента
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Customer customer) {
        customer.setId(id);
        customerRepository.save(customer);
        return "redirect:/customers"; // редирект на список клиентов
    }

    // Обработка удаления клиента
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return "redirect:/customers"; // редирект на список клиентов
    }
}
