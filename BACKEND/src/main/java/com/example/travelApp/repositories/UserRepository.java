package com.example.travelApp.repositories;

import com.example.travelApp.entities.Trip;
import com.example.travelApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
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
}