package com.example.code.repositories;

import com.example.code.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByEmail(String email);
    Optional<User> findById(String id);

    void deleteById(String id);





}
