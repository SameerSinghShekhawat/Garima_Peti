package com.example.garimapeti.fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.garimapeti.MainActivity;
import com.example.garimapeti.R;

public class EmergencyFragment extends Fragment {

    EditText emPhEditText;
    ImageView sosIV;
    Button addBtn;
    String phone;
    Boolean isPhoneAdded;

    public EmergencyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);
        emPhEditText = view.findViewById(R.id.emPhEditText);
        addBtn = view.findViewById(R.id.addButton);
        sosIV = view.findViewById(R.id.sosIV);
        loadData();

        addBtn.setOnClickListener(view1 -> {
            updateNumber();
        });

        sosIV.setOnClickListener(view12 -> {
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS)
            == PackageManager.PERMISSION_GRANTED){
                sendSMS();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS);
            }
        });

        return view;
    }

    private void sendSMS() {
        SmsManager smsManager = SmsManager.getDefault();
        if(phone.length()!=0 && !emPhEditText.isEnabled()) {
            smsManager.sendTextMessage(phone, null, "From App", null, null);
            Toast.makeText(getContext(), "Message Sent", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Add Phone Number First", Toast.LENGTH_SHORT).show();
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),isGranted -> {
                if(isGranted){
                    sendSMS();
                } else {
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            });
    private void saveData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone", emPhEditText.getText().toString());
        editor.apply();
        Toast.makeText(getContext(), "Number Saved", Toast.LENGTH_SHORT).show();
        isPhoneAdded=true;
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefer", Context.MODE_PRIVATE);
        phone = sharedPreferences.getString("phone","");
        if(phone.isEmpty()){
            isPhoneAdded = false;
            emPhEditText.setEnabled(true);
            addBtn.setText("Add");
        } else {
            isPhoneAdded = true;
            emPhEditText.setText(phone);
            emPhEditText.setEnabled(false);
            addBtn.setText("Edit");
        }
    }

    private void updateNumber(){
        if(isPhoneAdded==false){
            if(emPhEditText.getText().toString().length()==10){
                saveData();
                phone = emPhEditText.getText().toString();
                emPhEditText.setEnabled(false);
                addBtn.setText("Edit");
            } else{
                Toast.makeText(getContext(), "Enter Correct Number", Toast.LENGTH_SHORT).show();
            }
        } else {
            emPhEditText.setEnabled(true);
            isPhoneAdded=false;
            addBtn.setText("Add");
        }
    }


}