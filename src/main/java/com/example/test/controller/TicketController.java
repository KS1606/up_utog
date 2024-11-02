package com.example.test.controller;

import com.example.test.model.Performance;
import com.example.test.model.Show;
import com.example.test.model.Ticket;
import com.example.test.repositories.PerformanceRepository;
import com.example.test.repositories.ShowRepository;
import com.example.test.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private final TicketRepository ticketRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
        List<Show> shows = showRepository.findAll();
        model.addAttribute("shows", shows);
        List<Performance> performances = performanceRepository.findAll();
        model.addAttribute("performances", performances);
        return "tickets/index"; // название шаблона для отображения списка билетов
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        List<Show> shows = showRepository.findAll();
        model.addAttribute("shows", shows);
        List<Performance> performances = performanceRepository.findAll();
        model.addAttribute("performances", performances);
        return "tickets/new"; // название шаблона для создания нового билета
    }

    @PostMapping
    public String create(@ModelAttribute Ticket ticket) {
        ticketRepository.save(ticket);
        return "redirect:/tickets"; // перенаправление на список билетов
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Неверный ID билета: " + id));
        model.addAttribute("ticket", ticket);
        return "tickets/show"; // название шаблона для отображения деталей билета
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Неверный ID билета: " + id));
        model.addAttribute("ticket", ticket);
        List<Show> shows = showRepository.findAll();
        model.addAttribute("shows", shows);
        List<Performance> performances = performanceRepository.findAll();
        model.addAttribute("performances", performances);
        return "tickets/edit"; // название шаблона для редактирования билета
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Ticket ticket) {
        ticket.setId(id);
        ticketRepository.save(ticket);
        return "redirect:/tickets"; // перенаправление на список билетов
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        ticketRepository.deleteById(id);
        return "redirect:/tickets"; // перенаправление на список билетов
    }
}
