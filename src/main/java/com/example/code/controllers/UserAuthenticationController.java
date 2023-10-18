package com.example.code.controllers;

import com.example.code.model.user.*;
//import com.example.auth.security.TokenService;
import com.example.code.model.user.*;
import com.example.code.repositories.UserRepository;
import com.example.code.security.TokenService;
import com.example.code.services.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//@CrossOrigin( allowedHeaders ="https://127.0.0.1:5500" )


@RestController
@RequestMapping("/users")
public class UserAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private TokenService tokenService;


    @GetMapping("/list")
    public ResponseEntity<List<User>> listaUsuarios() {
        return ResponseEntity.status(200).body(authorizationService.listarUsuarios());
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticarDTO data) {

    try {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);


        var token = tokenService.generateToken((User) auth.getPrincipal());//vai pegar usuario principal

        return ResponseEntity.ok(new LoginResponseDTO(token));// retorna um token pro usuar
    }
    catch (AuthenticationException e){

        return ResponseEntity.status(401).build();
    }}
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid CadastroDTO data){
        if(this.repository.findByEmail(data.email()) != null){
            return ResponseEntity.status(422).build();}

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        User newUser = new User(data.nome(), data.email(), encryptedPassword, UserRole.USER);

        this.repository.save(newUser);

        return ResponseEntity.status(201).build();
    }



    @PutMapping("/atualizar")
    public ResponseEntity<User>atualizarUsuario(@RequestBody CadastroDTO usuario){
        return ResponseEntity.status(201).body(authorizationService.atualizarUsuarios(usuario));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable String id){
        authorizationService.deletarUsuarios(id);
        return ResponseEntity.status(204).build();

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        //// Cria um mapa para armazenar mensagens de erro de validação.
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
