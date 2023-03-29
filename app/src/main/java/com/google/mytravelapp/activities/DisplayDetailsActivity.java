package com.google.mytravelapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.mytravelapp.R;

public class DisplayDetailsActivity extends AppCompatActivity {
    private TextView tripNameDetails, destinationDetails, arrivingDateDetails, leavingDateDetails, weatherDetails, latitudeDetails, longitudeDetails;
    String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}";
    String apiKey = "8f0bd97aaf59233622f5cba4c4c86397";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details);
        Intent intent = getIntent();
        String tripName = intent.getStringExtra("tripName");
        String email = intent.getStringExtra("email");
        String destination = intent.getStringExtra("destination");
    }
    private void setupViews() {
        tripNameDetails = findViewById(R.id.tripNameDetails);
        destinationDetails = findViewById(R.id.destinationDetails);
        arrivingDateDetails = findViewById(R.id.arrivingDateDetails);
        leavingDateDetails = findViewById(R.id.leavingDateDetails);
        weatherDetails = findViewById(R.id.weatherDetails);
        latitudeDetails = findViewById(R.id.latitudeDetails);
        longitudeDetails = findViewById(R.id.longitudeDetails);
    }

}