package com.example.code.model.user;
//
//import com.example.code.model.favoritar.Favoritar;
import com.example.code.model.cursos.Cursos;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Table(name = "users")
@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id")
    private String id;
    @Column(name = "Nome", length = 50, nullable = false)
    private String nome;
    @Column(name = "Email", length = 50, nullable = false, unique = true)
    private String email;
    @Column(name = "Senha", nullable = false)
    private String senha;
    @Column(name = "Role")
    private UserRole roles;
//    @Column(name = "CodigoRecuperacaoSenha")
//    private String codigorecuperacaosenha;
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "DataEnvioCodigo")
//    private Date dataEnvioCodigo;

    public User(String nome, String email, String senha, UserRole role){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.roles = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.roles == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }



    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}