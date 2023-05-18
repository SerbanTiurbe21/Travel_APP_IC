package com.example.travelApp;

import com.example.travelApp.entities.Trip;
import com.example.travelApp.entities.User;
import com.example.travelApp.repositories.UserRepository;
import com.example.travelApp.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static User createUser(int id, String userId, String name, String password, String email) {
        return new User(id, userId, name, password, email);
    }

    private static Trip createTrip(int id, User user, String tripName, String startDate, String endDate, String destination, String tripType, Float price, Float rating, String photoUri, Float temperature, Boolean isFavourite){
        return new Trip(id, user, tripName, startDate, endDate, destination, tripType, price, rating, photoUri, temperature, isFavourite);
    }

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void shouldInsertCorrectlyWhenValidDataIsProvided() {
        User user = createUser(1, "denard1","DenardLaza","denard2001","denardlaza@yahoo.com");
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.save(user);
        assertEquals(user, createdUser);
        verify(userRepository,times(2)).save(any(User.class));
    }


    @Test
    public void shouldDeleteFromDatabaseWhenCorrectIdIsPassed() {
        User user = createUser(1, "denard1","DenardLaza","denard2001","denardlaza@yahoo.com");
        int id = user.getId();
        doAnswer((Answer<Void>) invocation -> null).when(userRepository).deleteById(anyInt());
        userService.deleteById(id);
        verify(userRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void shouldReturnUserWhenCorrectEmailIsProvided() {
        User user = createUser(1, "denard1","DenardLaza","denard2001","denardlaza@yahoo.com");
        String email = user.getEmail();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        Optional<User> result = userService.findByEmail(email);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void shouldReturnUserWhenCorrectUsernameIsProvided() {
        User user = createUser(1, "denard1","DenardLaza","denard2001","denardlaza@yahoo.com");
        String username = user.getName();
        when(userRepository.findByName(anyString())).thenReturn(Optional.of(user));
        Optional<User> result = userService.findByName(username);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository,times(1)).findByName(username);
    }

    @Test
    public void shouldReturnUserWhenCorrectIdIsProvided() {
        User user = createUser(1, "denard1","DenardLaza","denard2001","denardlaza@yahoo.com");
        int id = user.getId();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        Optional<User> result = userService.findById(id);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository,times(1)).findById(id);
    }

    @Test
    public void shouldReturnUserWhenCorrectUserIdIsProvided(){
        User user = createUser(1, "denard1","DenardLaza","denard2001","denardlaza@yahoo.com");
        String userId = user.getUserId();
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        Optional<User> optionalUser = userService.findByUserId(userId);
        assertTrue(optionalUser.isPresent());
        assertEquals(user,optionalUser.get());
        verify(userRepository,times(1)).findByUserId(userId);
    }

    @Test
    public void shouldReturnListContainingAllUsers(){
        User user = createUser(1, "denard1","DenardLaza","denard2001","denardlaza@yahoo.com");
        List<User> userList = List.of(user);
        when(userRepository.findAll()).thenReturn(userList);
        List<User> actualUsers = userService.findAll();
        assertEquals(userList,actualUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void shouldFindByIdWithTripsReturnsUserWhenUserExists(){
        User user = createUser(1, "denard1","DenardLaza","denard2001","denardlaza@yahoo.com");
        int id = user.getId();
        Trip trip = createTrip(1,user,"WalkInThePark","18.05.2023","19.05.2023","Milano","CityBreak",1500.50f,5.0f,"img.jpg",23.5f,true);
        user.setTrips(Set.of(trip));
        when(userRepository.findByIdWithTrips(anyInt())).thenReturn(Optional.of(user));
        Optional<User> optionalUser = userService.findByIdWithTrips(id);
        assertTrue(optionalUser.isPresent());
        User fromOptionalUser = optionalUser.get();
        Set<Trip> tripList = fromOptionalUser.getTrips();
        assertEquals(user.getTrips(),tripList);
        verify(userRepository, times(1)).findByIdWithTrips(id);
    }
}