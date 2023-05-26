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
    private String tripName;
    private String destination;
    private String imageUrl;
    private float price;
    private float rating;
    private boolean isBookmarked;
    private String email;
}
