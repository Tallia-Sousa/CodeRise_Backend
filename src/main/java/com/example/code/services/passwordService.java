package com.example.code.services;

import com.example.code.model.passwordUser.ResetPassword;
import com.example.code.model.user.User;
import com.example.code.repositories.UserRepository;
import com.example.code.repositories.passwordRepository;
import com.example.code.respostas.Respostas;
import jakarta.mail.MessagingException;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class passwordService {
    @Autowired
    private UserRepository repositoryUser;
    @Autowired
    private UserService userService;
    @Autowired
    private passwordRepository repositoryPassword;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    //criar o token
    public void criarToken(User user, String token) {
        //cria o objeto
        ResetPassword reset = new ResetPassword();

        //associa o user
        reset.setUser(user);
        //passa o token
        reset.setToken(token);
        //salva o reset de senha no banco
        repositoryPassword.save(reset);
    }

    public ResetPassword buscarToken(User user) {
        return repositoryPassword.findByUser(user);
    }

    public Respostas enviarEmail(String email) throws Exception {
        User user = (User) repositoryUser.findByEmail(email);

        if (user == null || user.getEmail() == null) {
            return new Respostas(400);
        }

         //ver se o user ja  possui um token
        if (buscarToken(user) != null) {
            return new Respostas(422);
        }

        // Gera o token
        String token = UUID.randomUUID().toString();

        // Chama o método que cria e associa o token ao usuário
        criarToken(user, token);

        // Envia o token para o usuário
        emailService.enviarEmail(user.getEmail(), token);

        // Retorna código de resposta 200 indicando sucesso
        return new Respostas(200);
    }
    public Boolean dataExpirou(LocalDateTime dataExpiracao){

        LocalDateTime agora = LocalDateTime.now();

        // Verifica se a data de expiração já passou
        return agora.isAfter(dataExpiracao);
    }


    public void atualizarSenha(String token, String email, String senha ) throws MessagingException {
        // Busca o token de redefinição na base de dados
        ResetPassword tokenR = repositoryPassword.findByToken(token);

        // Verifica se o token é válidoccc

        if (tokenR == null ) {
            throw new MessagingException("Token inválido");
        }

        if (tokenR.getDateExpiry().isBefore(LocalDateTime.now())) {
            repositoryPassword.delete(tokenR);
            throw new MessagingException("Token expirado");

        }


        // Busca o usuário pelo email
        User user = (User) repositoryUser.findByEmail(email);

        // Verifica se o usuário existe
        if (user == null) {
            throw new MessagingException("Usuário não encontrado");
        }

        // Define a nova senha para o usuário e a codifica
        String password = passwordEncoder.encode(senha);
        user.setSenha(password);

        // Salva a atualização da senha
        repositoryUser.save(user);

        // Deleta o token utilizado
        repositoryPassword.delete(tokenR);
    }
}




