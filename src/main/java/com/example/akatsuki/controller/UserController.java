package com.example.akatsuki.controller;
import com.example.akatsuki.model.User;
import com.example.akatsuki.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/users")
public class UserController {


        @Autowired
        private UserRepository userRepository;

        @PostMapping
        public User createUser(@RequestBody User user) {
            return userRepository.save(user);  // insert into DB
        }

        @GetMapping
        public List<User> getAllUsers() {
            return userRepository.findAll();   // select * from users
        }
}
