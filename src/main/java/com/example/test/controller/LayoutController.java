package com.example.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LayoutController {

    @GetMapping("/")
    public String showLayout(Model model) {
        return "layout";
    }

    @GetMapping("/home")
    public String show(Model model) {
        return "home";
    }

    @GetMapping("/home_admn")
    public String showAdmin(Model model) {
        return "home_admn";
    }

    @GetMapping("/home_user")
    public String showUser(Model model) {
        return "home_user";
    }

    @GetMapping("/home_usher")
    public String showUsher(Model model) {
        return "home_usher";
    }
}
