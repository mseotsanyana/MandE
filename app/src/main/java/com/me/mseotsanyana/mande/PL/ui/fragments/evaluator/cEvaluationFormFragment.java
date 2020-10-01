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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.questionnairelibrary.forms.cPageIndicator;
import com.me.mseotsanyana.questionnairelibrary.forms.cViewPageAdapter;
import com.me.mseotsanyana.questionnairelibrary.forms.db.cDBQuestionnaire;
import com.me.mseotsanyana.questionnairelibrary.forms.db.cDBViewPager;

import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/13.
 */

public class cEvaluationFormFragment extends Fragment {
    private static String TAG = cEvaluationFormFragment.class.getSimpleName();

    private Toolbar toolbar;
    private cDBQuestionnaire dbQuestionnaire;

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
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.evaluation_form_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /* display the questionnaire form */
        evaluationFormView(view);
        /* initialize the toolbar */
        toolbar = view.findViewById(R.id.toolbar);
        /* set the toolbar title */
        if(dbQuestionnaire != null) {
            toolbar.setTitle(dbQuestionnaire.getFormName());
        }else {
            toolbar.setTitle("Evaluation Form");
        }
        toolbar.setTitleTextColor(Color.WHITE);

        /* set a back button and listener */
        toolbar.setNavigationIcon(R.drawable.me_ic_arrow_back_36);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Testiiiiing");
                // Your code
                //finish();
            }
        });

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
    }

    private void evaluationFormView(View view) {
        /* 0. retrieve the evaluation data */
        dbQuestionnaire = cEvaluationFormFragmentArgs.fromBundle(requireArguments()).
                getDbQuestionnaire();

        //Log.d(TAG," cDBQuestionnaire Groups = "+dbQuestionnaire.getQuestionGroups());
        /* 1. create a view pager to hold the views */
        cDBViewPager dbViewPager = (cDBViewPager) view.findViewById(R.id.evaluationDBViewPager);

        /* 2. get the questionnaire data and set it to the view pager to generate views */
        dbViewPager.setDBQuestionnaire(dbQuestionnaire);

        /* 3. create a view pager adapter, pass list of views generated from step 2 above and
         *    set the adapter to the view pager */
        cViewPageAdapter viewPageAdapter = new cViewPageAdapter(getContext(),
                dbViewPager.getListViews());

        dbViewPager.setAdapter(viewPageAdapter);

        /* 4. create page indicator set or attach the view pager created in step 1 */
        cPageIndicator pageIndicator = requireActivity().findViewById(R.id.pageIndicator);
        pageIndicator.setStartButtonText("Start");
        pageIndicator.setPreviousButtonText("<<");
        pageIndicator.setNextButtonText(">>");
        pageIndicator.setEndButtonText("End");
        pageIndicator.setViewPager(dbViewPager);
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
            /* navigate from evaluation to logframe
            NavDirections action = cEvaluationFragmentDirections.
                    actionCEvaluationFragmentToCLogFrameFragment();
            Navigation.findNavController(requireView()).navigate(action);*/
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

        faIcon.setTypeface(cFontManager.getTypeface(requireContext(), cFontManager.FONTAWESOME));
        faIcon.setText(requireContext().getResources().getText(R.string.fa_home));
        faIcon.setTextColor(Color.WHITE);

        homeIcon.setIcon(faIcon);
        return toolBarMenu;
    }
}
