package com.example.akatsuki.controller;

import com.example.akatsuki.model.Admin;
import com.example.akatsuki.repository.AdminRepository;
import com.example.akatsuki.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/admins")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public Admin createUser(@RequestBody Admin admin) {
        return adminService.registerAdmin(admin);  // insert into DB
    }

    @PostMapping("/login")
    public Admin loginUsers(@RequestBody Admin admin) throws Exception {
        return adminService.loginAdmin(admin);   // select * from users
    }

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }
}
