package com.example.code.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.code.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
//classe de geração de tokens
@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    //metodo de geração de tokens
    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("users")//criador do token
                    .withSubject(user.getEmail())// usuario que ira receber o token e salvar o user no token
                    .withExpiresAt(genExpirationDate())//tempo de expiração
                    .sign(algorithm);//assinatura do token
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);//excecao do jwt quando houver 1 parametro nao informadi
        }
    }
//validar se o token é valido
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("users")
                    .build()
                    .verify(token)
                    .getSubject();//pega  o user e everiffica se o token passado é valido e foi stm que gerou
        } catch (JWTVerificationException exception){//
            return "";
        }
    }
    //retorna um instante do tempo: para que o token expire em 2 horas
    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
