package com.google.mytravelapp.activities;

import static com.google.mytravelapp.utilities.UtilitySharedPreferences.getSharedPrefEmail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.google.mytravelapp.R;
import com.google.mytravelapp.api.trip.TripService;
import com.google.mytravelapp.api.user.RetrofitInstance;
import com.google.mytravelapp.api.user.UserService;
import com.google.mytravelapp.database.User;
import com.google.mytravelapp.entities.TripDB;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDestinationActivity extends AppCompatActivity {

    private Uri selectedImageUri;
    static final int SELECT_IMAGE_CODE = 1;
    private Float rateValue;

    EditText tripNameDest, destinationDest, arrivingDateDestination, leavingDateDestination;
    TextView priceEurDest, imagePickDestination, testShit;
    RadioGroup radioGroupDest;
    RadioButton cityBreakDest, seaSideDest, mountainDest, radioButton;
    Slider slider;
    RatingBar ratingDest;
    Button saveButtonDest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination);

        String email = getSharedPrefEmail(getApplicationContext());
        setupViews();
        setImagePickDestination();
        setupCalendar(arrivingDateDestination);
        setSlider();
        setRatingDest();
        test_button(email);
    }

    public void saveTrip(TripDB trip, String email) {
        TripService tripService = RetrofitInstance.getRetrofitInstance().create(TripService.class);
        Call<TripDB> call = tripService.saveTrip(trip);

        call.enqueue(new Callback<TripDB>() {
            @Override
            public void onResponse(Call<TripDB> call, Response<TripDB> response) {
                if (response.isSuccessful()) {
                    TripDB savedTrip = response.body();
                    Toast.makeText(getApplicationContext(), "Trip saved successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddDestinationActivity.this, MainActivity.class);
                    intent.putExtra("email", email);
                    startActivityForResult(intent, 2);
                } else {
                    Toast.makeText(getApplicationContext(), "Error saving trip: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TripDB> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(AddDestinationActivity.this, "Network error! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddDestinationActivity.this, "An unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                }
                Log.e("AddDestinationActivity", "AddDestination API call failed: ", t);
            }
        });
    }

    private void test_button(String email) {
        saveButtonDest.setOnClickListener(view -> {
            String tripName = tripNameDest.getText().toString();
            String destination = destinationDest.getText().toString();
            String radioButtonValue = testShit.getText().toString();
            String price = getValue(priceEurDest.getText().toString());
            String arrivingDate = arrivingDateDestination.getText().toString();
            String leavingDate = leavingDateDestination.getText().toString();
            String stars = String.valueOf(rateValue);
            String linkImage = String.valueOf(selectedImageUri);
            UserService userService = RetrofitInstance.getRetrofitInstance().create(UserService.class);

            Call<User> call = userService.getUserByEmail(email);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.e("err", response.toString());
                    if (response.isSuccessful()) {
                        User user = response.body();
                        if (tripName.isEmpty() || destination.isEmpty() || radioButtonValue.isEmpty() || arrivingDate.isEmpty() || leavingDate.isEmpty() || price.isEmpty() || stars.isEmpty() || linkImage.isEmpty()) {
                            return;
                        }
                        TripDB trip = new TripDB(user, tripName, arrivingDate, leavingDate, destination, radioButtonValue, Float.parseFloat(price), Float.parseFloat(stars), linkImage, 0f, false);
                        saveTrip(trip, email);
                        Toast.makeText(getApplicationContext(), "User fetched successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error fetching user: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    if (t instanceof IOException) {
                        Toast.makeText(AddDestinationActivity.this, "Network error! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddDestinationActivity.this, "An unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("AddDestinationActivity", "Find User API call failed: ", t);
                }
            });
        });
    }

    private void setupCalendar(EditText myView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCalendar();
                updateDateRange(year, month, dayOfMonth, leavingDateDestination);
            }

            private void updateCalendar() {
                String format = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.UK);
                myView.setText(sdf.format(calendar.getTime()));
            }
        };
        myView.setOnClickListener(view -> {
            new DatePickerDialog(AddDestinationActivity.this, date, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void updateDateRange(int year1, int month1, int day1, EditText editText) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year1, month1, day1);

        editText.setOnClickListener(v -> {
            Calendar calendar1 = Calendar.getInstance();
            int year = calendar1.get(Calendar.YEAR);
            int month = calendar1.get(Calendar.MONTH);
            int day = calendar1.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddDestinationActivity.this, (view, year2, month2, day2) -> {
                String format = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.UK);
                calendar1.set(Calendar.YEAR, year2);
                calendar1.set(Calendar.MONTH, month2);
                calendar1.set(Calendar.DAY_OF_MONTH, day2);
                editText.setText(sdf.format(calendar1.getTime()));
            }, year, month, day);

            DatePicker datePicker = datePickerDialog.getDatePicker();
            datePicker.setMinDate(calendar.getTimeInMillis());
            datePickerDialog.show();
        });
    }

    private void setupViews() {
        tripNameDest = findViewById(R.id.tripNameDest);
        destinationDest = findViewById(R.id.destinationDest);
        arrivingDateDestination = findViewById(R.id.arrivingDateDestination);
        leavingDateDestination = findViewById(R.id.leavingDateDestination);
        priceEurDest = findViewById(R.id.priceEurDest);
        imagePickDestination = findViewById(R.id.imagePickDestination);
        radioGroupDest = findViewById(R.id.radioGroupDest);
        cityBreakDest = findViewById(R.id.cityBreakDest);
        seaSideDest = findViewById(R.id.seaSideDest);
        mountainDest = findViewById(R.id.mountainDest);
        slider = findViewById(R.id.slider);
        ratingDest = findViewById(R.id.ratingDest);
        testShit = findViewById(R.id.testShit);
        saveButtonDest = findViewById(R.id.saveButtonDest);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, SELECT_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if(data != null){
                Uri uri = data.getData();
                Log.e("URI", uri.toString());
                try {
                    getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                selectedImageUri = uri;
            }
        }
    }

    private void setImagePickDestination() {
        imagePickDestination.setOnClickListener(view -> {
            selectImage();
        });
    }

    public void onRadioButtonClicked(View view) {
        int radioId = radioGroupDest.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        testShit.setText(radioButton.getText());
    }

    private void setSlider() {
        slider.addOnChangeListener((slider, value, fromUser) -> {
            int index = String.valueOf(value).indexOf(".");
            String text = "Price in EUR: ";
            priceEurDest.setText(text + String.valueOf(value).substring(0, index));
        });
    }

    private void setRatingDest() {
        ratingDest.setOnRatingBarChangeListener((ratingBar, v, b) -> rateValue = ratingBar.getRating());
    }

    private static String getValue(String str) {
        String[] parts = str.split(":");
        return parts[parts.length - 1].trim();
    }
}