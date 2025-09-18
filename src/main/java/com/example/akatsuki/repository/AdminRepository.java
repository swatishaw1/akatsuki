package com.example.akatsuki.repository;

import com.example.akatsuki.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByAdminId(Integer adminId);
    Admin findAdminByUsername(String username);
}
