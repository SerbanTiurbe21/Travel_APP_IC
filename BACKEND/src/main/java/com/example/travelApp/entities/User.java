package com.example.travelApp.entities;

import com.example.travelApp.serializer.UserSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users_test")
@JsonSerialize(using = UserSerializer.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Trip> trips;

    public User(Integer id, String userId, String name, String password, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
