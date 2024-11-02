package com.example.test.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    public String handleNotFound(HttpServletRequest request, NoHandlerFoundException ex, Model model) {
        model.addAttribute("message", "Страница не найдена.");
        return "error"; // Название вашего шаблона ошибок
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.FORBIDDEN)
    public String handleAccessDenied(HttpServletRequest request, AccessDeniedException ex, Model model) {
        model.addAttribute("message", "У вас нет прав для доступа к этой странице.");
        return "error"; // Название вашего шаблона ошибок
    }

    // Другие обработчики ошибок можно добавить по аналогии
}
