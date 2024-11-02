package com.example.test.controller;

import com.example.test.model.ProductModel;
import com.example.test.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "search", required = false) String search) {
        List<ProductModel> products = (search != null && !search.isEmpty())
                ? productRepository.findByProductNameContaining(search)
                : productRepository.findAll();
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));
        model.addAttribute("product", product);
        return "products/show";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new ProductModel());
        return "products/new";
    }

    @PostMapping
    public String create(@ModelAttribute("product") ProductModel product) {
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute("product") ProductModel product) {
        product.setId(id);
        productRepository.save(product);
        return "redirect:/products";
    }



    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }

}
