package com.example.garimapeti.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garimapeti.R;
import com.example.garimapeti.entity.ReportEntity;

import org.w3c.dom.Text;

public class ViewComplaintFragment extends Fragment {

    String date, name, clss, school, phone, complaint;
    TextView dateTV, nameTV, classTV, schoolTV, phoneTV, complaintTV;

    public ViewComplaintFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_complaint, container, false);

        dateTV = view.findViewById(R.id.cmpDateTV);
        nameTV = view.findViewById(R.id.cmpNameTV);
        classTV = view.findViewById(R.id.cmpClassTV);
        schoolTV = view.findViewById(R.id.cmpSchoolTV);
        phoneTV = view.findViewById(R.id.cmpPhoneTV);
        complaintTV = view.findViewById(R.id.cmpComplaintTV);

        Bundle b = getArguments();
        if (b != null){
            ReportEntity reportEntity = (ReportEntity) b.getSerializable("clickedItem");
            date = reportEntity.getReportDate();
            name = "Name: "+reportEntity.getName();
            clss = "Class: "+reportEntity.getClss();
            school = "School: "+reportEntity.getSchool();
            phone = "Phone: "+reportEntity.getPhone();
            complaint = reportEntity.getReportDesc();

            dateTV.setText(date);
            nameTV.setText(name);
            classTV.setText(clss);
            schoolTV.setText(school);
            phoneTV.setText(phone);
            complaintTV.setText(complaint);
        }
        return view;
    }
}