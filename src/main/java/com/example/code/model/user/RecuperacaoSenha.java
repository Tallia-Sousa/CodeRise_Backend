package com.example.code.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RecuperacaoSenha")
public class RecuperacaoSenha {

    @GeneratedValue(strategy = GenerationType.UUID)//id unico
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "Codigo")
    private String codigorecuperar;
    @Column(name = "DataEnvioCodigo")
    private LocalDateTime dataenvioCod;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;




}
