package com.me.mseotsanyana.mande;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();
    NavController navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getting navigation control with bottom menu and setting it
        navigationController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navigationController.navigateUp();
    }
}