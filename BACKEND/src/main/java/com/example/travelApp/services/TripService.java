package com.example.travelApp.services;

import com.example.travelApp.entities.Trip;
import com.example.travelApp.entities.User;
import com.example.travelApp.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    @Autowired
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

    public List<Trip> findByUser(User user) {
        return tripRepository.findByUser(user);
    }

    public Optional<Trip> findByUserAndTripName(User user, String tripName) {
        return tripRepository.findByUserAndTripName(user, tripName);
    }

    public List<Trip> findByDestination(String destination) {
        return tripRepository.findByDestination(destination);
    }

    public List<Trip> findByIsFavourite(Boolean isFavourite) {
        return tripRepository.findByIsFavourite(isFavourite);
    }
}
