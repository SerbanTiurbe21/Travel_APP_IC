package com.google.mytravelapp.activities;

import static com.google.mytravelapp.utilities.Utility.isValidEmail;
import static com.google.mytravelapp.utilities.Utility.isValidPassword;
import static com.google.mytravelapp.utilities.Utility.isValidUsername;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.mytravelapp.R;
import com.google.mytravelapp.api.RetrofitInstance;
import com.google.mytravelapp.api.UserService;
import com.google.mytravelapp.database.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    EditText emailAddressReg, usernameReg, passwordReg, repassReg;
    Button registerButtonReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupViews();
        handleRegisterButtonClick();
    }

    private boolean validateInputs(String email, String username, String password, String repass) {
        if (!email.isEmpty() && !username.isEmpty() && !password.isEmpty() && !repass.isEmpty()) {
            return true;
        }
        return false;
    }

    private void setupViews() {
        emailAddressReg = findViewById(R.id.emailAddressReg);
        usernameReg = findViewById(R.id.usernameReg);
        passwordReg = findViewById(R.id.passwordReg);
        registerButtonReg = findViewById(R.id.registerButtonReg);
        repassReg = findViewById(R.id.repassReg);
    }

    private void handleRegisterButtonClick() {
        registerButtonReg.setOnClickListener(new View.OnClickListener() {
            String email = emailAddressReg.getText().toString();
            String username = usernameReg.getText().toString();
            String password = passwordReg.getText().toString();
            String repass = repassReg.getText().toString();

            @Override
            public void onClick(View view) {
                registerUser(email, username, password, repass);
            }
        });
    }

    /*
    private void registerUser(String email, String username, String password, String repass) {
        if (!email.isEmpty() && !username.isEmpty() && !repass.isEmpty()) {
            if (password.equals(repass)) {
                if (isValidEmail(email)) {
                    if(isValidUsername(username)){
                        if(isValidPassword(password)){

                        }else{
                            Toast.makeText(RegisterActivity.this, "Invalid password!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Invalid username!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Passwords not matching!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Please input all the fields!", Toast.LENGTH_SHORT).show();
        }
    }*/

    private void registerUser(String email, String username, String password, String repass) {
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

        // Proceed with user registration, make the API call
        User user = new User(email, username, password, null);
        UserService userService = RetrofitInstance.getRetrofitInstance().create(UserService.class);
        Call<User> call = userService.registerUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Registration was successful, handle the result
                    User registeredUser = response.body();
                    // Show a success message or navigate to another activity
                    Toast.makeText(RegisterActivity.this, "User successfully registered!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    // The server returned an error, handle it here
                    // You might want to show an error message to the user
                    String errorMessage;
                    int statusCode = response.code();
                    if (statusCode == 400) {
                        errorMessage = "Invalid request data. Please make sure all fields are correct.";
                    } else if (statusCode == 409) {
                        errorMessage = "The email or username is already in use. Please try a different one.";
                    } else {
                        errorMessage = "Registration failed. Please try again later.";
                    }
                    Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

                    // Log the error for debugging purposes
                    Log.e("RegisterActivity", "Registration API call failed with status code: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Network error occurred, handle it here
                if (t instanceof IOException) {
                    // This is a network error (e.g. no internet connection, timeout)
                    Toast.makeText(RegisterActivity.this, "Network error! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    // This is an unexpected error (e.g. a conversion issue in Retrofit)
                    Toast.makeText(RegisterActivity.this, "An unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                }

                // You might want to log the error for debugging purposes
                Log.e("RegisterActivity", "Registration API call failed: ", t);
            }

        });
    }


}