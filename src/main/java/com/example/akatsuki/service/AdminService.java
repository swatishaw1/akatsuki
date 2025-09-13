package com.example.akatsuki.service;

import com.example.akatsuki.model.Admin;
import com.example.akatsuki.model.User;
import com.example.akatsuki.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;

    public Admin registerAdmin(Admin admin) {
        if (admin!=null) {
            return adminRepository.save(admin);
        }else {
            return null;
        }
    }

    public Admin loginAdmin(Admin admin) {
        Admin existingAdmin = adminRepository.findByAdminId(admin.getAdminId());
        if (existingAdmin != null) {
            if (existingAdmin.getPassword().equals(admin.getPassword())) {
                return existingAdmin;
            } else {
                return null; // Incorrect password
            }
        } else {
            return null; // Admin not found
        }
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}
