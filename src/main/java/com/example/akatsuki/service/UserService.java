package com.example.akatsuki.service;

import com.example.akatsuki.model.User;
import com.example.akatsuki.repository.UserRepository;
import com.example.akatsuki.service.Jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;


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

    public String loginUser(User user) throws Exception {
        User existingUser = userRepository.findByUserId(user.getUserId());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        if (existingUser == null) {
//            throw new NoSuchElementException("User not found");
//        }
//        if (!existingUser.getUsername().equals(user.getUsername())) {
//            throw new IllegalArgumentException("Invalid username");
//        }
//        if (!(bCryptPasswordEncoder.matches(user.getPassword(), existingUser.getPassword()))){
//            throw new IllegalArgumentException("Invalid Password"); // Wrong Passwrord
//        }
        if (authenticate.isAuthenticated()){
            return jwtService.generateToken(user);
        }
        else{
            throw new IllegalArgumentException("Invalid Credentials");
        }
    }
}
