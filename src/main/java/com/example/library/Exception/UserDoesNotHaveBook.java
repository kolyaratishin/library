package com.example.library.Exception;

public class UserDoesNotHaveBook extends RuntimeException{
    public UserDoesNotHaveBook(String message) {
        super(message);
    }
}
