package org.example.app.services;

import org.example.web.controllers.LoginController;
import org.example.web.dto.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    public boolean authenticate(LoginForm loginForm) {
        logger.info("try auth with user-from: {}", loginForm);
        return loginForm.getUsername().equals("root")
                && loginForm.getPassword().equals("123");
    }
}
