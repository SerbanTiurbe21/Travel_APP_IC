package com.example.travelApp.repositories;

import com.example.travelApp.entities.Trip;
import com.example.travelApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findByUserId(Integer userId);
    Optional<Trip> findById(Integer id);
    Optional<Trip> findByTripName(String tripName);
}
