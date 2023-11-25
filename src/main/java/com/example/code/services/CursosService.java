package com.example.code.services;

import com.example.code.model.cursos.Cursos;
import com.example.code.model.cursos.CursosDto;
import com.example.code.repositories.CursosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursosService {

    private CursosRepository repositoryCursos;

    @Autowired
    public CursosService(CursosRepository cursosRepository) {
        this.repositoryCursos = cursosRepository;
    }


    public List<Cursos> buscartCursosPorArea(String area) {
        List<Cursos> list = repositoryCursos.findByArea(area);
        return list;
    }


    public Cursos cadastrarCursos(CursosDto cursosDto) {
        Cursos curso = new Cursos(cursosDto.getTitulo(), cursosDto.getArea(), cursosDto.getDescricao(), cursosDto.getPlaylist());
        return repositoryCursos.save(curso);

    }


}