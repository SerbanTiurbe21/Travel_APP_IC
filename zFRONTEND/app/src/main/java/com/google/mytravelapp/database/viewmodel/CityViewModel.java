package com.google.mytravelapp.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.mytravelapp.database.repository.TripRepository;
import com.google.mytravelapp.entities.TripDB;

import java.util.List;

public class CityViewModel extends AndroidViewModel {
    private final TripRepository tripRepository;
    private int currentUserId;

    public CityViewModel(@NonNull Application application) {
        super(application);
        tripRepository = new TripRepository();
    }

    public LiveData<List<TripDB>> getAllTrips() {
        return tripRepository.getAllTrips();
    }

    public LiveData<List<TripDB>> getTripsByUserId(int userId) {
        currentUserId = userId;
        return tripRepository.getTripsByUserId(userId);
    }

    public void refreshTrips() {
        //tripRepository.refreshTrips(id);
        tripRepository.getTripsByUserId(currentUserId);
    }
}
