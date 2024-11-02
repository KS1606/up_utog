package com.example.test.controller;

import com.example.test.model.EmployeeModel;
import com.example.test.model.ProductModel;
import com.example.test.repositories.EmployeeRepository;
import com.example.test.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, ProductRepository productRepository) {
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employees/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        EmployeeModel employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            model.addAttribute("employee", employee);
        }
        return "employees/show";
    }

    @GetMapping("/new")
    public String newEmployee(Model model) {
        model.addAttribute("employee", new EmployeeModel());
        model.addAttribute("products", productRepository.findAll());
        return "employees/new";
    }

    @PostMapping
    public String create(@ModelAttribute("employee") EmployeeModel employee, @RequestParam("productIds") List<Long> productIds) {
        List<ProductModel> products = productRepository.findAllById(productIds);
        employee.setProducts(products);
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        try {
            EmployeeModel employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
            model.addAttribute("employee", employee);
            model.addAttribute("products", productRepository.findAll());
            return "employees/edit";
        } catch (Exception e) {
            // Логируем ошибку
            System.err.println("Ошибка при редактировании сотрудника: " + e.getMessage());
            model.addAttribute("errorMessage", "Ошибка при загрузке данных");
            return "error";
        }
    }


    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("employee") EmployeeModel employee, @RequestParam("productIds") List<Long> productIds) {
        employee.setId(id);
        List<ProductModel> products = productRepository.findAllById(productIds);
        employee.setProducts(products);
        employeeRepository.save(employee);
        return "redirect:/employees";
    }


    // Обработка удаления сотрудника
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }

    // Метод для поиска сотрудников по имени
    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        List<EmployeeModel> employees = employeeRepository.findByNameContainingIgnoreCase(name);
        model.addAttribute("employees", employees);
        return "employees/index";
    }
}
