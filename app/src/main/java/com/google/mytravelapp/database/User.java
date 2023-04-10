package com.google.mytravelapp.database;

import java.util.Set;

public class User {
    private Integer id;

    private String userId;

    private String email;

    private String name;

    private String password;

    private Set<Trip> trips;

    public User() {
    }

    public User(String name, String email, String password, Set<Trip> trips) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.trips = trips;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
