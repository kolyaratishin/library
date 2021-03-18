package com.example.library.Controller;

import com.example.library.Model.Book;
import com.example.library.Service.BookService;
import com.example.library.Utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping(value = "/get/{id}")
    public Book getBook(@PathVariable("id") Long id){
        return bookService.getById(id);
    }

    @PostMapping(value = "/save")
    public void saveBook(@RequestBody Book book){
        bookService.save(book);
    }

    @PostMapping(value = "/update/{id}")
    public void updateBook(@RequestBody Book book,@PathVariable("id") Long id){
        Book sourceBook = bookService.getById(id);
        ObjectMapper.mapBook(sourceBook,book);
        bookService.save(book);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteBook(@PathVariable("id") Long id){
        bookService.delete(id);
    }

    @GetMapping(value = "/list")
    public List<Book> getBookList(){
        return bookService.getAll();
    }

}
