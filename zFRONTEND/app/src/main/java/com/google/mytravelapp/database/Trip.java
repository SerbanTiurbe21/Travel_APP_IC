package com.google.mytravelapp.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    private Integer id;
    private User user;
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
