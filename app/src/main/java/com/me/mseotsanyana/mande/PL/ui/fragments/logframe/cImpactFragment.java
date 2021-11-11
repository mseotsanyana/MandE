package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.logframe.cImpactFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.Impl.cImpactPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iImpactPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iOutcomePresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cImpactAdapter;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cModuleViewPagerAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/04.
 */

public class cImpactFragment extends Fragment implements iImpactPresenter.View,
        iOutcomePresenter.View {
    private static String TAG = cImpactFragment.class.getSimpleName();

    private cModuleViewPagerAdapter moduleViewPagerAdapter;

    private Toolbar toolbar;

    private LinearLayout includeProgressBar;

    private cImpactAdapter impactAdapter;

    /* impact interface */
    private iImpactPresenter impactPresenter;

    private cLogFrameModel logFrameModel;
    //private TextView logFrameName;

    private AppCompatActivity activity;

    public cImpactFragment() {

    }

    public static cImpactFragment newInstance() {
        return new cImpactFragment();
    }

    /*
     * this event fires 1st, before creation of fragment or any views
     * the onAttach method is called when the Fragment instance is
     * associated with an Activity and this does not mean the activity
     * is fully initialized.
     */
    @Override
    public void onAttach(@NonNull Context context) {
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
        this.logFrameModel = cImpactFragmentArgs.fromBundle(requireArguments()).getLogFrameModel();
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all impacts from the database */
        impactPresenter.resume();
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.impact_list_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* initialise data structures */
        initDataStructures();

        /* initialize appBar Layout */
        initAppBarLayout(view);

        /* initialise recycler view */
        initRecyclerView(view);

        /* initialize progress bar */
        initProgressBarView(view);

        /* initialise draggable FAB */
        initDraggableFAB(view);

        /* show the back arrow button */
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    private void initDataStructures() {
        /* contains a tree of impact */
        List<cTreeModel> impactTreeModels = new ArrayList<>();
        //new cImpactFirestoreRepositoryImpl(getContext())


        impactPresenter = new cImpactPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(getContext()),
                new cImpactFirestoreRepositoryImpl(getContext()), logFrameModel);

        // setup recycler view adapter
        impactAdapter = new cImpactAdapter(getActivity(), this,
                this, impactTreeModels, -1);

        activity = ((AppCompatActivity) getActivity());
    }

    private void initAppBarLayout(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        //TextView logFrameCaption = view.findViewById(R.id.title);
        //logFrameName = view.findViewById(R.id.subtitle);
        //logFrameCaption.setText(R.string.logframe_name_caption);

        CollapsingToolbarLayout collapsingToolbarLayout =
                view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        collapsingToolbarLayout.setTitle("List of Impacts");
    }

    private void initRecyclerView(View view) {
        RecyclerView impactRecyclerView = view.findViewById(R.id.impactRecyclerView);
        impactRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        impactRecyclerView.setAdapter(impactAdapter);
        impactRecyclerView.setLayoutManager(llm);
    }

    private void initProgressBarView(View view) {
        includeProgressBar = view.findViewById(R.id.includeProgressBar);

        //impactProgressBar = view.findViewById(R.id.impactProgressBar);
    }

    // initialise the floating action button
    private void initDraggableFAB(View view) {
        view.findViewById(R.id.impactDraggableFAB).setOnClickListener(v -> {

        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        /* get inflated option menu */
        Menu toolBarMenu = setToolBar();

        toolbar.setOnMenuItemClickListener(item -> onOptionsItemSelected(item));

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
        if (item.getItemId() == R.id.uploadItem) {
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
                impactAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    private Menu setToolBar() {
        toolbar.inflateMenu(R.menu.me_toolbar_menu);
        return toolbar.getMenu();
    }

    @Override
    public void onRetrieveImpactsCompleted(String logFrameName, List<cTreeModel> impactModels) {
        try {
            /* update subtitle */
            //this.logFrameName.setText(logFrameName);
            impactAdapter.setTreeModel(impactModels);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImpactUpdateFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
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

    /* impact events */

    @Override
    public void onClickBMBImpact(int menuIndex) {

    }

    @Override
    public void onClickCreateImpact(cImpactModel impactModel) {

    }

    @Override
    public void onClickCreateSubImpact(long impactID, cImpactModel impactModel) {

    }

    @Override
    public void onClickUpdateImpact(int position, cImpactModel impactModel) {

    }

    @Override
    public void onClickDeleteImpact(int position, long impactID) {

    }

    @Override
    public void onClickDeleteSubImpact(int position, long impactID) {

    }

    @Override
    public void onClickSyncImpact(cImpactModel impactModel) {

    }

    @Override
    public void onClickDetailImpact(cOutcomeModel[] outcomeModels, cQuestionModel[] questionModels) {
        /* navigate from logframe to outcome */
//        NavDirections action = cImpactFragmentDirections.
//                actionCImpactFragmentToCImpactDetailFragment(outcomeModels, questionModels);
//        Navigation.findNavController(requireView()).navigate(action);
    }

    /* outcome events */

    @Override
    public void onClickBMBOutcome(int menuIndex) {

    }

    @Override
    public void onClickCreateOutcome(cOutcomeModel outcomeModel) {

    }

    @Override
    public void onClickUpdateOutcome(cOutcomeModel outcomeModel, int position) {

    }

    @Override
    public void onClickDeleteOutcome(long outcomeID, int position) {

    }

    @Override
    public void onClickSyncOutcome(cOutcomeModel outcomeModel) {

    }

    @Override
    public void onOutcomeModelsRetrieved(String logFrameName, ArrayList<cTreeModel> outcomeModelSet) {

    }

    @Override
    public void onOutcomeModelsFailed(String msg) {

    }
}
