package com.example.akatsuki.service;

import com.example.akatsuki.model.Admin;
import com.example.akatsuki.repository.AdminRepository;
import com.example.akatsuki.service.Jwt.AdminJWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AdminJWTService adminJWTService;

    public Admin registerAdmin(Admin admin) {
        if (admin!=null) {
            admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
            return adminRepository.save(admin);
        }else {
            return null;
        }
    }

    public String loginAdmin(Admin admin) {
        Admin existingAdmin = adminRepository.findByAdminId(admin.getAdminId());
//        if (existingAdmin == null) {
//            throw new NoSuchElementException("Admin not found");
//        }
//        if (!existingAdmin.getUsername().equals(admin.getUsername())) {
//            throw new IllegalArgumentException("Invalid username");
//        }
//        if (!(bCryptPasswordEncoder.matches(admin.getPassword(), existingAdmin.getPassword()))) {
//            throw new IllegalArgumentException("Invalid password");
//        }
//        return existingAdmin; // ✅ Successful login
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(admin.getUsername(),admin.getPassword()));
        if (authenticate.isAuthenticated()){
            return adminJWTService.generateToken(admin);
        }
        throw new IllegalStateException("Admin Not Found");
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}
