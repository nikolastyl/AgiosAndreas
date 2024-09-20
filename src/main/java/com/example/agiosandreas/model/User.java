package com.example.agiosandreas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;


@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Παρακαλώ εισάγεται το όνομα")
    @Size(min=5,max=50,message = "Το όνομα πρέπει να είναι μεταξύ 5 έως 50 χαρακτήρες")
    @Column(name = "username")
    @Pattern(regexp = "^\\w{5,50}$",message = "Το όνομα θα πρέπει να περιέχει λατινικούς χαρακτήρες και αριθμούς μόνο ")
    private String username;

    @NotBlank(message = "Παρακαλώ εισάγεται το κωδικό")
    @Size(min=8,max=120,message = "Ο κωδικός θα πρέπει να είναι μεταξύ 8 έως 48 χαρακτήρες")
    @Column(name="password")
    private String password;

    public User() {}

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password){this.username = username;
        this.password=password;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
