package com.example.code.controllers;


import com.example.code.model.cursos.Cursos;
import com.example.code.model.cursos.CursosDto;
import com.example.code.repositories.CursosRepository;
import com.example.code.services.CursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursosController {


    private CursosService cursosService;
    @Autowired
    private CursosRepository cursosRepository;

    @Autowired
    public CursosController(CursosService cursosService) {
        this.cursosService = cursosService;
    }

    @PostMapping("/cadastrarCursos")
    public ResponseEntity<Cursos> cadastrarCurso(@RequestBody CursosDto cursoDTO) {
        Cursos cursos = cursosRepository.findByPlaylist(cursoDTO.getPlaylist());
        if (cursos == null) {
            cursosService.cadastrarCursos(cursoDTO);
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(422).build();

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


    @DeleteMapping("/{id}")
    public ResponseEntity deleteCursos(@PathVariable String id){

        cursosService.removerCursos(id);

       return  ResponseEntity.status(204).build();
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

