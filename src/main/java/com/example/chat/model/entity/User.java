package com.example.chat.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "username")
    String username;

    @Column(name = "email")
    String email;

    @Column(name = "password_hash")
    String passwordHash;

    @Column(name = "is_banned")
    private Boolean isBanned;

    @OneToMany
    @JoinColumn(name = "friends_my_id")
    private Set<Friend> fiendsId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roles_id")
    private Role role;


    public User(String username, String email, String passwordHash, Boolean isBanned, Role role) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isBanned = isBanned;
        this.role = role;
    }
}