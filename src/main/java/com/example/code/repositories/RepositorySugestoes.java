package com.example.code.repositories;

import org.springframework.stereotype.Repository;


import com.example.code.model.sugestoes.SugestaoUser;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface RepositorySugestoes  extends JpaRepository<SugestaoUser, String> {
    SugestaoUser findByLinkPlaylist(String linkPlaylist);
}
