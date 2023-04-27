package com.google.mytravelapp.api.trip;

import com.google.mytravelapp.entities.TripDB;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TripService {
    @POST("api/trips")
    Call<TripDB> saveTrip(@Body TripDB trip);
}
