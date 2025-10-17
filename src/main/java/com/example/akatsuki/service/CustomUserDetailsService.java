package com.example.akatsuki.service;


import com.example.akatsuki.CustomUserDetails;
import com.example.akatsuki.model.User;
import com.example.akatsuki.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class    CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if (user == null){
            System.out.println("User not available");
            throw new IllegalArgumentException("User not Found");
        }
        return new CustomUserDetails(user);
    }
}
