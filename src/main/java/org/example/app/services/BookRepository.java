package org.example.app.services;

import org.example.web.controllers.BookShelfController;
import org.example.web.dto.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// к BookRepository добавляем имплементацию ApplicationContextAware для доступа к applicationContext
@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    @Override
    // возвращаем копию листа всех книг
    public List<Book> retrieveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        // обращаемся к контексту context и получаем по классу IdProvider.class требуемый бин provideId(book)
        String provideId = context.getBean(IdProvider.class).provideId(book);
        book.setId(provideId);
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(String bookIdToRemove) {
        for (Book book : retrieveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    // для implements ApplicationContextAware
    // получаем applicationContext
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
