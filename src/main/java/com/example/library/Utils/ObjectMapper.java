package com.example.library.Utils;

import com.example.library.Model.Book;
import com.example.library.Model.User;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {
    public static void mapBook(Book sourceBook,Book targetBook){
        if(targetBook.getId()==null){
            targetBook.setId(sourceBook.getId());
        }
        if(targetBook.getName()==null){
            targetBook.setName(sourceBook.getName());
        }
        if(targetBook.getDescription()==null){
            targetBook.setDescription(sourceBook.getDescription());
        }
        if(targetBook.getUser()==null){
            targetBook.setUser(sourceBook.getUser());
        }
    }
    public static void mapUser(User sourceUser, User targetUser){
        if(targetUser.getId()==null){
            targetUser.setId(sourceUser.getId());
        }
        if(targetUser.getUsername()==null){
            targetUser.setUsername(sourceUser.getUsername());
        }
        if(targetUser.getBooks()==null){
            for (Book book: sourceUser.getBooks()) {
                targetUser.addBook(book);
            }
        }
    }

}
