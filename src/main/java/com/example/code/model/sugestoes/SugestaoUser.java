package com.example.code.model.sugestoes;

import com.example.code.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sugestoesUser")
public class SugestaoUser {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "Id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id") //  muitas sugestao pode estar associadas a uma única instância da entidade User
    private User user;
    @Column(name = "autorPlaylist")
    private String autorPlaylist;
    @Column(name = "linkPlaylist",  length = 200, columnDefinition ="VARCHAR(255)", nullable = false, unique = true)
    private String linkPlaylist;
    @Column(name = "areaPlaylist")
    private String areaPlaylist;

    public SugestaoUser(User user, String autorPlaylist, String areaPlaylist, String linkPlaylist) {

        this.autorPlaylist = autorPlaylist;
        this.areaPlaylist = areaPlaylist;
        this.linkPlaylist = linkPlaylist;
        this.user = user;
    }
}
