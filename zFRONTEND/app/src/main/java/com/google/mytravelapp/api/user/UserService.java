package com.google.mytravelapp.api.user;

import com.google.mytravelapp.database.User;
import com.google.mytravelapp.entities.UserDB;
import com.google.mytravelapp.login.LoginRequest;
import com.google.mytravelapp.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserService {
    @POST("api/users/register")
    Call<UserDB> registerUser(@Body UserDB user);

    @POST("api/users/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @PUT("api/users/reset-password")
    Call<Void> resetPassword(
            @Query("email") String email,
            @Query("oldPassword") String oldPassword,
            @Query("newPassword") String newPassword,
            @Query("confirmNewPassword") String confirmNewPassword
    );

    @GET("api/users/find-by-email")
    Call<User> getUserByEmail(@Query("email") String email);
}
