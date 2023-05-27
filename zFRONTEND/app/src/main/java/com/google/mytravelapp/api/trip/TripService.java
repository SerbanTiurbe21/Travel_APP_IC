package com.google.mytravelapp.api.trip;

import com.google.mytravelapp.entities.TripDB;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TripService {
    @POST("api/trips")
    Call<TripDB> saveTrip(@Body TripDB trip);

    @GET("api/trips")
    Call<List<TripDB>> getAllTrips();

    @GET("api/trips/user/{userId}")
    Call<List<TripDB>> getTripsByUserId(@Path("userId") int userId);

    @GET("api/trips/find-by-name/{tripName}")
    Call<TripDB> getTripsByName(@Path("tripName") String tripName);

    @PUT("/api/trips/{id}")
    Call<TripDB> updateTrip(@Path("id") int id, @Body TripDB trip);
}
