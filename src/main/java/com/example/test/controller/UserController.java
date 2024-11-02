package com.example.test.controller;

import com.example.test.model.*;
import com.example.test.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TheaterRepository theaterRepository;


    // Метод для просмотра всех театров
    @GetMapping("/theaters")
    public String listTheaters(Model model) {
        List<Theater> theaters = theaterRepository.findAll();
        model.addAttribute("theaters", theaters);
        return "user/theater_list"; // Здесь должен быть путь к вашему шаблону
    }

    // Метод для просмотра информации о театре
    @GetMapping("/theaters/{id}")
    public String viewTheater(@PathVariable Long id, Model model) {
        Theater theater = theaterRepository.findById(id).orElse(null);
        model.addAttribute("theater", theater);
        return "user/theater_details"; // Здесь должен быть путь к вашему шаблону
    }

}
