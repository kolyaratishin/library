package com.example.library.Service;

import com.example.library.Exception.BookAlreadyReturnedException;
import com.example.library.Exception.BookAlreadyTakenException;
import com.example.library.Exception.UserDoesNotHaveBook;
import com.example.library.Exception.UserNotFoundException;
import com.example.library.Model.Book;
import com.example.library.Model.User;
import com.example.library.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookService bookService;

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                ()->new UserNotFoundException("User with that id is not found")
        );
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
       return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        userRepository.deleteById(id);
    }

    @Override
    public void takeBook(Long bookId, Long userId) {
        User user = getById(userId);
        Book book = bookService.getById(bookId);
        if(book.isTaken()){
            throw new BookAlreadyTakenException("Book is already taken");
        }
        book.setTaken(true);
        user.addBook(book);
        save(user);
        bookService.save(book);
    }

    @Override
    public void returnBook(Long bookId, Long userId) {
        Book book = bookService.getById(bookId);
        User user = getById(userId);
        if(book.isTaken()){
            if(user.getBooks().contains(book)) {
                book.setTaken(false);
                user.removeBook(book);
                save(user);
                bookService.save(book);
            }else throw new UserDoesNotHaveBook("This user doesn't have book");
        }
        else throw new BookAlreadyReturnedException("Book is already returned in library");
    }
}
