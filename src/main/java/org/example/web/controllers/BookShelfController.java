package org.example.web.controllers;

import jakarta.validation.Valid;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Controller
@RequestMapping(value = "books")
// по умолчанию Scope("singleton")
@Scope("singleton")
public class BookShelfController {
    private static final Logger logger = LoggerFactory.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got books shelf {}", this);
        model.addAttribute("book", new Book());
        // создаем новый экземпляр DTO BookIdToRemove
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    // @Valid поле должно быть проверено на валидность вложенного объекта
    // BindingResult - объект содержит результаты валидации
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // в валидации были ошибки - отображаем общий список книг
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("error validation book: {}", book.getId());
            return "book_shelf";

        } else {
            // ошибок не было в валидации
            logger.info("validation is ok - save book: {}", book.getId());
            bookService.saveBook(book);
            logger.info("current book repository size: {}", bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    // на вход принимаем DTO объект BookIdToRemove
    @PostMapping("/remove")
    // @Valid поле должно быть проверено на валидность вложенного объекта
    // BindingResult - объект содержит результаты валидации
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        // выполняем валидацию
        if (bindingResult.hasErrors()) {
            // в валидации были ошибки - отображаем общий список книг
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            logger.info("error validation book: {}", bookIdToRemove.getId());
            return "book_shelf";

        } else {
            // ошибок не было в валидации - удаляем
            logger.info("validation is ok - remove book: {}", bookIdToRemove.getId());
            bookService.removeBookById(bookIdToRemove.getId());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByRegex")
    public String removeBookByRegex(@RequestParam(value = "queryRegex") String queryRegex) {
        //        addTmpBook();

        // todo не нашел более элегантного способа распарсить текст
        int size = 0;
        try {
            size = Integer.parseInt(queryRegex);
        } catch (NumberFormatException ex) {
        }

        int finalSize = size;
        List<Book> removeBookList = bookService.getAllBooks().stream()
                .filter(book -> book.getAuthor().equals(queryRegex)
                        || book.getTitle().equals(queryRegex)
                        || book.getSize() == finalSize)
                .toList();
        removeBookList.forEach(book -> bookService.removeBookById(book.getId()));
        return "redirect:/books/shelf";
    }

    private void addTmpBook() {
        Book book1 = new Book();
        book1.setAuthor("1aaa");
        book1.setTitle("1bbb");
        book1.setSize(111);
        bookService.saveBook(book1);
        Book book2 = new Book();
        book2.setAuthor("2aaa");
        book2.setTitle("2bbb");
        book2.setSize(222);
        bookService.saveBook(book2);
        Book book3 = new Book();
        book3.setAuthor("3aaa");
        book3.setTitle("3bbb");
        book3.setSize(333);
        bookService.saveBook(book3);
    }

}
