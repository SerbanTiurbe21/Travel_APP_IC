package com.google.mytravelapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.mytravelapp.R;

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

    private void setLoginButtonLogin() {

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