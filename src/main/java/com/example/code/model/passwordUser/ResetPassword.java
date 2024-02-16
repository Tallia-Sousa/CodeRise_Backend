package com.example.code.model.passwordUser;


import com.example.code.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "password_reset_tokens")
public class ResetPassword {
    private static final long expiracao = 2 * 60 * 1000;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //chave primaria
    private Long id;
    private String token;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false , name = "userId")
    private User user;
    private LocalDateTime dateExpiry;


    public ResetPassword() {
        this.dateExpiry =  dataExpiracao(expiracao);
    }



    private LocalDateTime dataExpiracao(final long expiracaoEmMilissegundos) {
        LocalDateTime localDateAtual = LocalDateTime.now();

        LocalDateTime dateExpiracao = localDateAtual.plusSeconds(expiracaoEmMilissegundos / 1000);

        return dateExpiracao;


    }

}
