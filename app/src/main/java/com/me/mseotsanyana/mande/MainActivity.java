package com.me.mseotsanyana.mande;

//import androidx.core.app.Fragment;
//import androidx.core.app.FragmentTransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

//import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.me.mseotsanyana.mande.DAL.ìmpl.session.cUserRepositoryImpl;
import com.me.mseotsanyana.mande.PL.ui.fragments.logframe.cLogFrameFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cLoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        cUserRepositoryImpl session=null;
        if (false) {
            fragment = null;//new cLogFrameFragment().newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, fragment);
            ft.commit();
        } else {
            fragment = new cLoginFragment();//.newInstance(session);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, fragment);
            ft.commit();
        }
    }

    // called when a home button is clicked
    public void onClickHome(View v) {
        Fragment dashboard = new cLogFrameFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, dashboard);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}