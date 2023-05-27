package com.google.mytravelapp.entities;

import com.google.mytravelapp.database.Trip;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDB {
    private Integer id;
    private String userId;
    private String email;
    private String name;
    private String password;
    private Set<Trip> trips;

    public UserDB(String name, String password, String email, Set<Trip> trips) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.trips = trips;
    }
}
