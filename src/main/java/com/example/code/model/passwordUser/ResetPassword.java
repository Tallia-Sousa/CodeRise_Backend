package com.example.code.model.passwordUser;


import com.example.code.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "password_reset_tokens")
public class ResetPassword {
    private static final int expiracao = 60 * 15; //15 minutos de expiraçao


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //chave primaria
    private Long id;
    private String token;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false , name = "userId")
    private User user;
    private Date dateExpiry;

    public ResetPassword(String token, User user) {
        this.token = token;
        this.user = user;
        this.dateExpiry = dataExpiracao(expiracao);
    }




    private Date dataExpiracao(final int expiracao){
        //intancia a classe
        Calendar calendario = Calendar.getInstance();
        //define o tempo atual como temp do calendario
        calendario.setTimeInMillis(new Date().getTime());
        //adiciona a quantidade de minutos especificada pela variável expiracao
        calendario.add(calendario.MINUTE, expiracao);
        //retorna a data de expiraçao em minutos
        return new Date(calendario.getTime().getTime());


    }
}
