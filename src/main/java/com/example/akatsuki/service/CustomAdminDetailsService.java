package com.example.akatsuki.service;

import com.example.akatsuki.CustomAdminDetails;
import com.example.akatsuki.model.Admin;
import com.example.akatsuki.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin=adminRepository.findAdminByUsername(username);
        if(admin==null){
            System.out.println("Admin not available");
            throw new IllegalArgumentException("Admin Not Found");
        }
        return new CustomAdminDetails(admin);
    }
}
