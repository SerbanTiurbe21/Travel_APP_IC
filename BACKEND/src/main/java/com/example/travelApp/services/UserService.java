package com.example.travelApp.services;

import com.example.travelApp.entities.User;
import com.example.travelApp.exceptions.UnauthorizedException;
import com.example.travelApp.login.LoginRequest;
import com.example.travelApp.login.LoginResponse;
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

    public Optional<User> updateUser(Integer id, User user){
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setUserId(user.getUserId());
            existingUser.setName(user.getName());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
        }
        return existingUserOptional;
    }

    public LoginResponse loginUser(LoginRequest loginRequest) throws UnauthorizedException {
        Optional<User> foundUser = userRepository.findByEmail(loginRequest.getEmail());
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setUser(user);
                loginResponse.setAccessToken(null);
                loginResponse.setMessage("Login successful.");
                return loginResponse;
            }
        }
        throw new UnauthorizedException("Invalid email or password.");
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
