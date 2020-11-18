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
import com.me.mseotsanyana.mande.BLL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cOutputRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cSessionManagerImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.Impl.cOutputPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iActivityPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iOutputPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cOutputAdapter;
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

public class cOutputFragment extends Fragment implements iOutputPresenter.View,
        iActivityPresenter.View{
    private static String TAG = cOutputFragment.class.getSimpleName();

    private Toolbar toolbar;
    private LinearLayout outputProgressBar;
    private cOutputAdapter outputAdapter;

    /* outcome interface */
    private iOutputPresenter outputPresenter;

    private long logFrameID;
    private TextView logFrameName;

    private AppCompatActivity activity;

    cOutputFragment(){

    }

    public static cOutputFragment newInstance(long logFrameID) {
        Bundle bundle = new Bundle();
        cOutputFragment fragment = new cOutputFragment();

        bundle.putLong("LOGFRAME_ID", logFrameID);
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
        /* get all outputs from the database */
        outputPresenter.resume();
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.output_list_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        init();
        outputView(view);
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
        collapsingToolbarLayout.setTitle("List of Outputs");

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
        List<cTreeModel> outputTreeModels = new ArrayList<>();

        outputPresenter = new cOutputPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSessionManagerImpl(getContext()),
                new cOutputRepositoryImpl(getContext()),
                logFrameID);

        // setup recycler view adapter
        outputAdapter = new cOutputAdapter(getActivity(), this,
                this, outputTreeModels, -1);

        activity = ((AppCompatActivity) getActivity());
    }

    private void outputView(View view) {
        outputProgressBar = view.findViewById(R.id.outputProgressBar);

        /* impact views */
        RecyclerView outputRecyclerView = view.findViewById(R.id.outputRecyclerView);
        outputRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        outputRecyclerView.setAdapter(outputAdapter);
        outputRecyclerView.setLayoutManager(llm);
    }

    // initialise the floating action button
    private void initFab(View view) {
        view.findViewById(R.id.outputFAB).setOnClickListener(new View.OnClickListener() {
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
    public void onClickBMBActivity(int menuIndex) {

    }

    @Override
    public void onClickCreateActivity(cActivityModel activityModel) {

    }

    @Override
    public void onClickUpdateActivity(cActivityModel activityModel, int position) {

    }

    @Override
    public void onClickDeleteActivity(long activityID, int position) {

    }

    @Override
    public void onClickSyncActivity(cActivityModel activityModel) {

    }

    @Override
    public void onActivityModelsRetrieved(String logFrameName, ArrayList<cTreeModel> activityModelSet) {

    }

    @Override
    public void onActivityModelsFailed(String msg) {

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
        try {
            /* update subtitle */
            this.logFrameName.setText(logFrameName);

            outputAdapter.setTreeModel(outputModelSet);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOutputModelsFailed(String msg) {

    }

    @Override
    public void showProgress() {
        outputProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        outputProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }
}
