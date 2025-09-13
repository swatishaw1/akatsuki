package com.example.akatsuki.service;

import com.example.akatsuki.model.User;
import com.example.akatsuki.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User registerUser(User user) {
        if (user!=null) {
            return userRepository.save(user);
        }else {
            return null;
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User loginUser(User user) {
        User existingUser = userRepository.findByUserId(user.getUserId());
        if (existingUser != null) {
            if (existingUser.getPassword().equals(user.getPassword())) {
                return existingUser;
            } else {
                return null; // Incorrect password
            }
        } else {
            return null; // User not found
        }
    }
}
