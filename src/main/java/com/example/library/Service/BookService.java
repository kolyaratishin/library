package com.example.library.Service;

import com.example.library.Model.Book;
import com.example.library.Model.User;

import java.util.List;

public interface BookService {
    Book getById(Long id);
    List<Book> getAll();
    Book save(Book book);
    void delete(Long id);
}
