package com.example.garimapeti.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.garimapeti.R;

public class HomeFragment extends Fragment {

    CardView fileComplaintCV;
    CardView helplineCV;
    CardView emergencyCV;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fileComplaintCV = view.findViewById(R.id.fileComplaintCV);
        helplineCV = view.findViewById(R.id.helpLineCV);
        emergencyCV = view.findViewById(R.id.emergencyCV);
        fileComplaintCV.setOnClickListener(view1 -> loadFragment(new AuthFragment()));
        helplineCV.setOnClickListener(view12 -> loadFragment(new HelplineFragment()));
        emergencyCV.setOnClickListener(view13 -> loadFragment(new EmergencyFragment()));

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment).addToBackStack("abc");

        ft.commit();

    }
}