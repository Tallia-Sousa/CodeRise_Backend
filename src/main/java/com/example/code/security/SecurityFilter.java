package com.example.code.security;

import com.example.code.repositories.UserRepository;
import com.example.code.services.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Recupera o token da requisição
            String token = recoverToken(request);

            if (token != null) {
                // Valida o token e recupera o login do usuário
                String login = tokenService.validateToken(token);

                // Busca informações do usuário no repositório
                UserDetails user = userRepository.findByEmail(login);

                // Verifica se o usuário é encontrado
                if (user != null) {
                    // Cria a autenticação com base nas informações do usuário
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    // Define a autenticação no contexto de segurança
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // Usuário não encontrado, envia uma resposta de erro
                    handleUserNotFound(response);
                    return;
                }
            }
        } catch (ExpiredJwtException e) {
            // Token expirado, envia uma resposta de erro
            handleTokenExpiredOrInvalid(response);
            return;
        }

        // Continua o fluxo do filtro
        filterChain.doFilter(request, response);
    }


    private void handleTokenExpiredOrInvalid(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void handleUserNotFound(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }


    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}





