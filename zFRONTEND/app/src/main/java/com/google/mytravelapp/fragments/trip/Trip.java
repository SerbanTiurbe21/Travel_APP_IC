package com.google.mytravelapp.fragments.trip;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Trip {
    private String tripName;
    private String destination;
    private String imageUrl;
    private float price;
    private float rating;
    private boolean isBookmarked;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(tripName, trip.tripName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripName);
    }
}
