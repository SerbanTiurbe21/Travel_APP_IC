package com.example.travelApp;

import com.example.travelApp.entities.Trip;
import com.example.travelApp.entities.User;
import com.example.travelApp.repositories.TripRepository;
import com.example.travelApp.services.TripService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripServiceTests {
    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripService tripService;

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(tripRepository);
    }

    private static User createUser(int id, String userId, String name, String password, String email) {
        return new User(id, userId, name, password, email);
    }

    private static Trip createTrip(int id, User user, String tripName, String startDate, String endDate, String destination, String tripType, Float price, Float rating, String photoUri, Float temperature, Boolean isFavourite){
        return new Trip(id, user, tripName, startDate, endDate, destination, tripType, price, rating, photoUri, temperature, isFavourite);
    }

    @Test
    public void shouldCreateNewTripWhenValidDataIsProvided(){
        User user = createUser(1, "denard1","DenardLaza","denard2001","denardlaza@yahoo.com");
        Trip trip = createTrip(1,user,"WalkInThePark","18.05.2023","19.05.2023","Milano","CityBreak",1500.50f,5.0f,"img.jpg",23.5f,true);
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);
        Trip createdTrip = tripService.save(trip);
        assertEquals(createdTrip,trip);
        verify(tripRepository, times(1)).save(trip);
    }

    @Test
    public void shouldReturnAllTripObjects(){
        User user = createUser(1, "denard1","DenardLaza","denard2001","denardlaza@yahoo.com");
        Trip trip = createTrip(1,user,"WalkInThePark","18.05.2023","19.05.2023","Milano","CityBreak",1500.50f,5.0f,"img.jpg",23.5f,true);
        List<Trip> trips = List.of(trip);
        when(tripRepository.findAll()).thenReturn(trips);
        List<Trip> actualTrips = tripService.findAll();
        assertEquals(actualTrips,trips);
        verify(tripRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnTripWhenCorrectUserIdIsProvided(){
        User user = createUser(1, "denard1","DenardLaza","denard2001","denardlaza@yahoo.com");
        Trip trip = createTrip(1,user,"WalkInThePark","18.05.2023","19.05.2023","Milano","CityBreak",1500.50f,5.0f,"img.jpg",23.5f,true);
        List<Trip> trips = List.of(trip);
        int tripUserId = trip.getUser().getId();
        int userId = user.getId();
        assertEquals(tripUserId,userId);
        when(tripRepository.findByUserId(anyInt())).thenReturn(List.of(trip));
        List<Trip> actualTrips = tripService.findByUserId(tripUserId);
        assertEquals(actualTrips,trips);
        verify(tripRepository, times(1)).findByUserId(tripUserId);
    }

    @Test
    public void shouldReturnOptionalContainingTripWhenCorrectTripIdIsProvided(){
        User user = createUser(1, "denard1","DenardLaza","denard2001","denardlaza@yahoo.com");
        Trip trip = createTrip(1,user,"WalkInThePark","18.05.2023","19.05.2023","Milano","CityBreak",1500.50f,5.0f,"img.jpg",23.5f,true);
        when(tripRepository.findById(anyInt())).thenReturn(Optional.of(trip));
        Optional<Trip> optionalTrip = tripService.findById(trip.getId());
        assertNotNull(optionalTrip);
        Trip fromOptionalTrip = optionalTrip.get();
        assertEquals(fromOptionalTrip,trip);
        verify(tripRepository, times(1)).findById(trip.getId());
    }

    @Test
    public void shouldDeleteFromDatabaseWhenCorrectTripIdIsPassed(){
        User user = createUser(1, "denard1","DenardLaza","denard2001","denardlaza@yahoo.com");
        Trip trip = createTrip(1,user,"WalkInThePark","18.05.2023","19.05.2023","Milano","CityBreak",1500.50f,5.0f,"img.jpg",23.5f,true);
        int id = trip.getId();
        doAnswer((Answer<Void>) invocation -> null).when(tripRepository).deleteById(anyInt());
        tripService.deleteById(id);
        verify(tripRepository, times(1)).deleteById(anyInt());
    }
}
