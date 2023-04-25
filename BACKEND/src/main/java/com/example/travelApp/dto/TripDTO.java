package com.example.travelApp.dto;

public class TripDTO {
    private Integer id;
    private UserSummaryDTO user;
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

    public TripDTO() {
    }

    public TripDTO(Integer id, UserSummaryDTO user, String tripName, String startDate, String endDate, String destination, String tripType, Float price, Float rating, String photoUri, Float temperature, Boolean isFavourite) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserSummaryDTO getUser() {
        return user;
    }

    public void setUser(UserSummaryDTO user) {
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

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }
}
