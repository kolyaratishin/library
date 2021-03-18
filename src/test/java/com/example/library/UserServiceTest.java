package com.example.library;


import com.example.library.Exception.BookAlreadyReturnedException;
import com.example.library.Exception.BookAlreadyTakenException;
import com.example.library.Exception.UserDoesNotHaveBook;
import com.example.library.Exception.UserNotFoundException;
import com.example.library.Model.Book;
import com.example.library.Model.User;
import com.example.library.Repository.UserRepository;
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
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookServiceImpl bookService;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void saveUserTest(){
        User user = new User();
        user.setUsername("user");
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        User savedUser = userService.save(user);
        assertThat(savedUser.getUsername()).isSameAs(user.getUsername());

        verify(userRepository).save(user);
    }
    @Test
    public void deleteUserTest(){
        User user = new User();
        user.setUsername("user");
        user.setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.delete(user.getId());

        verify(userRepository).deleteById(user.getId());
    }
    @Test
    public void deleteUserWhenUserDoesNotExistTest(){
        User user = new User();

        user.setUsername("null_user");
        user.setId(89L);

        given(userRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> userService.delete(user.getId())
        );

    }

    @Test
    public void UserListTest(){
        List<User> users = new ArrayList<>();
        users.add(new User());

        given(userRepository.findAll()).willReturn(users);

        List<User> expectedUsers = userService.getAll();

        assertEquals(expectedUsers,users);

        verify(userRepository).findAll();
    }

    @Test
    public void takeBookTest(){
        Book book = new Book();
        book.setId(1L);
        book.setName("book");

        User user = new User();
        user.setId(1L);
        user.setUsername("user");

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        doReturn(book).when(bookService).getById(book.getId());

        userService.takeBook(book.getId(),user.getId());

        assertTrue(book.isTaken());
        assertEquals(book.getUser(),user);
        assertTrue(user.getBooks().contains(book));

    }
    @Test
    public void takeBookThatAlreadyIsTakenTest(){
        Book book = new Book();
        book.setId(1L);
        book.setName("book");
        book.setTaken(true);

        User user = new User();
        user.setId(1L);
        user.setUsername("user");

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        doReturn(book).when(bookService).getById(book.getId());

        assertThrows(BookAlreadyTakenException.class,
                () -> userService.takeBook(book.getId(),user.getId())
        );

    }
    @Test
    public void returnBookTest(){
        Book book = new Book();
        book.setId(1L);
        book.setName("book");
        book.setTaken(true);

        User user = new User();
        user.setId(1L);
        user.setUsername("user");

        book.setUser(user);
        user.addBook(book);

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        doReturn(book).when(bookService).getById(book.getId());

        userService.returnBook(book.getId(),user.getId());

        assertFalse(book.isTaken());
        assertNull(book.getUser());
        assertFalse(user.getBooks().contains(book));

    }
    @Test
    public void returnBookThatIsNotTakenTest(){
        Book book = new Book();
        book.setId(1L);
        book.setName("book");
        book.setTaken(false);

        User user = new User();
        user.setId(1L);
        user.setUsername("user");

        book.setUser(user);
        user.addBook(book);

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        doReturn(book).when(bookService).getById(book.getId());


        assertThrows(BookAlreadyReturnedException.class,
                () -> userService.returnBook(book.getId(),user.getId())
        );

    }
    @Test
    public void returnBookByUserWhoDoesNotHaveItTest(){
        Book book = new Book();
        book.setId(1L);
        book.setName("book");
        book.setTaken(true);

        User user = new User();
        user.setId(1L);
        user.setUsername("user");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        book.setUser(user);
        user.addBook(book);

        given(userRepository.findById(user2.getId())).willReturn(Optional.of(user2));
        doReturn(book).when(bookService).getById(book.getId());


        assertThrows(UserDoesNotHaveBook.class,
                () -> userService.returnBook(book.getId(),user2.getId())
        );

    }

}
