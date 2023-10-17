package com.example.garimapeti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.garimapeti.fragments.ComplaintsAuthFragment;
import com.example.garimapeti.fragments.ComplaintsFragment;
import com.example.garimapeti.fragments.DevFragment;
import com.example.garimapeti.fragments.HomeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity2 extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();

        loadFragment(new HomeFragment(), false);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if(itemId == R.id.item_home){
                    loadFragment(new HomeFragment(),true);
                    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else if(itemId == R.id.item_viewComp) {
                    loadFragment(new ComplaintsAuthFragment(), true);
                } else if(itemId == R.id.item_dev) {
                    loadFragment(new DevFragment(),true);
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });


    }

    private void loadFragment(Fragment fragment, boolean isFragmentAdded){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(!isFragmentAdded){
            ft.add(R.id.container, fragment);
        } else {
            ft.replace(R.id.container, fragment).addToBackStack("abc");
        }
        ft.commit();
    }
}