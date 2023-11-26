package com.example.code.services;


import com.example.code.model.user.*;
//import com.example.code.repositories.RepositorySenha;

import com.example.code.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {


    private UserRepository repository;




    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;

    }

    public User cadastrarUsers(CadastroDTO data) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());

        User newUser = new User(data.nome(), data.email(), encryptedPassword, UserRole.USER);

        return this.repository.save(newUser);


    }

    public List<User> listarUsuarios() {

        List<User> list = repository.findAll();
        return list;
    }

    public Boolean deletarUsuarios(String id) {
        repository.deleteById(id);
        return true;
    }

}





