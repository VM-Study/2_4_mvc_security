package org.example.app.config;

import org.example.app.services.IdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// Определяем что это конфигурация
@Configuration
// Определяем сканирование "org.example.app" на наличие бинов
@ComponentScan(basePackages = "org.example.app")
public class AppContextConfig {

    @Bean
    public IdProvider idProvider() {
        return new IdProvider();
    }
}
