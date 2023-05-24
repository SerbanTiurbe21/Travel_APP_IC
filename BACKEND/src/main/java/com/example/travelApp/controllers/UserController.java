package com.example.travelApp.controllers;

import com.example.travelApp.entities.User;
import com.example.travelApp.exceptions.UnauthorizedException;
import com.example.travelApp.login.LoginRequest;
import com.example.travelApp.login.LoginResponse;
import com.example.travelApp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        Optional<User> user = userService.findByIdWithTrips(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("error", "User with id " + id + " not found.");
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find-by-email")
    public ResponseEntity<?> testFindByEmail(@RequestParam("email") String email) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("error", "User with email " + email + " not found.");
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("The same email cannot be registered.");
        }
        User savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = userService.loginUser(loginRequest);
            return ResponseEntity.ok().body(loginResponse);
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String email,
                                           @RequestParam("oldPassword") String oldPassword,
                                           @RequestParam("newPassword") String newPassword,
                                           @RequestParam("confirmNewPassword") String confirmNewPassword) {

        if (!newPassword.equals(confirmNewPassword)) {
            return ResponseEntity.badRequest().body("New password and confirmation do not match");
        }
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(oldPassword)) {
            return ResponseEntity.badRequest().body("Old password is incorrect");
        }

        user.setPassword(newPassword);
        userService.save(user);
        return ResponseEntity.ok("Password updated successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user) {
        Optional<User> updatedUser = userService.updateUser(id, user);
        if (updatedUser.isPresent()) {
            return new ResponseEntity<>(updatedUser.get(), HttpStatus.OK);
        } else {
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("error", "User with id " + id + " not found.");
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("error", "User with id " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
