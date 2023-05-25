package com.google.mytravelapp.database;

import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {
    private Integer id;
    private String userId;
    private String email;
    private String name;
    private String password;
    private Set<Trip> trips;

    public User(String name, String email, String password, Set<Trip> trips) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.trips = trips;
    }
}