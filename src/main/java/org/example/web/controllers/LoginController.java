package org.example.web.controllers;

import org.example.app.exceptions.BookShelfLoginException;
import org.example.app.services.LoginService;
import org.example.web.dto.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;




// указываем что это контроллер
// общий путь для всех /login (базовый путь)
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private LoginService loginService;

    // внедряем бин LoginService в качестве зависимости в конструкторе
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // итоговый путь будет: "/login" от базового
    @GetMapping
    public ModelAndView home(Model model) {
        logger.info("GET /login returns login_page.html");

        // добавляем в аттрибуты: new LoginForm()
        model.addAttribute("loginForm", new LoginForm());
        return new ModelAndView("login_page");
    }

    // итоговый путь будет: "/login/auth" от базового
    @PostMapping("/auth")
    public String authenticate(LoginForm loginForm) throws BookShelfLoginException {
        if (loginService.authenticate(loginForm)) {
            logger.info("login OK redirect to book shelf");
            return "redirect:/books/shelf";
        } else {
            logger.info("login FAIL redirect to login");
            // выбрасываем собственное исключение если логин не верен
            throw new BookShelfLoginException("invalid username or password");
        }
    }
}
