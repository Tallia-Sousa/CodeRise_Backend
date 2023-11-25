package com.example.code.repositories;

import com.example.code.model.cursos.Cursos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CursosRepository extends JpaRepository<Cursos, String> {


    List<Cursos> findByArea(String area);


    Cursos findByid(String cursoId);



    Cursos findByPlaylist(String Playlist);



//    List<Cursos> findByAreaAndTitulo(String area, String titulo);


}