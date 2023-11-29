package com.example.code.controllers;


import com.example.code.model.cursos.Cursos;
import com.example.code.model.cursos.CursosDto;
import com.example.code.repositories.CursosRepository;
import com.example.code.services.CursosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Cursos> cadastrarCurso(@RequestBody @Valid CursosDto cursoDTO) {
        Cursos cursos = cursosRepository.findByPlaylist(cursoDTO.getPlaylist());
        if (cursos == null) {
            cursosService.cadastrarCursos(cursoDTO);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(422).build();

    }

    @GetMapping("/{area}")
    public ResponseEntity<List<Cursos>> listaCursos(@PathVariable String area) {
        try {
            List<Cursos> cursos = cursosService.buscartCursosPorArea(area);
            if (cursos != null && !cursos.isEmpty()) {
                return ResponseEntity.status(200).body(cursos);
            } else {
                return ResponseEntity.status(404).build();
            }
        }
        catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteCursos(@PathVariable String id){

         cursosService.removerCursos(id);
         return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cursos> editarCurso( @PathVariable String id, @RequestBody @Valid CursosDto cursosDto) {
        try {
            Cursos curso= cursosService.editarCursos(id, cursosDto);
            return ResponseEntity.ok(curso);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        //// Criar um mapa para armazenar mensagens de erro de validação.
        Map<String, String> erros = new HashMap<>();
        // Obtém todos os erros de validação associados à exceção.
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            // Obtém o nome do campo que causou o erro.
            String fieldname =((FieldError)error).getField();
            // Obtém mensagem de  o erro.
            String errorMessage =error.getDefaultMessage();

            // envia para o mapa e faz comparações com
            erros.put(fieldname, errorMessage);
        });
//        retorna o erro
        return erros;
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

