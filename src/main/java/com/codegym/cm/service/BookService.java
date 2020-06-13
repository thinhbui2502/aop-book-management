package com.codegym.cm.service;

import com.codegym.cm.model.Book;

import java.util.List;

public interface BookService {
    Iterable<Book> findAll();

    Book findById(Long id);

    void save(Book book);
}
