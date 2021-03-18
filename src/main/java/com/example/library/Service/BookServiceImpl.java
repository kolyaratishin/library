package com.example.library.Service;

import com.example.library.Exception.*;
import com.example.library.Model.Book;
import com.example.library.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                ()-> new BookNotFoundException("Book with that id is not found")
        );
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book save(Book book) {
      return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }


}
