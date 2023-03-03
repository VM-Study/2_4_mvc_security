package org.example.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

// @Configuration - аннотация конфигурация web
@Configuration

// @ComponentScan - аннотация по которому контейнер инверсии контроллер будет сканировать аттрибуты на наличие бинов
// <context:component-scan base-package="org.example.web"/>
@ComponentScan(basePackages = "org.example.web")

// @EnableWebMvc - аннотация для подключения аннотации mvc
//  <mvc:annotation-driven/>
@EnableWebMvc
public class WebContextConfig implements WebMvcConfigurer {

    // мепинг ресурсов (implements WebMvcConfigurer)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // подключение ресурсов (картинки)
        // <mvc:resources mapping="/**" location="classpath:images"/>
        registry.addResourceHandler("/**").addResourceLocations("classpath:images");

    }

    // Подключение Thymeleaf к Spring 6

    // SpringResourceTemplateResolver автоматически интегрируется с собственной
    // инфраструктурой разрешения ресурсов Spring.
    //     <bean id="templateResolver"
    //          class="org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver">
    //        <property name="prefix" value="/WEB-INF/views/"/>
    //        <property name="suffix" value=".html"/>
    //        <!-- HTML значение по-умолчанию, добавлено здесь ради ясности. -->
    //        <property name="templateMode" value="HTML" />
    //        <!-- Cache страниц по-умолчанию включен (значение true).                                  -->
    //        <!-- Установите false если хотите чтобы шаблоны автоматически обновлялись при изменении. -->
    //        <property name="cacheable" value="true" />
    //    </bean>
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        return templateResolver;
    }

    // SpringTemplateEngine автоматически применяет SpringStandardDialect и
    // включает собственный механизм разрешения сообщений Spring.
    //     <bean id="templateEngine"
    //          class="org.thymeleaf.spring6.SpringTemplateEngine">
    //        <property name="templateResolver" ref="templateResolver"/>
    //        <!-- Включение компилятора SpringEL с Spring 4.2.4 или более новым может ускорить                   -->
    //        <!-- выполнение в большинстве случаев, но может быть несовместима в некоторых                       -->
    //        <!-- случаях, когда выражение в одном шаблоне многократно используется с различными типами данными, -->
    //        <!-- поэтому этот флаг по-умолчанию установлен в "false" для лучшей обратной совместимости.         -->
    //        <property name="enableSpringELCompiler" value="true"/>
    //    </bean>
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    // ThymeleafViewResolver Подключение самого Thymeleaf
    //     <bean class="org.thymeleaf.spring6.view.ThymeleafViewResolver">
    //        <property name="templateEngine" ref="templateEngine"/>
    //        <property name="order" value="1"/>
    //    </bean>
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        return viewResolver;
    }

}
