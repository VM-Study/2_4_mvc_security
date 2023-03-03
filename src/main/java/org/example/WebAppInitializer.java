package org.example;

import jakarta.servlet.ServletRegistration;
import org.example.app.config.AppContextConfig;
import org.example.web.config.WebContextConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

// Замена файла src/main/webapp/WEB-INF/web.xml
// определение конфигурации приложения
// определение диспетчера сервлетов
// требуется зависимость jakarta.servlet-api
public class WebAppInitializer implements WebApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(WebAppInitializer.class);

    @Override
    public void onStartup(jakarta.servlet.ServletContext servletContext) {

        // определение конфигурации приложения
        logger.info("loading app config");
        // [ app-config.xml конфигурация бинов для рут контекста ]
        // XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        // appContext.setConfigLocation("classpath:app-config.xml");
        // [ Анотация вместо конфиг файла ]
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        // указывается класс с анотациями - настройками /app/config/AppContextConfig.java
        appContext.register(AppContextConfig.class);
        servletContext.addListener(new ContextLoaderListener(appContext));

        // определение диспетчера сервлетов
        logger.info("loading web config");
        // [ web-config.xml конфигурация бинов для веба ]
        // XmlWebApplicationContext webContext = new XmlWebApplicationContext();
        // webContext.setConfigLocation("classpath:web-config.xml");
        // [ Анотация вместо конфиг файла ]
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        // указывается класс с анотациями - настройками /web/config/WebContextConfig.java
        webContext.register(WebContextConfig.class);


        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        // указываем приоритет (первый) загрузки dispatcherServlet
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        logger.info("dispatcher is ready");

    }
}
