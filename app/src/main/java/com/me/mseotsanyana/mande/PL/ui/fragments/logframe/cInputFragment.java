package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.BLL.model.wpb.cExpenseModel;
import com.me.mseotsanyana.mande.BLL.model.wpb.cHumanModel;
import com.me.mseotsanyana.mande.BLL.model.wpb.cIncomeModel;
import com.me.mseotsanyana.mande.BLL.model.wpb.cMaterialModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.awpb.cExpenseRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.awpb.cHumanRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.awpb.cIncomeRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.awpb.cMaterialRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.Impl.cInputPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iInputPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cInputViewPagerAdapter;
import com.me.mseotsanyana.mande.PL.ui.fragments.awpb.cExpenseFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.awpb.cHumanFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.awpb.cIncomeFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.awpb.cMaterialFragment;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewInputListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/13.
 */

public class cInputFragment extends Fragment implements iViewInputListener, iInputPresenter.View {
    private static String TAG = cInputFragment.class.getSimpleName();

    private Toolbar toolbar;
    private LinearLayout inputProgressBar;
    private ViewPager inputViewPager;
    private cInputViewPagerAdapter inputViewPagerAdapter;

    /* input interface */
    private iInputPresenter inputPresenter;

    private long logFrameID;
    private TextView logFrameName;

    private AppCompatActivity activity;

    private static final int HUMAN = 1;
    private static final int MATERIAL = 2;
    private static final int INCOME = 3;
    private static final int EXPENSE = 4;

    private ArrayList<cHumanModel> humanModels;
    private ArrayList<cMaterialModel> materialModels;
    private ArrayList<cIncomeModel> incomeModels;
    private ArrayList<cExpenseModel> expenseModels;

    private cHumanFragment humanFrag;
    private cMaterialFragment materialFrag;
    private cIncomeFragment incomeFrag;
    private cExpenseFragment expenseFrag;

    public cInputFragment() {

    }

    public static cInputFragment newInstance() {
        return new cInputFragment();
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

        //this.logFrameID = cInputFragmentArgs.fromBundle(requireArguments()).getLogFrameID();
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all outputs from the database */
        inputPresenter.resume();
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.input_list_fragment, parent, false);
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

        /* initialise view pager */
        initViewPager(view);

        /* initialize progress bar */
        initProgressBarView(view);

        /* show the back arrow button */
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }


    private void initDataStructures() {
        humanModels = new ArrayList<>();
        materialModels = new ArrayList<>();
        incomeModels = new ArrayList<>();
        expenseModels = new ArrayList<>();

        inputPresenter = new cInputPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this, null
                /*new cSessionManagerImpl(getContext())*/,
                new cHumanRepositoryImpl(getContext()),
                new cMaterialRepositoryImpl(getContext()),
                new cIncomeRepositoryImpl(getContext()),
                new cExpenseRepositoryImpl(getContext()),
                logFrameID);

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
        collapsingToolbarLayout.setTitle("List of Resources");

        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shepherds);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {

                    int vibrantColor = palette.getVibrantColor(R.color.colorPrimary);
                    int vibrantDarkColor = palette.getDarkVibrantColor(R.color.colorPrimaryDark);
                    collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                    collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
                }
            });

        } catch (Exception e) {
            // if Bitmap fetch fails, fallback to primary colors
            Log.e(TAG, "onCreate: failed to create bitmap from background", e.fillInStackTrace());
            collapsingToolbarLayout.setContentScrimColor(
                    ContextCompat.getColor(requireContext(), R.color.colorPrimary)
            );
            collapsingToolbarLayout.setStatusBarScrimColor(
                    ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
            );
        }
    }

    private void initViewPager(View view) {
        /* setup the pager views */
        inputViewPager = view.findViewById(R.id.inputViewPager);

        inputViewPagerAdapter = new cInputViewPagerAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        inputViewPagerAdapter.addFrag(cHumanFragment.newInstance(humanModels),"human");
        inputViewPagerAdapter.addFrag(cMaterialFragment.newInstance(materialModels),"material");
        inputViewPagerAdapter.addFrag(cIncomeFragment.newInstance(incomeModels),"income");
        inputViewPagerAdapter.addFrag(cExpenseFragment.newInstance(expenseModels),"expense");

        // use a number higher than half your fragments.
        inputViewPager.setOffscreenPageLimit(3);
        inputViewPager.setAdapter(inputViewPagerAdapter);

        /* setup the tab layout and add tabs to the view pager */
        TabLayout inputTabLayout = view.findViewById(R.id.inputTabLayout);
        inputTabLayout.setupWithViewPager(inputViewPager);

        inputTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                inputViewPager.setCurrentItem(tab.getPosition());
                Log.d(TAG, "onTabSelected: pos: " + tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        Log.d(TAG, "ONE: " + tab.getPosition());
                        break;
                    case 1:
                        Log.d(TAG, "TWO: " + tab.getPosition());
                        break;
                    case 2:
                        Log.d(TAG, "THREE: " + tab.getPosition());
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

    private void initProgressBarView(View view) {
        inputProgressBar = view.findViewById(R.id.inputProgressBar);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
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
        if (item.getItemId() == R.id.uploadItem) {
            Log.d(TAG, "Stub for displaying menu for input resources manual");
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
                humanFrag.getHumanAdapter().getFilter().filter(query);
                materialFrag.getMaterialAdapter().getFilter().filter(query);
                incomeFrag.getIncomeAdapter().getFilter().filter(query);
                expenseFrag.getExpenseAdapter().getFilter().filter(query);
                return false;
            }
        });
    }

    private Menu setToolBar() {
        toolbar.inflateMenu(R.menu.me_toolbar_menu);
        return toolbar.getMenu();
    }

    @Override
    public void onClickUpdateInput(int position, cInputModel inputModel) {

    }

    @Override
    public void onClickDeleteInput(int position, long outputID) {

    }

    @Override
    public void onClickSyncInput(int position, cInputModel inputModel) {

    }

    @Override
    public void onClickBMBInput(int menuIndex) {

    }

    @Override
    public void onClickCreateInput(cInputModel inputModel) {

    }

    @Override
    public void onClickUpdateInput(cInputModel inputModel, int position) {

    }

    @Override
    public void onClickDeleteInput(long outputID, int position) {

    }

    @Override
    public void onClickSyncInput(cInputModel inputModel) {

    }

    @Override
    public void onInputModelsRetrieved(ArrayList<cHumanModel> humanModels,
                                       ArrayList<cMaterialModel> materialModels,
                                       ArrayList<cIncomeModel> incomeModels,
                                       ArrayList<cExpenseModel> expenseModels) {

        this.humanModels = humanModels;
        this.materialModels = materialModels;
        this.incomeModels = incomeModels;
        this.expenseModels = expenseModels;

        humanFrag = (cHumanFragment) inputViewPagerAdapter.getItem(0);
        materialFrag = (cMaterialFragment) inputViewPagerAdapter.getItem(1);
        incomeFrag = (cIncomeFragment) inputViewPagerAdapter.getItem(2);
        expenseFrag = (cExpenseFragment) inputViewPagerAdapter.getItem(3);

        humanFrag.getHumanAdapter().setHumanModels(this.humanModels);
        humanFrag.getHumanAdapter().notifyDataSetChanged();

        materialFrag.getMaterialAdapter().setMaterialModels(this.materialModels);
        materialFrag.getMaterialAdapter().notifyDataSetChanged();

        incomeFrag.getIncomeAdapter().setIncomeModels(this.incomeModels);
        incomeFrag.getIncomeAdapter().notifyDataSetChanged();

        expenseFrag.getExpenseAdapter().setExpenseModels(this.expenseModels);
        expenseFrag.getExpenseAdapter().notifyDataSetChanged();

        //for (Map.Entry<Integer, ArrayList<Object>> entry : inputModelSet.entrySet()) {
            /* human resources
            if (entry.getKey() == HUMAN){
                cHumanFragment humanFrag;
                humanModels = (cHumanModel)entry.getValue();
                humanFrag = (cHumanFragment) inputViewPagerAdapter.getItem(0);
                humanFrag.getHumanAdapter().notifyDataSetChanged();
            }*/
            /* material & equipment resources
            if (entry.getKey() == MATERIAL){
                cMaterialFragment materialFrag;
                materialTreeModels = entry.getValue();
                materialFrag = (cMaterialFragment) inputViewPagerAdapter.getItem(1);
                try {
                    materialFrag.getMaterialAdapter().notifyTreeModelChanged(materialTreeModels);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }*/

            /* income resources
            if (entry.getKey() == INCOME){
                cIncomeFragment incomeFrag;
                incomeTreeModels = entry.getValue();
                incomeFrag = (cIncomeFragment) inputViewPagerAdapter.getItem(2);
                try {
                    incomeFrag.getIncomeAdapter().notifyTreeModelChanged(incomeTreeModels);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }*/

            /* budgeted expenses
            if (entry.getKey() == EXPENSE){
                cExpenseFragment expenseFrag;
                expenseTreeModels = entry.getValue();
                expenseFrag = (cExpenseFragment) inputViewPagerAdapter.getItem(3);
                try {
                    expenseFrag.getExpenseAdapter().notifyTreeModelChanged(expenseTreeModels);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }*/
        //}
    }

    @Override
    public void onInputModelsFailed(String msg) {

    }

    @Override
    public void showProgress() {
        inputProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        inputProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }
}
