package com.example.code.controllers;

import com.example.code.model.sugestoes.SugestaoDto;
import com.example.code.model.sugestoes.SugestaoUser;
import com.example.code.model.user.*;
//import com.example.code.repositories.RepositorySenha;
import com.example.code.repositories.RepositorySugestoes;
import com.example.code.repositories.UserRepository;
import com.example.code.respostas.Respostas;
import com.example.code.services.AuthorizationService;
import com.example.code.services.TokenService;
import com.example.code.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/users")

public class UsersController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserService UserService;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthorizationService authorizationService;


    @PostMapping("/cadastro")
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroDTO data) {
        try {
            if (this.repository.findByEmail(data.email()) != null) {
                return ResponseEntity.status(422).build();
            }

            UserService.cadastrarUsers(data);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro durante o cadastro");
        }
    }



    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticarDTO data, HttpServletResponse response) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
            var auth = this.authenticationManager.authenticate(usernamePassword);//verifica se as credenciais
            var token = tokenService.generateToken((User) auth.getPrincipal());


            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build();
        }
    }



    @PostMapping("/verificarToken")
    public ResponseEntity validarToken(HttpServletRequest request) {
        try {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim(); // Remove o prefixo 'Bearer ' e espaços em branco do token
            String resultadoValidacao = tokenService.validateToken(token);

            if (!resultadoValidacao.isEmpty()) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(403).build();
            }
        } else {
            return ResponseEntity.status(400).build();
        }}
        catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
    @PostMapping("/sugestoes")
    public ResponseEntity sugestaoUser(@RequestBody @Valid SugestaoDto sugestaoDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                User user = (User) authentication.getPrincipal();
                String idDoUsuarioAutenticado = user.getId();
                Respostas respostas = UserService.SugestaoUser(idDoUsuarioAutenticado, sugestaoDto);

                if (respostas.getStatus() == 200) {
                    return ResponseEntity.status(204).build();
                } else if (respostas.getStatus() == 422) {
                    return ResponseEntity.status(422).build();
                }
            }
            return ResponseEntity.status(401).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/perfil")
    public ResponseEntity<User> getPerfil() {

        // Recupera o usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.status(200).body(user);
        } else {
            return ResponseEntity.status(401).build(); // Não autenticado
        }

    }



    ///e esse aqui o oficial
    @GetMapping("/list")
    public ResponseEntity<List<User>> listaUsuarios() {
        return ResponseEntity.status(200).body(UserService.listarUsuarios());
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable String id){
        UserService.deletarUsuarios(id);
        return ResponseEntity.status(204).build();

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
