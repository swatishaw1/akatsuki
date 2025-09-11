package com.example.akatsuki.controller;

import com.example.akatsuki.model.Admin;
import com.example.akatsuki.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/admins")
public class AdminController {


    @Autowired
    private AdminRepository adminRepository;

    @PostMapping
    public Admin createUser(@RequestBody Admin admin) {
        return adminRepository.save(admin);  // insert into DB
    }

    @GetMapping
    public List<Admin> getAllUsers() {
        return adminRepository.findAll();   // select * from users
    }
}
