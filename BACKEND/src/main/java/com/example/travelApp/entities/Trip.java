package com.example.travelApp.entities;

import jakarta.persistence.*;

@Entity
@Table(name="trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "trip_name", nullable = true)
    private String tripName;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "destination")
    private String destination;

    @Column(name = "trip_type")
    private String tripType;

    @Column(name = "price")
    private Float price;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "photo_uri")
    private String photoUri;

    @Column(name = "temperature")
    private Float temperature;

    @Column(name = "is_favourite")
    private Boolean isFavourite;

    public Trip(){

    }

    public Trip(Integer id, User user, String tripName, String startDate, String endDate, String destination, String tripType, Float price, Float rating, String photoUri, Float temperature, Boolean isFavourite) {
        this.id = id;
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

    // Getters and setters for all fields

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
}
