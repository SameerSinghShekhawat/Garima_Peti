package com.example.garimapeti.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.garimapeti.R;


public class HelplineFragment extends Fragment {

    CardView policeCV, womanCV, childCV;

    public HelplineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_helpline, container, false);
        policeCV = view.findViewById(R.id.policeHLCV);
        womanCV = view.findViewById(R.id.womanHLCV);
        childCV = view.findViewById(R.id.childHLCV);

        policeCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:100"));
            startActivity(intent);
        });
        womanCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1090"));
            startActivity(intent);
        });
        childCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1098"));
            startActivity(intent);
        });


        return view;
    }
}