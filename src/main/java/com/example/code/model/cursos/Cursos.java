package com.example.code.model.cursos;


import com.example.code.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "Cursos")
@Table(name = "Cursos")
public class Cursos {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id")
    private String id;
    @Column(name = "Area",columnDefinition ="Text", nullable = false)
    private String area;
    @Column(name = "Descricao",columnDefinition ="Text", nullable = false, unique = true)
    private String descricao;
    @Column(name = "Playlist",  length = 200, columnDefinition ="VARCHAR(200)", nullable = false, unique = true)
    private String playlist;




    public Cursos(String area, String descricao, String playlist) {

        this.area = area;
        this.descricao = descricao;
        this.playlist = playlist;

    }
}

