package com.example.library.Exception;

public class BookAlreadyTakenException extends RuntimeException {
    public BookAlreadyTakenException(String message) {
        super(message);
    }
}
