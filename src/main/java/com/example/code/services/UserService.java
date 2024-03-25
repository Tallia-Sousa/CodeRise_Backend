package com.example.code.services;


import com.example.code.model.sugestoes.SugestaoDto;
import com.example.code.model.sugestoes.SugestaoUser;
import com.example.code.model.user.*;
//import com.example.code.repositories.RepositorySenha;

import com.example.code.repositories.RepositorySugestoes;
import com.example.code.repositories.UserRepository;
import com.example.code.respostas.Respostas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private UserRepository repository;


    private RepositorySugestoes repositorySugestoes;


  
    public UserService(UserRepository repository, RepositorySugestoes repositorySugestoes) {
        this.repository = repository;
        this.repositorySugestoes = repositorySugestoes;

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

    public Respostas SugestaoUser(String id, SugestaoDto sugestaoDto) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String linkPlaylist = sugestaoDto.getLinkPlaylist();
            SugestaoUser linkJaExiste = repositorySugestoes.findByLinkPlaylist(linkPlaylist);
            if (linkJaExiste == null)    {
                SugestaoUser sugestaoUser = new SugestaoUser(user, sugestaoDto.getAutorPlaylist(),
                        sugestaoDto.getAreaPlaylist(), sugestaoDto.getLinkPlaylist());
                repositorySugestoes.save(sugestaoUser);
                return new Respostas("sugestao enviada com sucesso", 200);
            }
            return new Respostas("Essa sugestao ja existe na nossa base de dados", 422);
        }

        return new Respostas("id nao encontrado", 401);
    }





}






