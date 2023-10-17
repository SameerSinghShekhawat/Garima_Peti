package com.example.garimapeti.DAO;

import com.example.garimapeti.entity.ReportEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ReportDAO {

    private DatabaseReference databaseReference;

    public ReportDAO(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(ReportEntity.class.getSimpleName());
    }

    public Task<Void> add(ReportEntity re){
        return databaseReference.push().setValue(re);
    }

    public Query get() {return databaseReference.orderByKey();}

}
