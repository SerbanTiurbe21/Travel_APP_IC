package com.example.travelApp.controllers;

import com.example.travelApp.entities.Trip;
import com.example.travelApp.entities.User;
import com.example.travelApp.services.TripService;
import com.example.travelApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    @Autowired
    private TripService tripService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.findAll();
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTripById(@PathVariable Integer id) {
        Optional<Trip> trip = tripService.findById(id);
        if (trip.isPresent()) {
            Trip existingTrip = trip.get();
            //existingTrip.setUser(null); // Set the user to null
            return new ResponseEntity<>(existingTrip, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "The trip with ID " + id + " doesn't exist.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTripsByUserId(@PathVariable Integer userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            List<Trip> trips = tripService.findByUserId(userId);
            return new ResponseEntity<>(trips, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "The user with ID " + userId + " doesn't exist.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createTrip(@RequestBody Trip trip) {
        // Check if the user exists
        Integer userId = trip.getUser().getId();
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "The user with ID " + userId + " doesn't exist.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Save the trip
        Trip newTrip = tripService.save(trip);
        return new ResponseEntity<>(newTrip, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Integer id, @RequestBody Trip trip) {
        Optional<Trip> existingTrip = tripService.findById(id);

        if (existingTrip.isPresent()) {
            Trip updatedTrip = existingTrip.get();
            // Update the fields of the existing trip with the new values from the request
            // For example: updatedTrip.setTripName(trip.getTripName());
            updatedTrip.setTripName(trip.getTripName());
            updatedTrip.setStartDate(trip.getStartDate());
            updatedTrip.setEndDate(trip.getEndDate());
            updatedTrip.setDestination(trip.getDestination());
            updatedTrip.setTripType(trip.getTripType());
            updatedTrip.setPrice(trip.getPrice());
            updatedTrip.setRating(trip.getRating());
            updatedTrip.setPhotoUri(trip.getPhotoUri());
            updatedTrip.setTemperature(trip.getTemperature());
            updatedTrip.setIsFavourite(trip.getIsFavourite());
            tripService.save(updatedTrip);
            return new ResponseEntity<>(updatedTrip, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "The trip with ID " + id + " doesn't exist.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Integer id) {
        Optional<Trip> trip = tripService.findById(id);
        if (trip.isPresent()) {
            tripService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "The trip with ID " + id + " doesn't exist.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}