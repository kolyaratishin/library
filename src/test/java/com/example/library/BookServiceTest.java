package com.example.library;

import com.example.library.Model.Book;
import com.example.library.Repository.BookRepository;
import com.example.library.Service.BookServiceImpl;
import com.example.library.Service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void saveBookTest(){
        Book book = new Book();
        book.setName("book");

        when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(book);

        Book savedBook = bookService.save(book);

        assertThat(savedBook.getName()).isSameAs(book.getName());

        verify(bookRepository).save(book);
    }
    @Test
    public void deleteBookTest(){
        Book book = new Book();
        book.setName("book");
        book.setId(1L);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        bookService.delete(book.getId());

        verify(bookRepository).deleteById(book.getId());
    }
    @Test
    public void deleteBookWhenBookDoesNotExistTest(){
        Book book = new Book();

        book.setName("null_book");
        book.setId(89L);

        given(bookRepository.findById(anyLong())).willReturn(Optional.empty());

        bookService.delete(book.getId());

    }

    @Test
    public void BookListTest(){
        List<Book> books = new ArrayList<>();
        books.add(new Book());

        given(bookRepository.findAll()).willReturn(books);

        List<Book> expectedBooks = bookService.getAll();

        assertEquals(expectedBooks,books);

        verify(bookRepository).findAll();
    }



}
