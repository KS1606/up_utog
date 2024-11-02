package com.example.test.controller;

import com.example.test.service.UserService;
import com.example.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model, Principal principal) {
        // Логируем имя пользователя и его роли
        System.out.println("Logged in user: " + principal.getName());
        User user = userService.findUserByUsername(principal.getName());
        System.out.println("User roles: " + user.getRoles());

        model.addAttribute("users", userService.getAllUsers());
        return "admin/user_list";
    }

    @GetMapping("/{username}/edit")
    public String editUserRoles(@PathVariable String username, Model model) {
        User user = userService.findUserByUsername(username);
        model.addAttribute("user", user);
        return "admin/edit_user_roles";
    }

    @PostMapping("/{username}/roles")
    public String updateUserRoles(@PathVariable String username, @RequestParam String role) {
        userService.assignRoleToUser(username, role);
        return "redirect:/admin/users";
    }
}
