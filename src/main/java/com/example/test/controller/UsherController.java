package com.example.test.controller;

import com.example.test.model.Hall;
import com.example.test.model.Performance;
import com.example.test.model.Theater;
import com.example.test.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usher")
public class UsherController {

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    // Главная страница со списком театров
    @GetMapping("/theaters")
    public String index(Model model) {
        model.addAttribute("theaters", theaterRepository.findAll());
        return "theaters/index";
    }

    @GetMapping("/theaters/{id}")
    public String showTheater(@PathVariable Long id, Model model) {
        Optional<Theater> theaterOptional = theaterRepository.findById(id);
        if (theaterOptional.isPresent()) {
            Theater theater = theaterOptional.get();
            model.addAttribute("theater", theater);
            model.addAttribute("halls", hallRepository.findByTheaterId(id));
            return "theaters/show";
        } else {
            return "redirect:/usher/theaters";
        }
    }

    @GetMapping("/theaters/new")
    public String newTheater(Model model) {
        model.addAttribute("theater", new Theater());
        return "theaters/new";
    }

    @PostMapping("/theaters")
    public String create(@ModelAttribute("theater") Theater theater, RedirectAttributes redirectAttributes) {
        theaterRepository.save(theater);
        redirectAttributes.addFlashAttribute("message", "Театр успешно добавлен!");
        return "redirect:/usher/theaters";
    }

    @GetMapping("/theaters/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Theater> theaterOpt = theaterRepository.findById(id);
        if (theaterOpt.isPresent()) {
            model.addAttribute("theater", theaterOpt.get());
            return "theaters/edit";
        } else {
            redirectAttributes.addFlashAttribute("error", "Театр не найден для редактирования");
            return "redirect:/usher/theaters";
        }
    }

    @PostMapping("/theaters/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("theater")
    Theater theater, RedirectAttributes redirectAttributes) {
        theater.setId(id);
        theaterRepository.save(theater);
        redirectAttributes.addFlashAttribute("message", "Театр успешно обновлен!");
        return "redirect:/usher/theaters";
    }

    @PostMapping("/theaters/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (theaterRepository.existsById(id)) {
            theaterRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Театр успешно удален!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Не удалось найти театр для удаления");
        }
        return "redirect:/usher/theaters";
    }

    // Залы
    @GetMapping("/halls")
    public String listHalls(Model model) {
        List<Hall> halls = hallRepository.findAll();
        model.addAttribute("halls", halls);
        return "halls/index";
    }

    @GetMapping("/halls/{id}")
    public String getHallDetails(@PathVariable Long id, Model model) {
        Optional<Hall> hallOptional = hallRepository.findById(id);
        if (hallOptional.isPresent()) {
            model.addAttribute("hall", hallOptional.get());
            return "halls/show";
        } else {
            return "redirect:/usher/halls";
        }
    }

    @GetMapping("/halls/new")
    public String showCreateForm(Model model) {
        model.addAttribute("hall", new Hall());
        List<Theater> theaters = theaterRepository.findAll();
        model.addAttribute("theaters", theaters);
        return "halls/new";
    }

    @PostMapping("/halls")
    public String createHall(@ModelAttribute Hall hall) {
        hallRepository.save(hall);
        return "redirect:/usher/halls";
    }

    @GetMapping("/halls/{id}/edit")
    public String editHall(@PathVariable Long id, Model model) {
        Optional<Hall> hallOptional = hallRepository.findById(id);
        if (hallOptional.isPresent()) {
            model.addAttribute("hall", hallOptional.get());
            model.addAttribute("theaters", theaterRepository.findAll());
            return "halls/edit";
        } else {
            return "redirect:/usher/halls";
        }
    }

    @PostMapping("/halls/{id}")
    public String updateHall(@PathVariable Long id, @ModelAttribute Hall hall) {
        hall.setId(id);
        hallRepository.save(hall);
        return "redirect:/usher/halls";
    }

    @PostMapping("/halls/{id}/delete")
    public String deleteHall(@PathVariable Long id) {
        hallRepository.deleteById(id);
        return "redirect:/usher/halls";
    }

    // Перфомансы
    @GetMapping("/performances")
    public String listPerformances(Model model) {
        List<Performance> performances = performanceRepository.findAll();
        model.addAttribute("performances", performances);
        return "performance/index";
    }

    @GetMapping("/performances/new")
    public String createForm(Model model) {
        model.addAttribute("performance", new Performance());
        return "performances/new";
    }

    @PostMapping("/performances")
    public String create(@ModelAttribute Performance performance) {
        performanceRepository.save(performance);
        return "redirect:/usher/performances";
    }

    @GetMapping("/performances/{id}")
    public String show(@PathVariable Long id, Model model) {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid performance id: " + id));
        model.addAttribute("performance", performance);
        return "performance/show";
    }

    @GetMapping("/performances/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid performance id: " + id));
        model.addAttribute("performance", performance);
        return "performances/edit";
    }

    @PostMapping("/performances/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Performance performance) {
        performance.setId(id);
        performanceRepository.save(performance);
        return "redirect:/usher/performances";
    }

    @PostMapping("/performances/{id}/delete")
    public String delete(@PathVariable Long id) {
        performanceRepository.deleteById(id);
        return "redirect:/usher/performances";
    }
}
