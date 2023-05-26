package com.google.mytravelapp.database.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.mytravelapp.api.trip.TripService;
import com.google.mytravelapp.api.user.RetrofitInstance;
import com.google.mytravelapp.entities.TripDB;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TripRepository {
    private final TripService tripService;
    private final MutableLiveData<List<TripDB>> tripsLiveData;

    public TripRepository() {
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        tripService = retrofit.create(TripService.class);
        tripsLiveData = new MutableLiveData<>();
    }

    public LiveData<List<TripDB>> getAllTrips() {
        return tripsLiveData;
    }

    public void refreshTrips(int id) {
        tripService.getTripsByUserId(id).enqueue(new Callback<List<TripDB>>() {
            @Override
            public void onResponse(Call<List<TripDB>> call, Response<List<TripDB>> response) {
                if (response.isSuccessful()) {
                    tripsLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TripDB>> call, Throwable t) {
                Log.e("AddDestinationActivity", "Get all destinations API call failed: ", t);
            }
        });
    }

    public LiveData<List<TripDB>> getTripsByUserId(int userId) {
        MutableLiveData<List<TripDB>> userTripsLiveData = new MutableLiveData<>();
        tripService.getTripsByUserId(userId).enqueue(new Callback<List<TripDB>>() {
            @Override
            public void onResponse(Call<List<TripDB>> call, Response<List<TripDB>> response) {
                if (response.isSuccessful()) {
                    userTripsLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TripDB>> call, Throwable t) {
                Log.e("TripRepository", "Get trips by user ID API call failed: ", t);
            }
        });
        return userTripsLiveData;
    }
}
