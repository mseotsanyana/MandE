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
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cTeamsWithMembersPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iTeamsWithMembersPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cTeamMemberAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cTeamMemberFragment extends Fragment implements iTeamsWithMembersPresenter.View,
        Filterable {
    private static String TAG = cTeamMemberFragment.class.getSimpleName();

    private iTeamsWithMembersPresenter teamPresenter;

    private LinearLayout includeProgressBar;
    private cTeamMemberAdapter teamAdapter;

    private AppCompatActivity activity;

    public cTeamMemberFragment() {
    }

    public static cTeamMemberFragment newInstance() {
        return new cTeamMemberFragment();
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
        teamPresenter.resume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.session_teams_fragment, container, false);
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
        List<cTreeModel> teamsMembersTree = new ArrayList<>();

        teamAdapter = new cTeamMemberAdapter(getActivity(), teamsMembersTree);

        assert getArguments() != null;
        teamPresenter = new cTeamsWithMembersPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(getContext()),
                new cTeamFirestoreRepositoryImpl(getContext()));

        activity = ((AppCompatActivity) getActivity());
    }

    private void initTeamViews(View view) {

        RecyclerView teamRecyclerView = view.findViewById(R.id.teamRecyclerView);
        teamRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        teamRecyclerView.setAdapter(teamAdapter);
        teamRecyclerView.setLayoutManager(llm);
        //teamPlaceholderView = view.findViewById(R.id.teamPlaceholderView);
    }

    private void initProgressBarView(View view) {
        includeProgressBar = view.findViewById(R.id.includeProgressBar);
    }

    private void initDraggableFAB(View view) {
        view.findViewById(R.id.teamFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCreateTeam();
            }
        });
    }

    // READ

    @Override
    public void onReadTeamsWithMembersFailed(String msg) {

    }

    @Override
    public void onReadTeamsWithMembersSucceeded(List<cTreeModel> teamsMembersTree) {
        try {
            teamAdapter.setTreeModel(teamsMembersTree);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // CREATE

    @Override
    public void onClickCreateTeam() {
        //createAlertDialog();
    }

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