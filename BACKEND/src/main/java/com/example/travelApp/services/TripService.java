package com.example.travelApp.services;

import com.example.travelApp.entities.Trip;
import com.example.travelApp.entities.User;
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

}
