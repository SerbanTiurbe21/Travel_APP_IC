package com.example.travelApp.services;

import com.example.travelApp.entities.User;
import com.example.travelApp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
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

    public User updateUser(User user){
        User existingUser = userRepository.findByUserId(user.getUserId()).orElse(null);
        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    public User save(User user) {
        User savedUser = userRepository.save(user);
        String nameWithoutSpaces = savedUser.getName().replace(" ", "");
        savedUser.setUserId(nameWithoutSpaces + savedUser.getId());
        return userRepository.save(savedUser);
    }

    public Optional<User> findByIdWithTrips(Integer id){
        return userRepository.findByIdWithTrips(id);
    }
}
