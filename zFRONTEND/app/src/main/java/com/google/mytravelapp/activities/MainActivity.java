package com.google.mytravelapp.activities;

import static com.google.mytravelapp.utilities.UtilitySharedPreferences.applyUsernamePreference;
import static com.google.mytravelapp.utilities.UtilitySharedPreferences.getSharedPrefEmail;
import static com.google.mytravelapp.utilities.UtilitySharedPreferences.getSharedPrefUsername;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mytravelapp.R;
import com.google.mytravelapp.api.user.RetrofitInstance;
import com.google.mytravelapp.api.user.UserService;
import com.google.mytravelapp.database.User;
import com.google.mytravelapp.databinding.ActivityMainBinding;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    TextView userNameHeader, emailHeader;
    View hView;
    private String username;

    private void getUserData(String email) {
        UserService userService = RetrofitInstance.getRetrofitInstance().create(UserService.class);
        Call<User> call = userService.getUserByEmail(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Log.e("MainActivity", "Request failed with status code: " + response.code());
                    return;
                }
                User user = response.body();
                username = user.getName();
                applyUsernamePreference(getApplicationContext(), username);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "Network error! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "An unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                }
                Log.e("MainActivity", "Find User API call failed: ", t);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String userName = getSharedPrefUsername(getApplicationContext());
        String email = getSharedPrefEmail(getApplicationContext());
        getUserData(email);

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        hView = navigationView.getHeaderView(0);
        userNameHeader = hView.findViewById(R.id.userNameHeader);
        emailHeader = hView.findViewById(R.id.emailHeader);

        userNameHeader.setText(userName);
        emailHeader.setText(email);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_aboutUs, R.id.nav_Contact, R.id.nav_Share)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}