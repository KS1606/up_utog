package com.example.test.controller;

import com.example.test.model.*;
import com.example.test.repositories.HallRepository;
import com.example.test.repositories.PerformanceRepository;
import com.example.test.repositories.ShowRepository;
import com.example.test.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shows")
public class ShowController {

    @Autowired
    private final ShowRepository showRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public ShowController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<Show> shows = showRepository.findAll();
        model.addAttribute("shows", shows);
        return "shows/index";  // Возвращаем шаблон index
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("show", new Show());
        List<Hall> halls = hallRepository.findAll();
        model.addAttribute("halls", halls);
        List<Performance> performances = performanceRepository.findAll();
        model.addAttribute("performances", performances);
        return "shows/new";  // Возвращаем шаблон new
    }

    @PostMapping
    public String create(@ModelAttribute Show show) {
        showRepository.save(show);
        return "redirect:/shows";  // Перенаправляем на список показов
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Show show = showRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid show Id:" + id));
        model.addAttribute("show", show);
        List<Ticket> tickets = ticketRepository.findByShowId(id);
        model.addAttribute("tickets", tickets);
        return "shows/show";  // Возвращаем шаблон show
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Show show = showRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid show Id:" + id));
        model.addAttribute("show", show);
        List<Hall> halls = hallRepository.findAll();
        model.addAttribute("halls", halls);
        List<Performance> performances = performanceRepository.findAll();
        model.addAttribute("performances", performances);
        return "shows/edit";  // Возвращаем шаблон edit
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Show show) {
        show.setId(id); // Устанавливаем ID для обновления
        showRepository.save(show);
        return "redirect:/shows";  // Перенаправляем на список показов
    }

    @PostMapping("/{id}/delete")
    public String deleteShow(@PathVariable Long id) {
        showRepository.deleteById(id);
        return "redirect:/shows";
    }
}
