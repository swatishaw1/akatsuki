package com.example.akatsuki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.akatsuki.model.User;
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(Integer userId);
    User findByUsername(String username);
}
