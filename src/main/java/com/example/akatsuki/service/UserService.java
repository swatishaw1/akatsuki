package com.example.akatsuki.service;

import com.example.akatsuki.model.User;
import com.example.akatsuki.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public User registerUser(User user) {
        if (user!=null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }else {
            return null;
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User loginUser(User user) throws Exception {
        User existingUser = userRepository.findByUserId(user.getUserId());
        System.out.println(existingUser.getPassword());
        System.out.println(user.getPassword());
        if (existingUser == null) {
            throw new NoSuchElementException("User not found");
        }
        if (!existingUser.getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("Invalid username");
        }
        if (!(bCryptPasswordEncoder.matches(user.getPassword(), existingUser.getPassword()))){
            throw new IllegalArgumentException("Invalid Password"); // Wrong Passwrord
        }
        return existingUser;
    }
}
