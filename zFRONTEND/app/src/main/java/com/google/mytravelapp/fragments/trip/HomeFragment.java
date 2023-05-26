package com.google.mytravelapp.fragments.trip;

import static com.google.mytravelapp.utilities.UtilitySharedPreferences.getSharedPrefEmail;
import static com.google.mytravelapp.utilities.UtilitySharedPreferences.getSharedPrefId;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.mytravelapp.R;
import com.google.mytravelapp.activities.AddDestinationActivity;
import com.google.mytravelapp.database.viewmodel.CityViewModel;
import com.google.mytravelapp.entities.TripDB;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private final int REQUEST_CODE = 1;
    private RecyclerView tripRecyclerView;
    private CityViewModel cityViewModel;
    private TripAdapter tripAdapter;
    private TripViewModel tripViewModel;
    private SwipeRefreshLayout swipeRefresh;
    List<Trip> myTrips;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button button = (Button) view.findViewById(R.id.addTripButtonHome);

        tripAdapter = new TripAdapter(getContext());
        myTrips = new ArrayList<>();
        tripAdapter.setTripList(myTrips);

        tripRecyclerView = view.findViewById(R.id.recyclerView);
        tripRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tripRecyclerView.setAdapter(tripAdapter);

        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this::refreshTrips);

        cityViewModel = new ViewModelProvider(this).get(CityViewModel.class);
        Integer id = getSharedPrefId(getContext());
        cityViewModel.getTripsByUserId(id).observe(getViewLifecycleOwner(), this::updateTrips);

        buttonToAddDestination(button);
        return view;
    }

    private void buttonToAddDestination(Button button){
        button.setOnClickListener(myView -> {
            String email = getSharedPrefEmail(getContext());
            Intent intent = new Intent(getActivity(), AddDestinationActivity.class);
            intent.putExtra("email", email);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    private void refreshTrips() {
        cityViewModel.refreshTrips();
        swipeRefresh.setRefreshing(false);
    }

    private void updateTrips(List<TripDB> trips) {
        myTrips.clear();
        for (TripDB tripDB : trips) {
            Trip trip = new Trip(tripDB.getTripName(), tripDB.getDestination(), tripDB.getPhotoUri(), tripDB.getPrice(), tripDB.getRating(), tripDB.getIsFavourite(), tripDB.getUser().getEmail());
            myTrips.add(trip);
        }
        tripAdapter.notifyDataSetChanged();
    }
}