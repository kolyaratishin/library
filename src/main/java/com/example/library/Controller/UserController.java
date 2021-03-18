package com.example.library.Controller;
import com.example.library.Model.User;
import com.example.library.Service.UserService;
import com.example.library.Utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/get/{id}")
    public User getUser(@PathVariable("id") Long id){
        return userService.getById(id);
    }

    @PostMapping(value = "/save")
    public void saveUser(@RequestBody User user){
        userService.save(user);
    }

    @PostMapping(value = "/update/{id}")
    public void updateUser(@RequestBody User user,@PathVariable("id") Long id){
        User sourceUser = userService.getById(id);
        ObjectMapper.mapUser(sourceUser,user);
        userService.save(user);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
    }

    @GetMapping(value = "/list")
    public List<User> getUserList(){
        return userService.getAll();
    }

    @PostMapping(value = "/take/book")
    public void takeBook(@RequestParam("bookId") Long bookId,@RequestParam("userId") Long userId){
        userService.takeBook(bookId,userId);
    }

    @PostMapping(value = "/return/book")
    public void returnBook(@RequestParam("bookId") Long bookId,@RequestParam("userId") Long userId){
        userService.returnBook(bookId,userId);
    }

}
