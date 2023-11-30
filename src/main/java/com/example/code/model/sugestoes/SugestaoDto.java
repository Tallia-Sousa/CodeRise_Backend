package com.example.code.model.sugestoes;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SugestaoDto {
    @NotBlank(message = "O nome do autor é obrigatorio")
    @Size(min = 3, message = "o nome tem que ter pelo menos 3 caracteres")
    private String autorPlaylist;
    @NotBlank(message = "A área  do curso é obrigatoria")
    private String areaPlaylist;
    @NotBlank(message = "O Link Da playlist é obrigatorio")
    private String linkPlaylist;


}
