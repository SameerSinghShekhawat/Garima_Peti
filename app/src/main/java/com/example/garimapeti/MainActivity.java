package com.example.garimapeti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.garimapeti.DAO.AdminDAO;
import com.example.garimapeti.entity.AdminEntity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AdminEntity adminEntity = new AdminEntity("Paladi", "456321");
        //AdminDAO adminDAO = new AdminDAO();
        //adminDAO.add(adminEntity);

        Intent main2 = new Intent(MainActivity.this, MainActivity2.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(main2);
                finish();
            }
        }, 3000);

    }
}