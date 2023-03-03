package org.example.app.services;

import org.example.web.dto.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class IdProvider implements InitializingBean, DisposableBean, BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(IdProvider.class);
    public String provideId(Book book) {
        // генерирует новый ID
        // hashCode IdProvider + _ + hashCode book
        return this.hashCode() + "_" + book.hashCode();
    }

        // init-method="initIdProvider" для бина id="idProvider"
        private void initIdProvider() {
            logger.info("provider INIT");
        }

        // destroy-method="destroyIdProvider" для бина id="idProvider"
        private void destroyIdProvider() {
            logger.info("provider DESTROY");
        }

        // глобальный default-init-method="defaultInit" для всех бинов app-config.xml
        private void defaultInit() {
            logger.info("default INIT in provider");
        }

        // глобальный default-destroy-method="defaultDestroy" для всех бинов app-config.xml
        private void defaultDestroy() {
            logger.info("default DESTROY in provider");

        }

        @Override
        public void afterPropertiesSet() throws Exception {
            logger.info("provider afterPropertiesSet");
        }

        @Override
        public void destroy() throws Exception {
            logger.info("DisposibleBean destroy invoke");
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            logger.info("postProcessBeforeInitialization invoke bt bean {}", beanName);
            return null;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            logger.info("postProcessAfterInitialization invoke bt bean {}", beanName);
            return null;
        }

        // требует зависимости javax.annotation-api
        @PostConstruct
        public void postConstructIdProvider() {
            logger.info("PostConstruct annotated method called");
        }

        // требует зависимости javax.annotation-api
        @PreDestroy
        public void preDestroyIdProvider() {
            logger.info("PreDestroy annotated method called");
        }

}
