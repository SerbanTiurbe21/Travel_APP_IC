package com.google.mytravelapp.activities;

import static com.google.mytravelapp.utilities.UtilitySharedPreferences.applyIdPreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.mytravelapp.R;
import com.google.mytravelapp.api.city.Example;
import com.google.mytravelapp.api.city.Main;
import com.google.mytravelapp.api.city.WeatherApi;
import com.google.mytravelapp.api.trip.TripService;
import com.google.mytravelapp.api.user.RetrofitInstance;
import com.google.mytravelapp.entities.TripDB;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayDetailsActivity extends AppCompatActivity {

    private TextView tripNameDetails, destinationDetails, arrivingDateDetails, leavingDateDetails, weatherDetails, latitudeDetails, longitudeDetails;
    private String apiKey = "8f0bd97aaf59233622f5cba4c4c86397";
    private String arrivingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details);

        Intent intent = getIntent();
        String tripName = intent.getStringExtra("tripName");
        String destination = intent.getStringExtra("destination");

        setupViews();
        displayTheDetails(tripName, destination);
        getTripByName(tripName);
        getWeather(destination);
    }

    private void getTripByName(String tripName){
        TripService tripService = RetrofitInstance.getRetrofitInstance().create(TripService.class);
        Call<TripDB> call = tripService.getTripsByName(tripName);
        call.enqueue(new Callback<TripDB>() {
            @Override
            public void onResponse(Call<TripDB> call, Response<TripDB> response) {
                if(response.isSuccessful()){
                    TripDB tripDB = response.body();
                    arrivingDateDetails.setText(arrivingDateDetails.getText().toString() + ": " + tripDB.getStartDate());
                    leavingDateDetails.setText(leavingDateDetails.getText().toString() + ": " + tripDB.getEndDate());
                }
            }

            @Override
            public void onFailure(Call<TripDB> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(DisplayDetailsActivity.this, "Network error! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DisplayDetailsActivity.this, "An unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                }
                Log.e("DisplayDetailsActivity", "Get trips by name call failed: ", t);
            }
        });
    }

    private void displayTheDetails(String tripName, String destination) {
        tripNameDetails.setText(tripNameDetails.getText().toString() + ": " + tripName);
        destinationDetails.setText(destinationDetails.getText().toString() + ": " + destination);
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

    private void getWeather(String dest) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherApi api = retrofit.create(WeatherApi.class);
        Call<Example> exampleCall = api.getWeather(dest, apiKey);
        exampleCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.code() == 404) {
                    Toast.makeText(DisplayDetailsActivity.this, "Not a valid city!", Toast.LENGTH_SHORT).show();
                } else if (!response.isSuccessful()) {
                    Toast.makeText(DisplayDetailsActivity.this, Integer.toString(response.code()), Toast.LENGTH_SHORT).show();
                }
                Example myData = response.body();
                Main main = myData.getMain();
                Double temp = main.getTemp();
                Integer temperature = (int) (temp - 273.15);

                int feelsLike = (int) (main.getFeelsLike()-273.15);
                int humidity = main.getHumidity();
                weatherDetails.setText("Weather: " + String.valueOf(temperature) + "C");
                latitudeDetails.setText("Feels like: " + String.valueOf(feelsLike)+"C");
                longitudeDetails.setText("Humidity: " + String.valueOf(humidity)+"%");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(DisplayDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}