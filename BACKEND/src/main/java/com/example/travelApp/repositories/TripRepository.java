package com.example.travelApp.repositories;

import com.example.travelApp.entities.Trip;
import com.example.travelApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    // Find all trips by a specific user
    List<Trip> findByUser(User user);

    // Find all trips by user_id
    List<Trip> findByUserId(Integer userId);

    Optional<Trip> findById(Integer id);

    Optional<Trip> findByUserAndTripName(User user, String tripName);

    List<Trip> findByDestination(String destination);

    List<Trip> findByIsFavourite(Boolean isFavourite);

    List<Trip> findByTripType(String tripType);

    List<Trip> findByPriceBetween(Float minPrice, Float maxPrice);

    List<Trip> findByRatingGreaterThanEqual(Float minRating);
}
