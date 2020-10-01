package com.me.mseotsanyana.mande.PL.ui.fragments.evaluator;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cEvaluationModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.evaluator.cEvaluationRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cSessionManagerImpl;
import com.me.mseotsanyana.mande.PL.presenters.evaluator.Impl.cEvaluationPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.evaluator.iEvaluationPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.evaluator.cEvaluationAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.questionnairelibrary.forms.db.cDBQuestionnaire;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/13.
 */

public class cEvaluationFragment extends Fragment implements iEvaluationPresenter.View{
    private static String TAG = cEvaluationFragment.class.getSimpleName();

    private Toolbar toolbar;
    private LinearLayout evaluationProgressBar;
    private cEvaluationAdapter evaluationAdapter;

    /* outcome interface */
    private iEvaluationPresenter evaluationPresenter;

    private long logFrameID;
    private TextView logFrameName;

    private AppCompatActivity activity;

    Gson gson = new Gson();
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
        this.logFrameID = cEvaluationFragmentArgs.fromBundle(requireArguments()).getLogFrameID();
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all evaluations from the database */
        evaluationPresenter.resume();
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.evaluation_list_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        init();
        evaluationView(view);
        initFab(view);
        /* initialize the toolbar */
        toolbar = view.findViewById(R.id.toolbar);
        TextView logFrameCaption = view.findViewById(R.id.title);
        logFrameName = view.findViewById(R.id.subtitle);
        logFrameCaption.setText(R.string.logframe_name_caption);
        //outcomeCaption.setText(R.string.logframe_name_caption);
        activity.setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout =
                view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        collapsingToolbarLayout.setTitle("List of Evaluations");

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
    }

    private void init() {
        // contains an evaluation tree
        List<cTreeModel> evaluationTreeModels = new ArrayList<>();

        // setup a presenter
        evaluationPresenter = new cEvaluationPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSessionManagerImpl(getContext()),
                new cEvaluationRepositoryImpl(getContext()),
                logFrameID);

        // setup recycler view adapter
        evaluationAdapter = new cEvaluationAdapter(getActivity(), this,
                evaluationTreeModels, -1);

        activity = ((AppCompatActivity) getActivity());
    }

    private void evaluationView(View view) {
        evaluationProgressBar = view.findViewById(R.id.evaluationProgressBar);

        /* impact views */
        RecyclerView evaluationRecyclerView = view.findViewById(R.id.evaluationRecyclerView);
        evaluationRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        evaluationRecyclerView.setAdapter(evaluationAdapter);
        evaluationRecyclerView.setLayoutManager(llm);
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
                getSearchableInfo(requireActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.homeItem) {
            /* navigate from evaluation to logframe */
            NavDirections action = cEvaluationFragmentDirections.
                    actionCEvaluationFragmentToCLogFrameFragment();
            Navigation.findNavController(requireView()).navigate(action);
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

    // initialise the floating action button
    private void initFab(View view) {
        view.findViewById(R.id.evaluationFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onEvaluationSelected(cEvaluationModel evaluationModel) {
        /* make a transition from evaluation to evaluation_form fragments */
        cDBQuestionnaire dbQuestionnaire = (cDBQuestionnaire) evaluationModel.getQuestionnaireObj();

        NavDirections action = cEvaluationFragmentDirections.
                actionCEvaluationFragmentToCEvaluationFormFragment(dbQuestionnaire);
        Navigation.findNavController(requireView()).navigate(action);

        //Gson gson = new Gson();
        //Log.d(TAG," cDBQuestionnaire = "+dbQuestionnaire);
    }

    @Override
    public void onEvaluationModelsRetrieved(String logFrameName,
                                            ArrayList<cTreeModel> evaluationModelSet) {
        try {
            /* update subtitle */
            this.logFrameName.setText(logFrameName);
            Log.d(TAG," SIZE =========================="+evaluationModelSet.size());
            evaluationAdapter.setTreeModel(evaluationModelSet);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEvaluationModelsFailed(String msg) {

    }

    @Override
    public void showProgress() {
        evaluationProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        evaluationProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

}
