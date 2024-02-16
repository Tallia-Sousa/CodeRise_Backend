package com.example.code.controllers;

import com.example.code.model.passwordUser.ResetPassword;
import com.example.code.model.passwordUser.passwordDto;
import com.example.code.repositories.UserRepository;
import com.example.code.respostas.Respostas;
import com.example.code.services.passwordService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/password-reset")
public class ControllerResetPassword {

    @Autowired
    private passwordService passwordService;

    private UserRepository userRepository;

    @PostMapping("/esquecerSenha")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestParam("email") String email)
            throws Exception {

      Respostas resposta = passwordService.enviarEmail(email);

      if(resposta.getStatus() == 400){
          return  ResponseEntity.status(400).build();
      }
      else if(resposta.getStatus() == 422){

          return  ResponseEntity.status(422).build();

      }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Map<String, String>> atualizarSenha(@RequestBody passwordDto pass){
        try{
            //chamar metodo de atualizar senha

            passwordService.atualizarSenha(pass.getToken(), pass.getEmail(), pass.getSenha());

        return  ResponseEntity.status(200).build();

        } catch (MessagingException e) {

            Map<String, String> response = new HashMap<>();

            response.put("message",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }}
