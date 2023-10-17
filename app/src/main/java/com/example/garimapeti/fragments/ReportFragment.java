package com.example.garimapeti.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.garimapeti.DAO.ReportDAO;
import com.example.garimapeti.R;
import com.example.garimapeti.entity.ReportEntity;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Calendar;
import java.util.Date;


public class ReportFragment extends Fragment {

    EditText nameEditText;
    EditText classEditText;
    EditText schoolEditText;
    EditText reportEditText;
    Button submitReportBtn;
    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        nameEditText = view.findViewById(R.id.nameEditText);
        classEditText = view.findViewById(R.id.classEditText);
        schoolEditText = view.findViewById(R.id.schoolEditText);
        reportEditText = view.findViewById(R.id.reportEditText);
        submitReportBtn = view.findViewById(R.id.submitReportBtn);

        ReportDAO reportDAO = new ReportDAO();

        submitReportBtn.setOnClickListener(view1 -> {
            Bundle bundle = this.getArguments();
            String phone="";
            if(bundle != null){
                phone = bundle.getString("phone");
            }
            String name = nameEditText.getText().toString();
            String clss = classEditText.getText().toString();
            String school = schoolEditText.getText().toString();
            String report = reportEditText.getText().toString();

            String reportDate = Calendar.getInstance().getTime().toString();

            if(name.isEmpty()||clss.isEmpty()||school.isEmpty()||report.isEmpty()||phone.isEmpty()){
                Toast.makeText(getContext(), "Fill All Details", Toast.LENGTH_SHORT).show();
            } else if(!clss.matches("([1-9]|1[0-2])")){
                Toast.makeText(getContext(), "Enter Class Between 1-12", Toast.LENGTH_SHORT).show();
            } else {
                ReportEntity re = new ReportEntity(name, clss, school, phone, report, reportDate);
                reportDAO.add(re).addOnSuccessListener(suc -> {
                    Toast.makeText(getContext(), "Report Submitted", Toast.LENGTH_SHORT).show();
                    submitReportBtn.setEnabled(false);
                    nameEditText.setEnabled(false);
                    classEditText.setEnabled(false);
                    schoolEditText.setEnabled(false);
                    reportEditText.setEnabled(false);
                    loadFragment(new HomeFragment());
                }).addOnFailureListener(er -> {
                    Toast.makeText(getContext(), er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}