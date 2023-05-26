package com.google.mytravelapp.entities;

import com.google.mytravelapp.database.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TripDB {
    private Integer id;
    private User user;
    private String tripName;
    private String startDate;
    private String endDate;
    private String destination;
    private String tripType;
    private Float price;
    private Float rating;
    private String photoUri;
    private Float temperature;
    private Boolean isFavourite;

    public TripDB(User user, String tripName, String startDate, String endDate, String destination, String tripType, Float price, Float rating, String photoUri, Float temperature, Boolean isFavourite) {
        this.user = user;
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.destination = destination;
        this.tripType = tripType;
        this.price = price;
        this.rating = rating;
        this.photoUri = photoUri;
        this.temperature = temperature;
        this.isFavourite = isFavourite;
    }
}
