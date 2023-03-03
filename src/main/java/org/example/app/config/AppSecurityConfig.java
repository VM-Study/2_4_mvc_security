package org.example.app.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;

// Конфигурация безопасности

// Определяем что это конфигурация
@Configuration
// Аннотация Spring Security активирует фреймворк
@EnableWebSecurity
public class AppSecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppSecurityConfig.class);

    // [ In-Memory Authentication ]
    // Создает бин userDetailsService, который использует класс InMemoryUserDetailsManager
    // из Spring Security для хранения пользователей в памяти
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        logger.info("populate inmemory auth user");
        UserDetails user = User.withDefaultPasswordEncoder()
                // имея пользователя
                .username("root")
                // пароль зашифровывается через BCryptPasswordEncoder
                .password(passwordEncoder().encode("123"))
                // роль пользователя
                .roles("USER")
                .build();
        // InMemoryUserDetailsManager затем может использоваться
        // в конфигурации Spring Security для аутентификации пользователей
        return new InMemoryUserDetailsManager(user);
    }

    // код создает бин PasswordEncoder, который использует BCryptPasswordEncoder для шифрования паролей.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // [ Configuring HttpSecurity ]
    // Создает SecurityFilterChain, который представляет собой цепочку фильтров,
    // обрабатывающих запросы в соответствии с настройками Spring Security.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("config http security");
        http
                // отключение Cross-Site Request Forgery (CSRF)
                .csrf().disable()
                // начало настройки авторизации запросов
                .authorizeHttpRequests(authorize -> authorize
                        // разрешает все запросы, начинающиеся с /login*.
                        .requestMatchers("/login**").permitAll()
                        // требует аутентификации для всех остальных запросов
                        .anyRequest().authenticated()
                )
                // // настройка формы логина
                .formLogin(form -> form
                        // указывает, что страница логина находится по адресу /login
                        .loginPage("/login")
                        // указывает URL, который обрабатывает запрос на аутентификацию
                        .loginProcessingUrl("/login/auth")
                        // указывает URL для перенаправления пользователя после успешной аутентификации
                        .defaultSuccessUrl("/books/shelf", true)
                        // указывает URL для перенаправления пользователя после неудачной попытки аутентификации
                        .failureUrl("/login")
                );
        // создает и возвращает SecurityFilterChain
        return http.build();
    }

    // [ Configuring WebSecurity ]
    // создает бин webSecurityCustomizer, который используется для настройки объекта WebSecurity
    // WebSecurity используется для настройки базовой защиты на уровне веб-запросов,
    // таких как запросы на статические ресурсы (например, изображения, CSS-файлы и т.д.).
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        logger.info("config web security");
        return web -> web
                // игнорирование определенных запросов
                // т.е. Spring Security не будет выполнять на них проверки авторизации и аутентификации
                .ignoring();
                // запросов, которые начинаются с /images/**
                //.requestMatchers("/images/**");
    }
}
