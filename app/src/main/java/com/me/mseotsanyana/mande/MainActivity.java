package com.me.mseotsanyana.mande;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

//import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cUserRepositoryImpl;
import com.me.mseotsanyana.mande.PL.ui.fragments.logframe.cLogFrameFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cJoinFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cLoginFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cRegisterFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cSettingsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting bottom navigation view and attaching the listener
        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        // initialise the dashboard fragment
        initFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    // initialise the login and profile fragments
    private void initFragment() {
        //session.logoutUser();
        //session.deleteSettings();
        //session.commitSettings();

        Fragment fragment;
        cUserRepositoryImpl session = null;
        if (false) {
            pushFragment(new cLogFrameFragment(), cLogFrameFragment.class.getSimpleName());
            //fragment = null;//new cLogFrameFragment().newInstance();
            //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //ft.replace(R.id.fragment_frame, fragment);
            //ft.commit();
        } else {
            pushFragment(new cLoginFragment(), cLoginFragment.class.getSimpleName());
            //fragment = new cLoginFragment();//.newInstance(session);
            //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //ft.replace(R.id.fragment_frame, fragment);
            //ft.commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.action_login:
                pushFragment(new cLoginFragment(), cLoginFragment.class.getSimpleName());//.newInstance(null));
                return true;
            case R.id.action_create:
                pushFragment(cRegisterFragment.newInstance(), cRegisterFragment.class.getSimpleName());
                return true;
            case R.id.action_join:
                pushFragment(cJoinFragment.newInstance(), cJoinFragment.class.getSimpleName());
                return true;
            case R.id.action_settings:
                pushFragment(cSettingsFragment.newInstance(), cSettingsFragment.class.getSimpleName());
                return true;
        }
        return false;
    }

    protected void pushFragment(Fragment fragment, String fragmentTag) {
        if (fragment == null)
            return;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (ft != null) {
            ft.replace(R.id.fragment_frame, fragment, fragmentTag);
            ft.commit();
        }
    }

    /* called when a home button is clicked 
    public void onClickHome3(View v) {
        Fragment dashboard = new cLogFrameFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, dashboard);
        ft.commit();
    }

    public boolean onClickHome(MenuItem menuItem) {
        Fragment dashboard = new cLogFrameFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, dashboard);
        ft.commit();
        return true;
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}