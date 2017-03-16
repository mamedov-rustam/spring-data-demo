package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private String username;
    private Boolean isActive;

    public User(String email, String password, String username, Boolean isActive) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.isActive = isActive;
    }

    public User(String email, String password, String username) {
        this(email, password, username, true);
    }
}
