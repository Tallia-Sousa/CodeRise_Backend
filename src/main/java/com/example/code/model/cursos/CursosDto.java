package com.example.code.model.cursos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CursosDto {
        @NotBlank(message = "O titulo é obrigatorio")
        @Size(min = 3, message = "o titulo tem que ter pelo menos 3 caracteres")
        private String titulo;
        @NotBlank(message = "A área é obrigatoria")
        @Size(min = 2, message = "A área tem que ter pelo menos 2 caracteres")
        private String area;
        @NotBlank(message = "O link da playlist é obrigatorio")
        @Size(min = 30, message = "o link tem que ter pelo menos 30 caracteres")
        private String playlist;
        @NotBlank(message = "A descriçao é obrigatoria")
        @Size(min = 5, message = "A descriçao tem que ter pelo menos 5 caracteres")
        private String descricao;
        @NotBlank(message = "O nome do autor da playlist é obrigatorio")
        @Size(min = 3, message = "o nome do autor tem que ter pelo menos 3 caracteres")
        private String autorPlaylist;


}
