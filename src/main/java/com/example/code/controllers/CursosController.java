package com.example.code.controllers;


import com.example.code.model.cursos.Cursos;
import com.example.code.model.cursos.CursosDto;
import com.example.code.services.CursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursosController {
@Autowired
    private  CursosService cursosService;

    @Autowired
    public CursosController(CursosService cursosService) {
        this.cursosService = cursosService;
    }

    @PostMapping("/cadastrarCursos")
    public ResponseEntity<String> cadastrarCurso(@RequestBody CursosDto cursoDTO) {
        cursosService.cadastrarCursos(cursoDTO);
        return ResponseEntity.status(200).build();


    }
    @GetMapping("/{area}")
    public ResponseEntity<List<Cursos>> listaCursos(@PathVariable String area) {
        List<Cursos> cursos = cursosService.buscartCursosPorArea(area);
        if (cursos != null && !cursos.isEmpty()) {
            return ResponseEntity.status(200).body(cursos);
        } else {
            return ResponseEntity.status(204).build();
        }
        }




    }

















//    public ResponseEntity<List<CursosDto>> getCursosPorArea(@PathVariable String area) {
//
//        List<Cursos> cursos = cursosService.buscartCursosPorArea(area);
//        List<CursosDto> cursosDTO = new ArrayList<>();
//
//        if (cursos.isEmpty()) {
//            return ResponseEntity.status(204).build();
//        }
//
//        for (Cursos curso : cursos) {
//            CursosDto cursoDTO = new CursosDto();
//            cursoDTO.setArea(curso.getArea());
//            cursoDTO.setDescricao(curso.getDescricao());
//            cursoDTO.setComunidades(curso.getComunidades());
//            cursoDTO.setLinksPdf(curso.getLinksPdf());
//            cursoDTO.setVideos(curso.getVideos());
//            cursosDTO.add(cursoDTO);
//        }
//        return ResponseEntity.ok(cursosDTO);
//    }

