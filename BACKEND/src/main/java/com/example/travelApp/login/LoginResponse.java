package com.example.travelApp.login;

import com.example.travelApp.entities.User;

public class LoginResponse {
    private String message;
    private String accessToken;
    private User user;

    public LoginResponse() {
    }

    public LoginResponse(String message, String accessToken, User user) {
        this.message = message;
        this.accessToken = accessToken;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
