package com.example.code.model.passwordUser;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@Setter
public class passwordDto {


    String token;
    String email;
    String senha;
}
