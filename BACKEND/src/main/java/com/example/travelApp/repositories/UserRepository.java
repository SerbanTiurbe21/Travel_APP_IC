package com.example.travelApp.repositories;

import com.example.travelApp.entities.Trip;
import com.example.travelApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(String userId);

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    boolean existsByUserId(String userId);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    List<User> findByUserIdContaining(String userIdPart);

    List<User> findByNameContaining(String namePart);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.trips WHERE u.id = :id")
    Optional<User> findByIdWithTrips(@Param("id") Integer id);
}