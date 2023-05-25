package com.google.mytravelapp.fragments.trip;

import static com.google.mytravelapp.utilities.UtilitySharedPreferences.getSharedPrefEmail;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.mytravelapp.R;
import com.google.mytravelapp.activities.AddDestinationActivity;


public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final int REQUEST_CODE = 1;
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button button = (Button) view.findViewById(R.id.addTripButtonHome);
        String email = getSharedPrefEmail(getContext());
        button.setOnClickListener(myView -> {
            Intent intent = new Intent(getActivity(), AddDestinationActivity.class);
            intent.putExtra("email", email);
            startActivityForResult(intent, REQUEST_CODE);
        });
        return view;
    }
}