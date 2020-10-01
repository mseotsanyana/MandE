package com.me.mseotsanyana.mande;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{
    private static String TAG = MainActivity.class.getSimpleName();
    NavController navigationController;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getting bottom navigation view and attaching the listener
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // getting navigation control with bottom menu and setting it
        navigationController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navigationController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navigationController.navigateUp();
    }
}