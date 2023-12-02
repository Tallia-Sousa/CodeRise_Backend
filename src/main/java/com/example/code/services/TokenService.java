package com.example.code.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.code.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

//classe de geração de tokens
@Service
public class TokenService {
    //variavel de ambiente
    @Value("${api.security.token.secret}")
    private String secret;

    //metodo de geração de tokens
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("CodeRise")//criador do token
                    .withSubject(user.getEmail())// usuario que ira receber o token e salvar o user no token
                    .withExpiresAt(genExpirationDate())//tempo de expiração
                    .sign(algorithm);//assinatura do token
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);//excecao do jwt quando houver 1 parametro nao informadi
        }
    }


    //validar se o token que  o usuario quer fazer uma nova req esta valido
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("CodeRise")//emissor
                    .build()
                    .verify(token)
                    .getSubject();//pega  o user e verifica se o token passado é valido e foi stm que gerou
        } catch (JWTVerificationException exception) {
            // se retornar isso pq o token expirou ou pq o token é invalido
            return "";
        }

    }

    public boolean isTokenExpired(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("CodeRise")
                    .build();
            verifier.verify(token);
            return false; // Token não está expirado
        } catch (JWTVerificationException exception) {
            // Token expirado
            return true;
        }
    }


    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(4).toInstant(ZoneOffset.of("-03:00"));
    }


}











