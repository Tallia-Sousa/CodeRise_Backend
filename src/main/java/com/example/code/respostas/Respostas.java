package com.example.code.respostas;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Respostas {


    private String message;
    private int status;

    public Respostas(String message, int status) {
        this.message = message;
        this.status = status;
    }

}