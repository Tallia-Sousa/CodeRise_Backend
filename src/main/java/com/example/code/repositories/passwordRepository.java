package com.example.code.repositories;

import com.example.code.model.passwordUser.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface passwordRepository extends JpaRepository<ResetPassword, Long> {


    ResetPassword findByToken(String token);
}
