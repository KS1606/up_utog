package com.example.test.controller;

import com.example.test.model.Performance;
import com.example.test.model.Purchase;
import com.example.test.model.Customer;
import com.example.test.model.Ticket;
import com.example.test.repositories.PerformanceRepository;
import com.example.test.repositories.PurchaseRepository;
import com.example.test.repositories.CustomerRepository;
import com.example.test.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    @GetMapping
    public String index(Model model) {
        List<Purchase> purchases = purchaseRepository.findAll();
        model.addAttribute("purchases", purchases);
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
        return "purchases/index";
    }

    @GetMapping("/new")
    public String newPurchase(Model model) {
        model.addAttribute("purchase", new Purchase());
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
        List<Performance> performances = performanceRepository.findAll();
        model.addAttribute("performances", performances);
        return "purchases/new";
    }


    @PostMapping
    public String create(@ModelAttribute Purchase purchase) {
        purchaseRepository.save(purchase);
        return "redirect:/purchases"; // перенаправление на список билетов
    }


    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid purchase ID: " + id));
        model.addAttribute("purchase", purchase);
        List<Performance> performances = performanceRepository.findAll();
        model.addAttribute("performances", performances);
        return "purchases/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid purchase ID: " + id));
        model.addAttribute("purchase", purchase);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("tickets", ticketRepository.findAll());
        List<Performance> performances = performanceRepository.findAll();
        model.addAttribute("performances", performances);
        return "purchases/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Purchase purchase) {
        purchase.setId(id);
        purchaseRepository.save(purchase);
        return "redirect:/purchases";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        purchaseRepository.deleteById(id);
        return "redirect:/purchases";
    }
}
