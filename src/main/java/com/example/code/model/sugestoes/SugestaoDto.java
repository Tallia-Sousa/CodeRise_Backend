package com.example.code.model.sugestoes;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SugestaoDto {
    @NotBlank(message = "O nome do autor é obrigatorio")
    @Size(min = 3, message = "o nome do autor tem que ter pelo menos 3 caracteres")
    private String autorPlaylist;
    @NotBlank(message = "A área  do curso é obrigatoria")
    @Size(min = 2, message = "A área nome tem que ter pelo menos 2 caracteres")
    private String areaPlaylist;
    @NotBlank(message = "O Link Da playlist é obrigatorio")
    @Size(min = 30, message = "A playlist tem que ter pelo menos 30 caracteres")
    private String linkPlaylist;


}
