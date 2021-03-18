package com.example.library.Service;

import com.example.library.Model.User;

import java.util.List;

public interface UserService {
    User getById(Long id);
    List<User> getAll();
    User save(User user);
    void delete(Long id);
    void takeBook(Long bookId,Long userId);
    void returnBook(Long bookId,Long userId);
}
