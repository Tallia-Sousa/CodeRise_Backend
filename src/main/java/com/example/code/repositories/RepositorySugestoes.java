package com.example.code.repositories;

import com.example.code.model.sugestoes.SugestaoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorySugestoes  extends JpaRepository<SugestaoUser, String> {
    SugestaoUser findByLinkPlaylist(String linkPlaylist);
}
