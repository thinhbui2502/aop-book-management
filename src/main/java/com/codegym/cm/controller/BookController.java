package com.codegym.cm.controller;

import com.codegym.cm.model.Book;
import com.codegym.cm.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ModelAndView showBooks() {
        Iterable<Book> books = bookService.findAll();
        ModelAndView modelAndView = new ModelAndView("book/list");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping("/books/{id}")
    public ModelAndView viewBook(@PathVariable ("id") Long id) {
        Book book = bookService.findById(id);
        if (book == null) {
            return new ModelAndView("book/error.404");
        }
        ModelAndView modelAndView = new ModelAndView("book/view");
        modelAndView.addObject("book", book);
        return modelAndView;
    }

    @GetMapping("/books/borrow/{id}")
    public ModelAndView borrowBook(@PathVariable Long id) {
        Book book = bookService.findById(id);
        if (book == null) {
            return new ModelAndView("book/error.404");
        }
        book.setQuantity(book.getQuantity() -1);
        if (book.getQuantity() < 0) {
            return new ModelAndView("book/error.404");
        }
        bookService.save(book);
        Iterable<Book> books = bookService.findAll();
        ModelAndView modelAndView = new ModelAndView("book/list");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping("/books/get/{id}")
    public ModelAndView getBook(@PathVariable Long id) {
        Book book = bookService.findById(id);
        if (book == null) {
            return new ModelAndView("book/error.404");
        }
        book.setQuantity(book.getQuantity() +1);
        bookService.save(book);
        Iterable<Book> books = bookService.findAll();
        ModelAndView modelAndView = new ModelAndView("book/list");
        modelAndView.addObject("books", books);
        return modelAndView;
    }
}
