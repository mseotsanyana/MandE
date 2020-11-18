package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.TypedValue;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cOutcomeRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cSessionManagerImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.Impl.cOutcomePresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iOutcomePresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iOutputPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cOutcomeAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/04.
 */

public class cOutcomeFragment extends Fragment implements iOutcomePresenter.View,
        iOutputPresenter.View{
    private static String TAG = cOutcomeFragment.class.getSimpleName();

    private Toolbar toolbar;
    private LinearLayout outcomeProgressBar;
    private cOutcomeAdapter outcomeAdapter;

    /* outcome interface */
    private iOutcomePresenter outcomePresenter;

    private long logFrameID;
    private TextView logFrameName;

    private AppCompatActivity activity;

    public cOutcomeFragment(){

    }

    public static cOutcomeFragment newInstance(long logFrameID) {
        Bundle bundle = new Bundle();
        cOutcomeFragment fragment = new cOutcomeFragment();

        bundle.putLong("LOGFRAME_ID", logFrameID);
        fragment.setArguments(bundle);

        return fragment;
    }

    public cOutcomeFragment newInstance() {
        cOutcomeFragment fragment = new cOutcomeFragment();
        return fragment;
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

        this.logFrameID = requireArguments().getLong("LOGFRAME_ID");
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all outcomes from the database */
        outcomePresenter.resume();
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.outcome_list_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        init();
        outcomeView(view);
        initFab(view);
        /* initialize the toolbar */
        toolbar = view.findViewById(R.id.toolbar);
        TextView logFrameCaption = view.findViewById(R.id.title);
        logFrameName = view.findViewById(R.id.subtitle);
        logFrameCaption.setText(R.string.logframe_name_caption);
        //outcomeCaption.setText(R.string.logframe_name_caption);
        CollapsingToolbarLayout collapsingToolbarLayout =
                view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        collapsingToolbarLayout.setTitle("List of Outcomes");

        /* show the back arrow button */
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*initFab(view);

        // initialize the toolbar
        toolBar = view.findViewById(R.id.me_toolbar);
        toolBar.setTitle(R.string.outcome_list_title);
        toolBar.setTitleTextColor(Color.WHITE);*/

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
    }

    private void init() {
        /* contains a tree of impact */
        // setup recycler view adapter
        List<cTreeModel> outcomeTreeModels = new ArrayList<>();

        outcomePresenter = new cOutcomePresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSessionManagerImpl(getContext()),
                new cOutcomeRepositoryImpl(getContext()),
                logFrameID);

        // setup recycler view adapter
        outcomeAdapter = new cOutcomeAdapter(getActivity(), this,
                this, outcomeTreeModels, -1);

        activity = ((AppCompatActivity) getActivity());
    }

    private void outcomeView(View view) {
        outcomeProgressBar = view.findViewById(R.id.outcomeProgressBar);

        /* impact views */
        RecyclerView outcomeRecyclerView = view.findViewById(R.id.outcomeRecyclerView);
        outcomeRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        outcomeRecyclerView.setAdapter(outcomeAdapter);
        outcomeRecyclerView.setLayoutManager(llm);
    }

    // initialise the floating action button
    private void initFab(View view) {
        view.findViewById(R.id.outcomeFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        Menu toolBarMenu = setToolBar();

        //MenuItem toolBarMenuItem = toolBarMenu.findItem(R.id.homeItem);

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
                getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeItem:
                showFragment(cLogFrameFragment.class.getSimpleName());
                break;
            default:
                break;
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
                //userAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    private Menu setToolBar(){
        toolbar.inflateMenu(R.menu.me_toolbar_menu);
        Menu toolBarMenu = toolbar.getMenu();

        MenuItem homeIcon = toolBarMenu.findItem(R.id.homeItem);
        TextDrawable faIcon = new TextDrawable(requireContext());
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(cFontManager.getTypeface(getContext(), cFontManager.FONTAWESOME));
        faIcon.setText(getContext().getResources().getText(R.string.fa_home));
        faIcon.setTextColor(Color.WHITE);

        homeIcon.setIcon(faIcon);
        return toolBarMenu;
    }

    private void showFragment(String selectedFrag){
        if (requireFragmentManager().findFragmentByTag(selectedFrag) != null) {
            /* if the fragment exists, show it. */
            getFragmentManager().beginTransaction().show(
                    requireFragmentManager().findFragmentByTag(selectedFrag)).
                    commit();
        } else {
            /* if the fragment does not exist, add it to fragment manager. */
            getFragmentManager().beginTransaction().add(
                    R.id.fragment_frame, new cLogFrameFragment(), selectedFrag).commit();
        }
        if (getFragmentManager().findFragmentByTag(TAG) != null) {
            /* if the other fragment is visible, hide it. */
            getFragmentManager().beginTransaction().hide(
                    requireFragmentManager().findFragmentByTag(TAG)).commit();
        }
    }

    protected void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        assert getFragmentManager() != null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    @Override
    public void onOutcomeModelsRetrieved(String logFrameName, ArrayList<cTreeModel> outcomeModelSet) {
        try {
            /* update subtitle */
            this.logFrameName.setText(logFrameName);

            outcomeAdapter.setTreeModel(outcomeModelSet);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOutcomeModelsFailed(String msg) {

    }


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
    public void onClickBMBOutput(int menuIndex) {

    }

    @Override
    public void onClickCreateOutput(cOutputModel outputModel) {

    }

    @Override
    public void onClickUpdateOutput(cOutputModel outputModel, int position) {

    }

    @Override
    public void onClickDeleteOutput(long outputID, int position) {

    }

    @Override
    public void onClickSyncOutput(cOutputModel outputModel) {

    }

    @Override
    public void onOutputModelsRetrieved(String logFrameName, ArrayList<cTreeModel> outputModelSet) {

    }

    @Override
    public void onOutputModelsFailed(String msg) {

    }

    @Override
    public void showProgress() {
        outcomeProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        outcomeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }
}
