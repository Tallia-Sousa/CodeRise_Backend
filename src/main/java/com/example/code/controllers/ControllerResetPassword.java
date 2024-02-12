package com.example.code.controllers;

import com.example.code.model.passwordUser.ResetPassword;
import com.example.code.model.passwordUser.passwordDto;
import com.example.code.services.passwordService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/password-reset")
public class ControllerResetPassword {

    @Autowired
    private passwordService passwordService;

    @PostMapping("/esquecerSenha")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestParam("email") String email)
            throws Exception {

        passwordService.enviarEmail(email);

        Map<String, String> responseBody = new HashMap<>();

        responseBody.put("message", "Solicitação de alteração de senha enviada com sucesso.");

        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Map<String, String>> atualizarSenha(@RequestBody passwordDto pass){
        try{
            //chamar metodo de atualizar senha

            passwordService.atualizarSenha(pass.getToken(), pass.getEmail(), pass.getSenha());

        Map<String, String> response = new HashMap<>();

        response.put("Message", "Senha alterada com sucesso");

        return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (MessagingException e) {

            Map<String, String> response = new HashMap<>();

            response.put("Error", e.getMessage());

            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }}
