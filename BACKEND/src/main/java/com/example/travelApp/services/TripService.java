package com.example.travelApp.services;

import com.example.travelApp.entities.Trip;
import com.example.travelApp.exceptions.TripNotFoundException;
import com.example.travelApp.repositories.TripRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TripService {
    private TripRepository tripRepository;

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public List<Trip> findByUserId(Integer userId) {
        return tripRepository.findByUserId(userId);
    }

    public Optional<Trip> findById(Integer id) {
        return tripRepository.findById(id);
    }

    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    public void deleteById(Integer id) {
        tripRepository.deleteById(id);
    }

    public Optional<Trip> findByTripName(String tripName){
        return this.tripRepository.findByTripName(tripName);
    }

    public Trip updateTrip(Integer id, Trip updatedTrip) throws TripNotFoundException {
        return tripRepository.findById(id)
                .map(trip -> {
                    trip.setTripName(updatedTrip.getTripName());
                    trip.setStartDate(updatedTrip.getStartDate());
                    trip.setEndDate(updatedTrip.getEndDate());
                    trip.setDestination(updatedTrip.getDestination());
                    trip.setTripType(updatedTrip.getTripType());
                    trip.setPrice(updatedTrip.getPrice());
                    trip.setPrice(updatedTrip.getPrice());
                    trip.setPhotoUri(updatedTrip.getPhotoUri());
                    trip.setTemperature(updatedTrip.getTemperature());
                    trip.setIsFavourite(updatedTrip.getIsFavourite());
                    return tripRepository.save(trip);
                })
                .orElseThrow(() -> new TripNotFoundException("Trip with id: " + id + " not found"));
    }

}
