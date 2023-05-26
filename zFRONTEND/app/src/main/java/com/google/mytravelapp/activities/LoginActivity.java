package com.google.mytravelapp.activities;

import static com.google.mytravelapp.utilities.UtilitySharedPreferences.applyEmailPreference;
import static com.google.mytravelapp.utilities.UtilitySharedPreferences.applyIdPreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.mytravelapp.R;
import com.google.mytravelapp.api.user.RetrofitInstance;
import com.google.mytravelapp.api.user.UserService;
import com.google.mytravelapp.database.User;
import com.google.mytravelapp.login.LoginRequest;
import com.google.mytravelapp.login.LoginResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button registerButtonLogin, loginButtonLogin;
    EditText emailAddressLogin, passwordLogin;
    TextView forgotPassLogin;

    private void registerButtonAction() {
        registerButtonLogin.setOnClickListener(view -> {
            Intent changeActivity = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(changeActivity);
        });
    }

    private void getUserId(String userEmail){
        UserService userService = RetrofitInstance.getRetrofitInstance().create(UserService.class);
        Call<User> call = userService.getUserByEmail(userEmail);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    Integer userId = user.getId();
                    applyIdPreference(getApplicationContext(),userId);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(LoginActivity.this, "Network error! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "An unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                }
                Log.e("RegisterActivity", "Get UserId call failed: ", t);
            }
        });
    }

    private void loginUser(String userEmail, String password){
        final LoginRequest loginRequest = new LoginRequest(userEmail,password);
        UserService userService = RetrofitInstance.getRetrofitInstance().create(UserService.class);
        Call<LoginResponse> call = userService.loginUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    applyEmailPreference(getApplicationContext(),userEmail);

                    LoginResponse loginResponse = response.body();
                    String successMessage = loginResponse.getMessage();
                    Toast.makeText(LoginActivity.this, successMessage, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("email",userEmail);
                    intent.putExtra("password",password);
                    getUserId(userEmail);
                    startActivity(intent);
                } else {
                    try {
                        String errorMessage = response.errorBody().string();
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(LoginActivity.this, "Network error! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "An unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                }
                Log.e("RegisterActivity", "Registration API call failed: ", t);
            }
        });
    }

    private void setLoginButtonLogin(){
        loginButtonLogin.setOnClickListener(view -> {
            String userEmail = emailAddressLogin.getText().toString();
            String password = passwordLogin.getText().toString();
            if(userEmail.isEmpty() || password.isEmpty()){
                Toast.makeText(LoginActivity.this, "Please input all the fields!", Toast.LENGTH_SHORT).show();
                return;
            }
            loginUser(userEmail,password);
        });
    }

    private void forgotPassAction() {
        forgotPassLogin.setOnClickListener(view -> {
            Intent changeActivity = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(changeActivity);
        });
    }

    private void setupViews() {
        registerButtonLogin = findViewById(R.id.registerButtonLogin);
        loginButtonLogin = findViewById(R.id.loginButtonLogin);
        emailAddressLogin = findViewById(R.id.emailAddressLogin);
        passwordLogin = findViewById(R.id.passwordMain);
        forgotPassLogin = findViewById(R.id.forgotPassLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews();
        registerButtonAction();
        setLoginButtonLogin();
        forgotPassAction();
    }
}