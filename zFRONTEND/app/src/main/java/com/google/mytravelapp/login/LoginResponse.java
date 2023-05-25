package com.google.mytravelapp.login;

import com.google.mytravelapp.database.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private String message;
    private String accessToken;
    private User user;
}
