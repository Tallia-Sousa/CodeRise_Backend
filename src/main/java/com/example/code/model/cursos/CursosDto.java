package com.example.code.model.cursos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CursosDto {
        @NotBlank(message = "O titulo é obrigatorio")
        private String titulo;
        @NotBlank(message = "A área é obrigatoria")
        private String area;
        @NotBlank(message = "O link da playlist é obrigatorio")
        private String playlist;
        @NotBlank(message = "A descriçao é obrigatoria")
        private String descricao;
        @NotBlank(message = "O nome do autor da playlist é obrigatorio")
        private String autorPlaylist;


}
