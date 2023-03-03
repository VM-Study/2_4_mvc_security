package org.example.web.controllers;

import org.example.app.exceptions.BookShelfLoginException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

// Контроллер для обработки ошибок
// @ControllerAdvice определения глобального обработчика исключений в приложении Spring
@ControllerAdvice
public class ErrorController {

    // перехватываем адрес "/404"
    @GetMapping("/404")
    public String notFoundError() {

        // перенаправляем на 404.htmlø
        return "errors/404";
    }

    // @ExceptionHandler используется для обработки исключительных ситуаций в Spring MVC контроллерах
    // Обработчик ошибки - обрабатывает ошибку BookShelfLoginException.class
    @ExceptionHandler(BookShelfLoginException.class)
    public String handleError(Model model, BookShelfLoginException exception) {
        // передача текста ошибки на страницу
        model.addAttribute("errorMessage", exception.getMessage());
        // при ошибке возвратит страницу по адресу "errors/404"
        return "errors/404";
    }
}
