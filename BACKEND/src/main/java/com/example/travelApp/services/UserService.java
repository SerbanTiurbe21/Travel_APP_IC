package com.example.travelApp.services;

import com.example.travelApp.entities.User;
import com.example.travelApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean existsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    public boolean existsByName(String name) {
        return userRepository.existsByName(name);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public User updateUser(User user){
        User existingUser = userRepository.findByUserId(user.getUserId()).orElse(null);
        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    public User save(User user) {
        // Save the user to the database first to generate the ID
        User savedUser = userRepository.save(user);

        // Remove spaces from the user's name
        String nameWithoutSpaces = savedUser.getName().replace(" ", "");

        // Set the userId to the user's name concatenated with the user's ID
        savedUser.setUserId(nameWithoutSpaces + savedUser.getId());

        // Update the user record in the database with the new userId
        return userRepository.save(savedUser);
    }

    public Optional<User> findByIdWithTrips(Integer id){
        return userRepository.findByIdWithTrips(id);
    }
}
