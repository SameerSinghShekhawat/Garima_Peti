package com.example.garimapeti.DAO;

import com.example.garimapeti.entity.AdminEntity;
import com.example.garimapeti.entity.ReportEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AdminDAO {

    private DatabaseReference databaseReference;

    public AdminDAO(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(AdminEntity.class.getSimpleName());
    }

    //public Task<Void> add(AdminEntity ae){
        //return databaseReference.push().setValue(ae);
    //}

    public Query get() {return databaseReference;}

}
