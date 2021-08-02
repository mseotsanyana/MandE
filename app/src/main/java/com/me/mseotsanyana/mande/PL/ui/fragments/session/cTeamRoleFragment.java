package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cTeamFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cTeamsWithRolesPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iTeamsWithRolesPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cTeamRoleAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cTeamRoleFragment extends Fragment implements iTeamsWithRolesPresenter.View,
        Filterable {
    //private static final String TAG = cTeamRoleFragment.class.getSimpleName();

    private iTeamsWithRolesPresenter teamsWithRolesPresenter;

    private LinearLayout includeProgressBar;
    private cTeamRoleAdapter teamRoleAdapter;

    private AppCompatActivity activity;

    public cTeamRoleFragment() {
    }

    public static cTeamRoleFragment newInstance() {
        return new cTeamRoleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all teams from the database */
        teamsWithRolesPresenter.resume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.session_team_roles_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* create data structures */
        initDataStructures();

        /* create RecyclerView */
        initTeamViews(view);

        /* initialize progress bar */
        initProgressBarView(view);

        /* initialise draggable FAB */
        initDraggableFAB(view);

        /* show the back arrow button */
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    private void initDataStructures() {
        List<cTreeModel> teamsRolesTree = new ArrayList<>();

        teamRoleAdapter = new cTeamRoleAdapter(getActivity(), teamsRolesTree);

        assert getArguments() != null;
        teamsWithRolesPresenter = new cTeamsWithRolesPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cTeamFirestoreRepositoryImpl(getContext()));

        activity = ((AppCompatActivity) getActivity());
    }

    private void initTeamViews(View view) {
        RecyclerView teamRecyclerView = view.findViewById(R.id.teamRoleRecyclerView);
        teamRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        teamRecyclerView.setAdapter(teamRoleAdapter);
        teamRecyclerView.setLayoutManager(llm);
        //teamPlaceholderView = view.findViewById(R.id.teamPlaceholderView);
    }

    private void initProgressBarView(View view) {
        includeProgressBar = view.findViewById(R.id.includeProgressBar);
    }

    private void initDraggableFAB(View view) {
        view.findViewById(R.id.teamRoleFAB).setOnClickListener(v -> {
            //onClickCreateTeam();
        });
    }

    // READ

    @Override
    public void onReadTeamsWithRolesFailed(String msg) {

    }

    @Override
    public void onReadTeamsWithRolesSucceeded(List<cTreeModel> teamsRolesTree) {
        try {
            teamRoleAdapter.setTreeModel(teamsRolesTree);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // CREATE

//    @Override
//    public void onClickCreateTeam() {
//        //createAlertDialog();
//    }

    @Override
    public void onCreateTeamFailed(String msg) {

    }

    @Override
    public void onCreateTeamSucceeded(String msg) {

    }

    // PRESENTER FUNCTIONS
    @Override
    public void showProgress() {
        includeProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        includeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Filter getFilter() {
        return null;
    }
}