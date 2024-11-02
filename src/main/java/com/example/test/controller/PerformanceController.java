package com.example.test.controller;

import com.example.test.model.Performance;
import com.example.test.repositories.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/performances")
public class PerformanceController {

    @Autowired
    private PerformanceRepository performanceRepository;

    @GetMapping
    public String index(Model model) {
        List<Performance> performances = performanceRepository.findAll();
        model.addAttribute("performances", performances);
        return "performances/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("performance", new Performance());
        return "performances/new";
    }

    @PostMapping
    public String create(@ModelAttribute Performance performance) {
        performanceRepository.save(performance);
        return "redirect:/performances";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Performance performance = performanceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid performance id: " + id));
        model.addAttribute("performance", performance);
        return "performances/show";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Performance performance = performanceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid performance id: " + id));
        model.addAttribute("performance", performance);
        return "performances/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Performance performance) {
        performance.setId(id);
        performanceRepository.save(performance);
        return "redirect:/performances";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        performanceRepository.deleteById(id);
        return "redirect:/performances";
    }


}

