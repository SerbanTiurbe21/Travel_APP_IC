package com.example.travelApp.utility;

import com.example.travelApp.dto.TripDTO;
import com.example.travelApp.dto.UserSummaryDTO;
import com.example.travelApp.entities.Trip;
import com.example.travelApp.entities.User;

public class TripDtoConverter {
    public static TripDTO tripToTripDTO(Trip trip) {
        User user = trip.getUser();
        UserSummaryDTO userSummaryDTO = new UserSummaryDTO(user.getId(), user.getUserId(), user.getName(), user.getEmail());

        return new TripDTO(
                trip.getId(),
                userSummaryDTO,
                trip.getTripName(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDestination(),
                trip.getTripType(),
                trip.getPrice(),
                trip.getRating(),
                trip.getPhotoUri(),
                trip.getTemperature(),
                trip.getIsFavourite()
        );
    }
}
