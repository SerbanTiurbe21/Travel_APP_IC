package com.google.mytravelapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.google.mytravelapp.R;

public class EditTripActivity extends AppCompatActivity {

    private EditText destinationEdit;
    private TextView priceEurEdit, imagePickEdit, tripNameEdit;
    private Slider sliderEdit;
    private RatingBar ratingEdit;
    private ImageView bookmarkEdit;
    private Button saveButtonEdit;
    private Float rateValue;
    private Uri selectedImageUri;
    static final int SELECT_IMAGE_CODE = 1;
    private boolean isMarked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        setupViews();
        Intent intent = getIntent();
        Boolean bookMarkItem = Boolean.valueOf(intent.getStringExtra("bookMarkItem"));
        String tripName = intent.getStringExtra("tripName");
        String destination = intent.getStringExtra("destination");
        Float price = Float.parseFloat(intent.getStringExtra("price"));
        Float rating = Float.parseFloat(intent.getStringExtra("rating"));
        String email = intent.getStringExtra("email");
        String linkImage = intent.getStringExtra("imageLink");
        allTheSetters(bookMarkItem, tripName, destination, price, rating, email,linkImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Uri uri = data.getData();
            selectedImageUri = uri;
        }
    }

    private void setSaveButtonEdit(String mail, String linkImage) {
        saveButtonEdit.setOnClickListener(view -> {
            String updatedTripName = tripNameEdit.getText().toString();
            String updatedDestination = destinationEdit.getText().toString();
            String updatedPrice = priceEurEdit.getText().toString();
            String updatedRating = String.valueOf(rateValue);
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
        bookmarkEdit.setImageResource(isBookmarked ? R.drawable.ic_baseline_bookmark__gold_24 : R.drawable.ic_baseline_bookmark_24);
    }

    private void updateImage() {
        bookmarkEdit.setOnClickListener(view -> {
            bookmarkEdit.setImageResource(R.drawable.ic_baseline_bookmark__gold_24);
            isMarked = true;
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
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_IMAGE_CODE);
    }

    private void setImagePickEdit() {
        imagePickEdit.setOnClickListener(view -> {
            selectImage();
        });
    }

    private String getValue(String str) {
        String[] parts = str.split(":");
        return parts[parts.length - 1].trim();
    }

    private void allTheSetters(Boolean bookMarkItem, String tripName, String destination, Float price, Float rating, String email, String linkImage) {
        updateViews(tripName, destination, price, rating, bookMarkItem);
        updateImage();
        setSlider();
        setRating();
        setImagePickEdit();
        setSaveButtonEdit(email,linkImage);
    }
}