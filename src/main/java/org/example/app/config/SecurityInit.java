package org.example.app.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// Определение фильтра
// Этот класс автоматически регистрирует фильтр в контейнере, который будет обрабатывать все входящие запросы
// проходящие через приложение и передавать их на обработку Spring Security.
// Если класс SecurityInit наследуется от AbstractSecurityWebApplicationInitializer,
// то нет необходимости явно регистрировать фильтр в файле web.xml.
// Класс будет автоматически загружаться и фильтр будет зарегистрирован в контейнере сервлетов при запуске приложения.
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {
}

