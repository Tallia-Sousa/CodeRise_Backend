package com.example.code.model.cursos;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor

@Entity(name = "Cursos")
@Table(name = "Cursos")
public class Cursos {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "area",columnDefinition ="Text", nullable = false)
    private String area;
    @Column(name = "descricao",columnDefinition ="Text", nullable = false)
    private String descricao;
    @Column(name = "videos",  length = 200, columnDefinition ="VARCHAR(200)", nullable = false)
    private String videos;
    @Column(name = "linksPdf", columnDefinition ="VARCHAR(200)", length = 200, nullable = false)
    private String linksPdf;
    @Column(name = "comunidades", columnDefinition ="VARCHAR(200)",  length = 200, nullable = false)
    private String comunidades;


    public Cursos() {

    }

    public Cursos(String area, String descricao, String videos, String linksPdf, String comunidades) {

        this.area = area;
        this.descricao = descricao;
        this.videos = videos;
        this.linksPdf = linksPdf;
        this.comunidades = comunidades;
    }
}

