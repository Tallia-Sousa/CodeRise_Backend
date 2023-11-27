package com.example.code.services;

import com.example.code.model.cursos.Cursos;
import com.example.code.model.cursos.CursosDto;
import com.example.code.repositories.CursosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public Cursos editarCursos( String id, CursosDto cursosDto){
        Optional<Cursos> curso = repositoryCursos.findById(id);

        if(curso.isPresent()){
            Cursos cursos = curso.get();
            cursos.setPlaylist(cursosDto.getPlaylist());
            return repositoryCursos.save(cursos);

        }
        else {
            return  null;
        }


    }

    public boolean removerCursos(String id){
         repositoryCursos.deleteById(id);
         return true;
    }

    public Cursos cadastrarCursos(CursosDto cursosDto) {
        Cursos curso = new Cursos(cursosDto.getTitulo(), cursosDto.getArea(), cursosDto.getDescricao(), cursosDto.getPlaylist());
        return repositoryCursos.save(curso);

    }


}