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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.me.mseotsanyana.mande.BLL.model.session.cAgreementModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cOrganizationViewPagerAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/04.
 */

public class cOrganizationDetailFragment extends Fragment {
    private static String TAG = cOrganizationDetailFragment.class.getSimpleName();
    private static SimpleDateFormat tsdf = cConstant.TIMESTAMP_FORMAT_DATE;
    private static SimpleDateFormat ssdf = cConstant.SHORT_FORMAT_DATE;

    private Toolbar toolbar;
    private View organizationProgressBar;

    private ViewPager organizationViewPager;
    private cOrganizationViewPagerAdapter organizationViewPagerAdapter;


    private static final int ORGANIZATIONS = 0;
    private static final int AGREEMENTS    = 1;
    private ArrayList<cOrganizationModel> organizationModels;
    private ArrayList<cAgreementModel> agreementModels;

    //private cOrganizationAdapter organizationAdapter;
    //private long logFrameID;
    //private TextView logFrameName;

    private AppCompatActivity activity;

    public cOrganizationDetailFragment(){

    }

    public static cOrganizationDetailFragment newInstance() {
        return new cOrganizationDetailFragment();
    }

    public static cOrganizationDetailFragment newInstance(ArrayList<cOrganizationModel> domainList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ORGANIZATION", domainList);

        cOrganizationDetailFragment fragment = new cOrganizationDetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    /*
    * this event fires 1st, before creation of fragment or any views
    * the onAttach method is called when the Fragment instance is
    * associated with an Activity and this does not mean the activity
    * is fully initialized.
    */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        //this.logFrameID = cImpactFragmentArgs.fromBundle(requireArguments()).getLogFrameID();
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Register the event to subscribe.
        //-cGlobalBus.getBus().register(this);
        return inflater.inflate(R.layout.session_org_details_fragment, parent, false);
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
        initViewPager(view);

        /* initialize progress bar */
        //initProgressBarView(view);

        /* initialise draggable FAB */
        //initDraggableFAB(view);

        /* show the back arrow button */
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    private void initDataStructures() {
        /* contains a tree of impact */
        organizationModels = new ArrayList<>();
        agreementModels = new ArrayList<>();

        /*impactPresenter = new cImpactPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSessionManagerImpl(getContext()),
                new cImpactRepositoryImpl(getContext()), logFrameID);

        // setup recycler view adapter
        impactAdapter = new cImpactAdapter(getActivity(), this,
                this, impactTreeModels,-1);*/

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
        //collapsingToolbarLayout.setTitle("Organizations");
    }

    private void initViewPager(View view) {
        /* setup the pager views */
        organizationViewPager = view.findViewById(R.id.organizationViewPager);

        organizationViewPagerAdapter = new cOrganizationViewPagerAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        organizationViewPagerAdapter.addFrag(cOrganizationFragment.newInstance(organizationModels),"organizations");
        organizationViewPagerAdapter.addFrag(cAgreementFragment.newInstance(agreementModels),"agreements");

        // use a number higher than half your fragments.
        //--organizationViewPager.setOffscreenPageLimit(3);
        organizationViewPager.setAdapter(organizationViewPagerAdapter);

        /* setup the tab layout and add tabs to the view pager */
        TabLayout organizationTabLayout = view.findViewById(R.id.organizationTabLayout);
        organizationTabLayout.setupWithViewPager(organizationViewPager);

        organizationTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                organizationViewPager.setCurrentItem(tab.getPosition());
                Log.d(TAG, "onTabSelected: pos: " + tab.getPosition());

                switch (tab.getPosition()) {
                    case ORGANIZATIONS:
                        Log.d(TAG, "ONE: " + tab.getPosition());
                        break;
                    case AGREEMENTS:
                        Log.d(TAG, "TWO: " + tab.getPosition());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
/*
    private void initProgressBarView(View view) {
        organizationProgressBar = view.findViewById(R.id.progressBar);
    }
*/
    /* initialise the floating action button
    private void initDraggableFAB(View view) {
        view.findViewById(R.id.organizationDraggableFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }*/

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
}
