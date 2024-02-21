package com.example.code.repositories;

import com.example.code.model.passwordUser.ResetPassword;
import com.example.code.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface passwordRepository extends JpaRepository<ResetPassword, Long> {


    ResetPassword findByToken(String token);

//    ResetPassword findByDeleteToken(String token);


    ResetPassword findByUser(User user);
}
