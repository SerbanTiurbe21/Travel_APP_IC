package com.google.mytravelapp.api;

import com.google.mytravelapp.database.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("api/users")
    Call<User> registerUser(@Body User user);
}
