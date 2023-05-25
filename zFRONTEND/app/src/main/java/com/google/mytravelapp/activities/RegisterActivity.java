package com.google.mytravelapp.activities;

import static com.google.mytravelapp.utilities.Utility.isValidEmail;
import static com.google.mytravelapp.utilities.Utility.isValidPassword;
import static com.google.mytravelapp.utilities.Utility.isValidUsername;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.mytravelapp.R;
import com.google.mytravelapp.api.user.RetrofitInstance;
import com.google.mytravelapp.api.user.UserService;
import com.google.mytravelapp.entities.UserDB;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText emailAddressReg, usernameReg, passwordReg, repassReg;
    Button registerButtonReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupViews();
        register();
    }

    private void register() {
        registerButtonReg.setOnClickListener(view -> {
            String email = emailAddressReg.getText().toString();
            String username = usernameReg.getText().toString();
            String password = passwordReg.getText().toString();
            String repass = repassReg.getText().toString();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || repass.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please input all the fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidEmail(email)) {
                Toast.makeText(RegisterActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidUsername(username)) {
                Toast.makeText(RegisterActivity.this, "Invalid username!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidPassword(password)) {
                Toast.makeText(RegisterActivity.this, "Invalid password!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(repass)) {
                Toast.makeText(RegisterActivity.this, "Passwords not matching!", Toast.LENGTH_SHORT).show();
                return;
            }

            final UserDB user = new UserDB(username, password, email, null);
            UserService userService = RetrofitInstance.getRetrofitInstance().create(UserService.class);
            Call<UserDB> call = userService.registerUser(user);

            call.enqueue(new Callback<UserDB>() {
                @Override
                public void onResponse(Call<UserDB> call, Response<UserDB> response) {
                    if (response.isSuccessful()) {
                        UserDB registeredUser = response.body();
                        Toast.makeText(RegisterActivity.this, "User successfully registered!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        String errorMessage;
                        int statusCode = response.code();
                        if (statusCode == 400) {
                            errorMessage = "The same email cannot be registered.";
                        } else if (statusCode == 409) {
                            errorMessage = "The email or username is already in use. Please try a different one.";
                        } else {
                            errorMessage = "Registration failed. Please try again later.";
                        }
                        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e("RegisterActivity", "Registration API call failed with status code: " + statusCode);
                    }
                }

                @Override
                public void onFailure(Call<UserDB> call, Throwable t) {
                    if (t instanceof IOException) {
                        Toast.makeText(RegisterActivity.this, "Network error! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "An unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("RegisterActivity", "Registration API call failed: ", t);
                }
            });
        });
    }

    private void setupViews() {
        emailAddressReg = findViewById(R.id.emailAddressReg);
        usernameReg = findViewById(R.id.usernameReg);
        passwordReg = findViewById(R.id.passwordReg);
        registerButtonReg = findViewById(R.id.registerButtonReg);
        repassReg = findViewById(R.id.repassReg);
    }
}