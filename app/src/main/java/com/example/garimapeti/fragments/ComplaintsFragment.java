package com.example.garimapeti.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.garimapeti.DAO.ReportDAO;
import com.example.garimapeti.R;
import com.example.garimapeti.adapter.ComplaintListAdapter;
import com.example.garimapeti.entity.ReportEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class ComplaintsFragment extends Fragment {

    SearchView searchView;
    ListView complaintsListView;
    ComplaintListAdapter complaintListAdapter;
    ReportDAO reportDAO;
    private Context mContext;
    ArrayList<ReportEntity> arrayList;
    ArrayList<ReportEntity> mFilteredList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public ComplaintsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaints, container, false);

        searchView = view.findViewById(R.id.searchView);
        complaintsListView = view.findViewById(R.id.complaintLV);
        reportDAO = new ReportDAO();

        loadData();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        complaintsListView.setOnItemClickListener((adapterView, view1, i, l) -> {
            Bundle b = new Bundle();
            ReportEntity reportEntity = (ReportEntity) adapterView.getItemAtPosition(i);
            b.putSerializable("clickedItem", reportEntity);
            ViewComplaintFragment viewComplaintFragment = new ViewComplaintFragment();
            viewComplaintFragment.setArguments(b);
            loadFragment(viewComplaintFragment);
            mFilteredList.clear();
        });

        return view;
    }

    private void loadData(){
        reportDAO.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for(DataSnapshot data : snapshot.getChildren()){
                    ReportEntity re = data.getValue(ReportEntity.class);
                    arrayList.add(re);
                }
                mFilteredList.addAll(arrayList);
                complaintListAdapter = new ComplaintListAdapter(mContext,R.layout.complaint_list_row,arrayList);
                complaintsListView.setAdapter(complaintListAdapter);
                complaintListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arrayList.clear();
        if (charText.length() == 0) {
            arrayList.addAll(mFilteredList);
        } else {
            for (ReportEntity re : mFilteredList) {
                if (re.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    arrayList.add(re);
                }
            }
        }
        complaintListAdapter.notifyDataSetChanged();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment).addToBackStack("abc");
        ft.commit();
    }

}