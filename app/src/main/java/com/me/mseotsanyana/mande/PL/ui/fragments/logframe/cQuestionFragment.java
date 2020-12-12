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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.logframe.cArrayQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cMatrixQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cPrimitiveQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cQuestionRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cSessionManagerImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.Impl.cQuestionPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iQuestionPresenter;
import com.me.mseotsanyana.mande.PL.ui.views.cLogFrameHeaderView;
import com.me.mseotsanyana.mande.PL.ui.views.cQuestionBodyView;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandablePlaceHolderView;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by mseotsanyana on 2016/12/13.
 */

public class cQuestionFragment extends Fragment implements iQuestionPresenter.View {
    private static String TAG = cQuestionFragment.class.getSimpleName();

    private Toolbar toolbar;
    private LinearLayout questionProgressBar;
    //private cQuestionAdapter questionAdapter;
    cExpandablePlaceHolderView questionPlaceholderView;
    /* output interface */
    private iQuestionPresenter questionPresenter;

    private long logFrameID;
    private TextView logFrameName;
    private ArrayList<cQuestionModel> questionModels;

    private AppCompatActivity activity;

    public cQuestionFragment(){

    }

    public cQuestionFragment newInstance() {
        return new cQuestionFragment();
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

        this.logFrameID = cQuestionFragmentArgs.fromBundle(requireArguments()).getLogFrameID();
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all outputs from the database */
        questionPresenter.resume();
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.question_list_fragment, parent, false);
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

        /* initialise placeholder view */
        initPlaceholderView(view);

        /* initialize progress bar */
        initProgressBarView(view);

        /* show the back arrow button */
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    private void initDataStructures() {
        /* contains a tree of output */
        questionModels = new ArrayList<>();

        questionPresenter = new cQuestionPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSessionManagerImpl(getContext()),
                new cQuestionRepositoryImpl(getContext()),
                logFrameID);

        // setup recycler view adapter
        //questionAdapter = new cQuestionAdapter(getActivity(), questionModels);

        activity = ((AppCompatActivity) getActivity());
    }

    private void initAppBarLayout(View view){
        /* initialize the toolbar */
        toolbar = view.findViewById(R.id.toolbar);
        TextView logFrameCaption = view.findViewById(R.id.title);
        logFrameName = view.findViewById(R.id.subtitle);
        logFrameCaption.setText(R.string.logframe_name_caption);
        CollapsingToolbarLayout collapsingToolbarLayout =
                view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        collapsingToolbarLayout.setTitle("List of Questions");
    }

    private void initPlaceholderView(View view) {
        questionPlaceholderView = view.findViewById(R.id.questionPlaceholderView);
    }

    private void initProgressBarView(View view) {
        questionProgressBar = view.findViewById(R.id.questionProgressBar);
    }

    // initialise the floating action button
    private void initFab(View view) {
        view.findViewById(R.id.impactDraggableFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        toolbar.inflateMenu(R.menu.me_toolbar_menu);
        Menu toolBarMenu = toolbar.getMenu();

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
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.helpItem) {
            Log.d(TAG,"Stub for component questions");
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

    @Override
    public void onClickDetailQuestion(cQuestionModel questionModel) {

    }

    @Override
    public void onClickUpdateQuestion(cQuestionModel questionModel, int position) {

    }

    @Override
    public void onClickDeleteQuestion(long questionID, int position) {

    }

    @Override
    public void onClickSyncQuestion(cQuestionModel questionModel) {

    }

    @Override
    public void onQuestionModelsRetrieved(String logFrameName,
                                          ArrayList<cQuestionModel> questionModels) {

        this.logFrameName.setText(logFrameName);
        this.questionModels = questionModels;

        /* update the placeholder view */
        updatePlaceholderView(this.questionModels);

        //this.questionAdapter.notifyDataSetChanged();

        Gson gson = new Gson();
        Log.d(TAG, gson.toJson(questionModels.size()));
        Log.d(TAG, gson.toJson(questionModels));
    }

    @Override
    public void onQuestionModelsFailed(String msg) {

    }

    @Override
    public void showProgress() {
        questionProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        questionProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    private void updatePlaceholderView(ArrayList<cQuestionModel> questionModels){
        Gson gson = new Gson();
        /* clear the placeholder */
        questionPlaceholderView.removeAllViews();

        boolean index = true;
        for (int i = 0; i < questionModels.size(); i++) {
            if (questionModels.get(i) instanceof cPrimitiveQuestionModel) {
                if (index) {
                    questionPlaceholderView.addView(new cLogFrameHeaderView(
                            getContext(), "Primitive Key Performance Questions"));
                    questionPlaceholderView.addView(new cQuestionBodyView(
                            getContext(), (cQuestionModel) questionModels.get(i)));
                    Log.d(TAG, "1. PRIMITIVE QUESTION: " + questionModels.get(i));
                    index = false;
                } else {
                    questionPlaceholderView.addView(new cQuestionBodyView(
                            getContext(), (cQuestionModel) questionModels.get(i)));
                    Log.d(TAG, "2. PRIMITIVE QUESTION: " + questionModels.get(i));
                }
            }
        }

        index = true;
        for (int i = 0; i < questionModels.size(); i++) {
            if (questionModels.get(i) instanceof cArrayQuestionModel) {
                if (index) {
                    questionPlaceholderView.addView(new cLogFrameHeaderView(
                            getContext(), "Array Key Performance Questions"));
                    questionPlaceholderView.addView(new cQuestionBodyView(
                            getContext(), (cQuestionModel) questionModels.get(i)));
                    Log.d(TAG, "1. ARRAY QUESTION: " + questionModels.get(i));
                    index = false;
                } else {
                    questionPlaceholderView.addView(new cQuestionBodyView(
                            getContext(), (cQuestionModel) questionModels.get(i)));
                    Log.d(TAG, "2. ARRAY QUESTION: " + questionModels.get(i));
                }
            }
        }

        index = true;
        for (int i = 0; i < questionModels.size(); i++) {
            if (questionModels.get(i) instanceof cMatrixQuestionModel) {
                if (index) {
                    questionPlaceholderView.addView(new cLogFrameHeaderView(
                            getContext(), "Matrix Key Performance Questions"));
                    questionPlaceholderView.addView(new cQuestionBodyView(
                            getContext(), (cQuestionModel) questionModels.get(i)));
                    Log.d(TAG, "1. MATRIX QUESTION: " + questionModels.get(i));
                    index = false;
                } else {
                    questionPlaceholderView.addView(new cQuestionBodyView(
                            getContext(), (cQuestionModel) questionModels.get(i)));
                    Log.d(TAG, "2. MATRIX QUESTION: " + questionModels.get(i));
                }
            }
        }
    }
}
