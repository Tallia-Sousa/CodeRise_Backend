package com.example.code.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AutenticarDTO(
        @Email(message = "insira um email válido")
        @NotBlank(message = "O email é obrigatorio")
        String email,
        @NotBlank(message = "A senha é obrigatoria")
        String senha) {
}