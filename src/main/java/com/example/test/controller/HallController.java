package com.example.test.controller;

import com.example.test.model.Hall;
import com.example.test.model.Theater;
import com.example.test.repositories.HallRepository;
import com.example.test.repositories.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/halls")
public class HallController {

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @GetMapping
    public String listHalls(Model model) {
        List<Hall> halls = hallRepository.findAll();
        model.addAttribute("halls", halls);
        return "halls/index"; // путь к шаблону для списка залов
    }

    @GetMapping("/{id}")
    public String getHallDetails(@PathVariable Long id, Model model) {
        Optional<Hall> hallOptional = hallRepository.findById(id);
        if (hallOptional.isPresent()) {
            model.addAttribute("hall", hallOptional.get());
            return "halls/show"; // Ваш путь к шаблону
        } else {
            return "redirect:/halls"; // Переход к списку залов, если зал не найден
        }
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("hall", new Hall());
        List<Theater> theaters = theaterRepository.findAll(); // Получаем список театров
        model.addAttribute("theaters", theaters); // Добавляем в модель
        return "halls/new"; // путь к шаблону для создания нового зала
    }

    @PostMapping
    public String createHall(@ModelAttribute Hall hall) {
        hallRepository.save(hall);
        return "redirect:/halls"; // перенаправление на список залов после создания
    }

    @GetMapping("/{id}/edit")
    public String editHall(@PathVariable Long id, Model model) {
        Optional<Hall> hallOptional = hallRepository.findById(id);
        if (hallOptional.isPresent()) {
            model.addAttribute("hall", hallOptional.get());
            model.addAttribute("theaters", theaterRepository.findAll()); // Получаем список театров
            return "halls/edit"; // Убедитесь, что путь к шаблону правильный
        } else {
            return "redirect:/halls";
        }
    }

    @PostMapping("/{id}")
    public String updateHall(@PathVariable Long id, @ModelAttribute Hall hall) {
        hall.setId(id);
        hallRepository.save(hall);
        return "redirect:/halls"; // перенаправление на список залов после редактирования
    }

    @GetMapping("/{id}/delete")
    public String deleteHall(@PathVariable Long id) {
        hallRepository.deleteById(id);
        return "redirect:/halls"; // перенаправление на список залов после удаления
    }
}