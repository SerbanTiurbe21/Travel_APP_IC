package com.google.mytravelapp.api.city;

import com.google.mytravelapp.api.city.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("weather")
    Call<Example> getWeather(@Query("q") String cityName,
                             @Query("appid") String apiKey);
}
