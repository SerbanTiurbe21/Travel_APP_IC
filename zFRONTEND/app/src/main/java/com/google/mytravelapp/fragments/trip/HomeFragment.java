package com.google.mytravelapp.fragments.trip;

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

    private static String email;
    private final int REQUEST_CODE = 1;
    private String mParam1;
    private String mParam2;

    public HomeFragment() {

    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            email = bundle.getString("email");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button button = (Button) view.findViewById(R.id.addTripButtonHome);
        button.setOnClickListener(myView -> {
            Intent intent = new Intent(getActivity(), AddDestinationActivity.class);
            intent.putExtra("email", email);
            startActivityForResult(intent, REQUEST_CODE);
        });
        return view;
    }
}