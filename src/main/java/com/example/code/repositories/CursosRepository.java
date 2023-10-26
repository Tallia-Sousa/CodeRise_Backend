package com.example.code.repositories;

import com.example.code.model.cursos.Cursos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursosRepository extends JpaRepository<Cursos, String> {


    List<Cursos> findByArea(String area);
}
