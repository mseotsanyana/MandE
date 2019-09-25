package com.me.mseotsanyana.mande.PPMER.PL;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.me.mseotsanyana.mande.BRBAC.BLL.cOrganizationDomain;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

public class cDesktopActivity extends AppCompatActivity implements
        cMainFragment.OnGridViewItemSelectedListener {
    Fragment fragment;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // put an arrow button
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        fragment = new cMainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,fragment);
        ft.commit();
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

    @Override
    public void getGridPosition(int position) {

    }

    @Override
    public void getSelectedOrganizationList(ArrayList<cOrganizationDomain> selectedOrganizations) {

    }

    @Override
    public void getSelectedTreeModel(ArrayList<cTreeModel> selectedGoalTree) {

    }

    @Override
    public void getGroupMenuPosition(int parentPosition) {

    }

    @Override
    public void getChildMenuPosition(int childPosition) {

    }
}
