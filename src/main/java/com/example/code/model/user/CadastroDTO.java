package com.example.code.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroDTO(
        @NotBlank(message = "O nome é obrigatorio")
        @Size(min = 3, message = "o nome tem que ter pelo menos 3 caracteres")
        String nome,
        @Email(message = "insira um email válido")
        @NotBlank(message = "O email é obrigatorio")
        String email,
        @NotBlank(message = "A senha é obrigatoria")
        String senha,
        UserRole role) {
}
