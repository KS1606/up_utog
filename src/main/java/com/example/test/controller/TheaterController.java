package com.example.test.controller;

import com.example.test.model.Theater;
import com.example.test.repositories.HallRepository;
import com.example.test.repositories.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/theaters")
public class TheaterController {

    @Autowired
    private final TheaterRepository theaterRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    public TheaterController(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    // Главная страница со списком театров
    @GetMapping
    public String index(Model model) {
        model.addAttribute("theaters", theaterRepository.findAll());
        return "theaters/index";
    }

    @GetMapping("/{id}")
    public String showTheater(@PathVariable Long id, Model model) {
        Optional<Theater> theaterOptional = theaterRepository.findById(id);
        if (theaterOptional.isPresent()) {
            Theater theater = theaterOptional.get();
            model.addAttribute("theater", theater);
            model.addAttribute("halls", hallRepository.findByTheaterId(id)); // Получаем залы, связанные с театром
            return "theaters/show"; // Убедитесь, что путь к шаблону правильный
        } else {
            return "redirect:/theaters";
        }
    }

    // Форма для создания нового театра
    @GetMapping("/new")
    public String newTheater(Model model) {
        model.addAttribute("theater", new Theater());
        return "theaters/new";
    }

    // Сохранение нового театра
    @PostMapping
    public String create(@ModelAttribute("theater") Theater theater, RedirectAttributes redirectAttributes) {
        theaterRepository.save(theater);
        redirectAttributes.addFlashAttribute("message", "Театр успешно добавлен!");
        return "redirect:/theaters";
    }

    // Форма редактирования существующего театра
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Theater> theaterOpt = theaterRepository.findById(id);
        if (theaterOpt.isPresent()) {
            model.addAttribute("theater", theaterOpt.get());
            return "theaters/edit";
        } else {
            redirectAttributes.addFlashAttribute("error", "Театр не найден для редактирования");
            return "redirect:/theaters";
        }
    }

    // Обновление театра после редактирования
    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("theater") Theater theater, RedirectAttributes redirectAttributes) {
        theater.setId(id); // Установим ID, чтобы обновить существующую запись
        theaterRepository.save(theater);
        redirectAttributes.addFlashAttribute("message", "Театр успешно обновлен!");
        return "redirect:/theaters";
    }

    // Удаление театра
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (theaterRepository.existsById(id)) {
            theaterRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Театр успешно удален!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Не удалось найти театр для удаления");
        }
        return "redirect:/theaters";
    }
}
