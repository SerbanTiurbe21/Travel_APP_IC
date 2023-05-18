package com.example.travelApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
