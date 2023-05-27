package com.google.mytravelapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.google.mytravelapp.R;
import com.google.mytravelapp.api.trip.TripService;
import com.google.mytravelapp.api.user.RetrofitInstance;
import com.google.mytravelapp.entities.TripDB;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTripActivity extends AppCompatActivity {
    private EditText destinationEdit;
    private TextView priceEurEdit, imagePickEdit, tripNameEdit;
    private Slider sliderEdit;
    private RatingBar ratingEdit;
    private ImageView bookmarkEdit;
    private Button saveButtonEdit;
    private Uri selectedImageUri;
    private Float rateValue;
    static final int SELECT_IMAGE_CODE = 1;
    private boolean isMarked;
    private boolean updatedFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        setupViews();
        Intent intent = getIntent();

        String tripName = intent.getStringExtra("tripName");
        Log.e("lol", tripName);
        getIsFav(tripName);

        String destination = intent.getStringExtra("destination");
        Float price = Float.parseFloat(intent.getStringExtra("price"));
        Float rating = Float.parseFloat(intent.getStringExtra("rating"));
        String linkImage = intent.getStringExtra("imageLink");

        sliderEdit.setValue(price);
        priceEurEdit.setText("Price in EUR: " + price);
        ratingEdit.setRating(rating);

        allTheSetters(isMarked, tripName, destination, price, rating);
        setSaveButtonEdit(tripName, linkImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                selectedImageUri = uri;
            }
        }
    }

    private void updateTrip(Integer id, TripDB tripDB) {
        TripService tripService = RetrofitInstance.getRetrofitInstance().create(TripService.class);
        Call<TripDB> call = tripService.updateTrip(id, tripDB);
        call.enqueue(new Callback<TripDB>() {
            @Override
            public void onResponse(Call<TripDB> call, Response<TripDB> response) {
                if (response.isSuccessful()) {
                    TripDB tripDB = response.body();
                } else {
                    Toast.makeText(getApplicationContext(), "Error updating trip: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TripDB> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "Network error! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "An unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                }
                Log.e("EditTripActivity", "Find Trip API call failed: ", t);
            }
        });
    }

    private void getIsFav(String name) {
        TripService tripService = RetrofitInstance.getRetrofitInstance().create(TripService.class);
        Call<TripDB> call = tripService.getTripsByName(name);
        call.enqueue(new Callback<TripDB>() {
            @Override
            public void onResponse(Call<TripDB> call, Response<TripDB> response) {
                if (response.isSuccessful()) {
                    TripDB tripDB = response.body();
                    Log.e("dadadad",String.valueOf(tripDB.getIsFavourite()));
                    isMarked = tripDB.getIsFavourite();
                    bookmarkEdit.setImageResource(isMarked ? R.drawable.ic_gold_baseline_bookmark_24 : R.drawable.ic_baseline_bookmark_24);
                }
            }

            @Override
            public void onFailure(Call<TripDB> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "Network error! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "An unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                }
                Log.e("EditTripActivity", "Find Trip API call failed: ", t);
            }
        });
    }

    private void getTripByName(String name, String destination, Float price, Float rating, String photoUri) {
        TripService tripService = RetrofitInstance.getRetrofitInstance().create(TripService.class);
        Call<TripDB> call = tripService.getTripsByName(name);

        call.enqueue(new Callback<TripDB>() {
            @Override
            public void onResponse(Call<TripDB> call, Response<TripDB> response) {
                if (response.isSuccessful()) {
                    TripDB tripDB = response.body();

                    tripDB.setDestination(destination);
                    tripDB.setPrice(price);
                    tripDB.setRating(rating);
                    tripDB.setPhotoUri(photoUri);
                    tripDB.setIsFavourite(isMarked);

                    updateTrip(tripDB.getId(), tripDB);
                } else {
                    Toast.makeText(getApplicationContext(), "Error fetching trip: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TripDB> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "Network error! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "An unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                }
                Log.e("EditTripActivity", "Find Trip API call failed: ", t);
            }
        });
    }

    private void setSaveButtonEdit(String name, String initialUri) {
        saveButtonEdit.setOnClickListener(view -> {
            String updatedDestination = destinationEdit.getText().toString();
            Float updatedPrice = sliderEdit.getValue();
            Float updatedRating = ratingEdit.getRating();

            if (selectedImageUri == null) {
                selectedImageUri = Uri.parse(initialUri);
            }
            getTripByName(name, updatedDestination, updatedPrice, updatedRating, String.valueOf(selectedImageUri));
            Intent intent = new Intent(EditTripActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void setupViews() {
        tripNameEdit = findViewById(R.id.tripNameEdit);
        destinationEdit = findViewById(R.id.destinationEdit);
        sliderEdit = findViewById(R.id.sliderEdit);
        ratingEdit = findViewById(R.id.ratingEdit);
        bookmarkEdit = findViewById(R.id.bookmarkEdit);
        priceEurEdit = findViewById(R.id.priceEurEdit);
        imagePickEdit = findViewById(R.id.imagePickEdit);
        saveButtonEdit = findViewById(R.id.saveButtonEdit);
    }

    private void updateViews(String tripName, String destination, Float price, Float rating, Boolean isBookmarked) {
        tripNameEdit.setText(tripName);
        destinationEdit.setText(destination);
        sliderEdit.setValue(price);
        ratingEdit.setRating(rating);
    }

    private void updateImage() {
        bookmarkEdit.setOnClickListener(view -> {
            if (isMarked) {
                bookmarkEdit.setImageResource(R.drawable.ic_baseline_bookmark_24);
                isMarked = false;
                updatedFav = false;
            } else {
                bookmarkEdit.setImageResource(R.drawable.ic_gold_baseline_bookmark_24);
                isMarked = true;
                updatedFav = true;
            }
        });
    }

    private void setSlider() {
        sliderEdit.addOnChangeListener((slider, value, fromUser) -> {
            int index = String.valueOf(value).indexOf(".");
            String text = "Price in EUR: ";
            priceEurEdit.setText(text + String.valueOf(value).substring(0, index));
        });
    }

    private void setRating() {
        ratingEdit.setOnRatingBarChangeListener((ratingBar, v, b) -> rateValue = ratingBar.getRating());
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, SELECT_IMAGE_CODE);
    }

    private void setImagePickEdit() {
        imagePickEdit.setOnClickListener(view -> {
            selectImage();
        });
    }

    private void allTheSetters(Boolean bookMarkItem, String tripName, String destination, Float price, Float rating) {
        updateViews(tripName, destination, price, rating, updatedFav);
        updateImage();
        setSlider();
        setRating();
        setImagePickEdit();
    }
}