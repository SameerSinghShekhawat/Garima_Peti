package com.example.garimapeti.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.garimapeti.DAO.AdminDAO;
import com.example.garimapeti.R;
import com.example.garimapeti.entity.AdminEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ComplaintsAuthFragment extends Fragment {

    EditText idEditText;
    EditText passEditText;
    Button compAuthSubBtn;
    AdminDAO adminDAO;
    String id;
    String pass;

    public ComplaintsAuthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaints_auth, container, false);
        idEditText = view.findViewById(R.id.idEditText);
        passEditText = view.findViewById(R.id.passEditText);
        compAuthSubBtn = view.findViewById(R.id.compAuthSubBtn);
        adminDAO = new AdminDAO();
        compAuthSubBtn.setEnabled(false);
        loadData();
        compAuthSubBtn.setOnClickListener(view1 -> {
            if(idEditText.getText().toString().isEmpty() || passEditText.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Require ID and Password", Toast.LENGTH_SHORT).show();
            } else if (idEditText.getText().toString().equals(id) && passEditText.getText().toString().equals(pass)){
                loadFragment(new ComplaintsFragment());
            } else {
                Toast.makeText(getContext(), "Wrong ID or Password", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void loadData(){
        adminDAO.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AdminEntity ae = snapshot.child("-NgnwbzORyE6e3nI04a3").getValue(AdminEntity.class);
                id = ae.getId();
                pass = ae.getPassword();
                compAuthSubBtn.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment).addToBackStack("abc");
        ft.commit();
    }
}