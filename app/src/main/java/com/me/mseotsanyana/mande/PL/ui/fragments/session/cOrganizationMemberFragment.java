package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cOrganizationFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cOrganizationMemberPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iOrganizationMemberPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cOrganizationMemberAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/04.
 */

public class cOrganizationMemberFragment extends Fragment implements iOrganizationMemberPresenter.View {
    private static final String TAG = cOrganizationMemberFragment.class.getSimpleName();

    private iOrganizationMemberPresenter organizationMemberPresenter;

    private Toolbar toolbar;
    private LinearLayout includeProgressBar;

    private cOrganizationMemberAdapter organizationMemberAdapter;

    private AppCompatActivity activity;

    public cOrganizationMemberFragment(){

    }

    public static cOrganizationMemberFragment newInstance() {
        return new cOrganizationMemberFragment();
    }

    /**
     * this method is fired 2nd, before views are created for the fragment,
     * the onCreate method is called when the fragment instance is being created,
     * or re-created use onCreate for any standard setup that does not require
     * the activity to be fully created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        organizationMemberPresenter.resume();
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.session_org_members_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /* initialise data structures */
        initDataStructures();

        /* initialize appBar Layout */
        initAppBarLayout(view);

        /* initialise view pager */
        initRecyclerView(view);

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
        List<cUserProfileModel> userProfileModels = new ArrayList<>();

        organizationMemberAdapter = new cOrganizationMemberAdapter(getActivity(), userProfileModels);

        organizationMemberPresenter = new cOrganizationMemberPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(getContext()),
                new cOrganizationFirestoreRepositoryImpl(getContext()));

        activity = ((AppCompatActivity) getActivity());

    }

    private void initAppBarLayout(View view){
        toolbar = view.findViewById(R.id.toolbar);
        TextView appName = view.findViewById(R.id.appName);
        //logFrameName = view.findViewById(R.id.subtitle);
        appName.setText(R.string.app_name);
        CollapsingToolbarLayout collapsingToolbarLayout =
                view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        collapsingToolbarLayout.setTitle("Organizations");
    }

    private void initRecyclerView(View view) {

        RecyclerView organizationMemberRecyclerView;
        organizationMemberRecyclerView = view.findViewById(R.id.organizationMemberRecyclerView);

        organizationMemberRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        organizationMemberRecyclerView.setAdapter(organizationMemberAdapter);
        organizationMemberRecyclerView.setLayoutManager(llm);
    }

    private void initProgressBarView(View view) {
        includeProgressBar = view.findViewById(R.id.includeProgressBar);
    }

    /* initialise the floating action button */
    private void initDraggableFAB(View view) {
        view.findViewById(R.id.organizationMemberFAB).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        /* get inflated option menu */
        Menu toolBarMenu = setToolBar();

        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        SearchManager searchManager = (SearchManager) requireActivity().
                getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) toolBarMenu.findItem(R.id.searchItem).getActionView();
        searchView.setSearchableInfo(Objects.requireNonNull(searchManager).
                getSearchableInfo(requireActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.helpItem) {
            Log.d(TAG, "Stub for information button");
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //organizationAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    private Menu setToolBar(){
        toolbar.inflateMenu(R.menu.me_toolbar_menu);
        return toolbar.getMenu();
    }

    @Override
    public void onReadOrganizationMembersFailed(String msg) {

    }

    @Override
    public void onReadOrganizationMembersSucceeded(List<cUserProfileModel> userProfileModels) {
        this.organizationMemberAdapter.setOrganizationMemberModels(userProfileModels);
        this.organizationMemberAdapter.notifyDataSetChanged();
    }

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
}
