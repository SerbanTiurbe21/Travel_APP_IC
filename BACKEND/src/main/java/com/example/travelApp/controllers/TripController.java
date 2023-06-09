package com.example.travelApp.controllers;

import com.example.travelApp.dto.TripDTO;
import com.example.travelApp.entities.Trip;
import com.example.travelApp.entities.User;
import com.example.travelApp.exceptions.TripNotFoundException;
import com.example.travelApp.services.TripService;
import com.example.travelApp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.travelApp.utility.TripDtoConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<TripDTO>> getAllTrips() {
        List<Trip> trips = tripService.findAll();
        List<TripDTO> tripDTOs = trips.
                stream()
                .map(TripDtoConverter::tripToTripDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tripDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTripById(@PathVariable Integer id) {
        Optional<Trip> trip = tripService.findById(id);
        if (trip.isPresent()) {
            Trip existingTrip = trip.get();
            return new ResponseEntity<>(existingTrip, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "The trip with ID " + id + " doesn't exist.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find-by-name/{tripName}")
    public ResponseEntity<Object> getTripByName(@PathVariable String tripName){
        Optional<Trip> trip = tripService.findByTripName(tripName);
        if (trip.isPresent()) {
            Trip existingTrip = trip.get();
            return new ResponseEntity<>(existingTrip, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "The trip with name " + tripName + " doesn't exist.");
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
        Integer userId = trip.getUser().getId();
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "The user with ID " + userId + " doesn't exist.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Trip newTrip = tripService.save(trip);
        return new ResponseEntity<>(newTrip, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Integer id, @RequestBody Trip updatedTrip) {
        try{
            Trip trip = tripService.updateTrip(id, updatedTrip);
            return ResponseEntity.ok(trip);
        }catch (TripNotFoundException e){
            return ResponseEntity.notFound().build();
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
