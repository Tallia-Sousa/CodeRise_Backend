package com.example.code.repositories;

import com.example.code.model.user.RecuperacaoSenha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorySenha extends JpaRepository<RecuperacaoSenha, String> {
}
