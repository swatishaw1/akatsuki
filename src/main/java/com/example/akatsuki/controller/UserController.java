package com.example.akatsuki.controller;
import com.example.akatsuki.model.User;
import com.example.akatsuki.repository.UserRepository;
import com.example.akatsuki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        return userService.registerUser(user);  // insert into DB
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user){
        return userService.loginUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();   // select * from users
    }
}
