package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cSessionManagerImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.awpb.cExpenseRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.awpb.cHumanRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.awpb.cIncomeRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.awpb.cMaterialRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.Impl.cInputPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iInputPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cInputViewPagerAdapter;
import com.me.mseotsanyana.mande.PL.ui.fragments.awpb.cExpenseFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.awpb.cHumanFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.awpb.cIncomeFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.awpb.cMaterialFragment;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewInputListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/13.
 */

public class cInputFragment extends Fragment implements iViewInputListener, iInputPresenter.View {
    private static String TAG = cInputFragment.class.getSimpleName();

    private Toolbar toolbar;
    private LinearLayout inputProgressBar;
    cInputViewPagerAdapter inputViewPagerAdapter;

    /* outcome interface */
    private iInputPresenter inputPresenter;

    private long logFrameID;

    private AppCompatActivity activity;

    private static final int HUMAN = 1;
    private static final int MATERIAL = 2;
    private static final int INCOME = 3;
    private static final int EXPENSE = 4;

    private ArrayList<cTreeModel> humanTreeModels;
    private ArrayList<cTreeModel> materialTreeModels;
    private ArrayList<cTreeModel> incomeTreeModels;
    private ArrayList<cTreeModel> expenseTreeModels;

    private cInputFragment() {

    }

    public static cInputFragment newInstance(long logFrameID) {
        Bundle bundle = new Bundle();
        cInputFragment fragment = new cInputFragment();

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
        init();
        inputView(view);
        //initFab(view);
        /* initialize the toolbar */
        toolbar = view.findViewById(R.id.toolbar);
        TextView logFrameCaption = view.findViewById(R.id.title);
        TextView logFrameName = view.findViewById(R.id.subtitle);
        logFrameCaption.setText(R.string.logframe_name_caption);
        //outcomeCaption.setText(R.string.logframe_name_caption);
        CollapsingToolbarLayout collapsingToolbarLayout =
                view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        collapsingToolbarLayout.setTitle("List of Inputs");

        /* show the back arrow button */
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

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
                    ContextCompat.getColor(getContext(), R.color.colorPrimary)
            );
            collapsingToolbarLayout.setStatusBarScrimColor(
                    ContextCompat.getColor(getContext(), R.color.colorPrimaryDark)
            );
        }

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
        List<cTreeModel> inputTreeModels = new ArrayList<>();

        humanTreeModels = new ArrayList<>();
        materialTreeModels = new ArrayList<>();
        incomeTreeModels = new ArrayList<>();
        expenseTreeModels = new ArrayList<>();

        inputPresenter = new cInputPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSessionManagerImpl(getContext()),
                new cHumanRepositoryImpl(getContext()),
                new cMaterialRepositoryImpl(getContext()),
                new cIncomeRepositoryImpl(getContext()),
                new cExpenseRepositoryImpl(getContext()),
                logFrameID);

        activity = ((AppCompatActivity) getActivity());
    }

    private void inputView(View view) {
        inputProgressBar = view.findViewById(R.id.inputProgressBar);
        //expandablePlaceHolderView = view.findViewById(R.id.inputPlaceholderView);

        /* setup the pager views */
        final ViewPager inputViewPager = view.findViewById(R.id.inputViewPager);
        setupViewPager(inputViewPager);
        /* tab layout view */
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

    private void setupViewPager(ViewPager viewPager) {
        inputViewPagerAdapter = new cInputViewPagerAdapter(getFragmentManager(),
               FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        inputViewPagerAdapter.addFrag(cHumanFragment.newInstance(humanTreeModels),"human");
        inputViewPagerAdapter.addFrag(cMaterialFragment.newInstance(materialTreeModels),"material");
        inputViewPagerAdapter.addFrag(cIncomeFragment.newInstance(incomeTreeModels),"income");
        inputViewPagerAdapter.addFrag(cExpenseFragment.newInstance(expenseTreeModels),"expense");

        // use a number higher than half your fragments.
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(inputViewPagerAdapter);
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

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).
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

    private Menu setToolBar() {
        toolbar.inflateMenu(R.menu.me_toolbar_menu);
        Menu toolBarMenu = toolbar.getMenu();

        MenuItem homeIcon = toolBarMenu.findItem(R.id.homeItem);
        TextDrawable faIcon = new TextDrawable(Objects.requireNonNull(getContext()));
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(cFontManager.getTypeface(getContext(), cFontManager.FONTAWESOME));
        faIcon.setText(getContext().getResources().getText(R.string.fa_home));
        faIcon.setTextColor(Color.WHITE);

        homeIcon.setIcon(faIcon);
        return toolBarMenu;
    }

    private void showFragment(String selectedFrag) {
        if (Objects.requireNonNull(getFragmentManager()).findFragmentByTag(selectedFrag) != null) {
            /* if the fragment exists, show it. */
            getFragmentManager().beginTransaction().show(
                    Objects.requireNonNull(getFragmentManager().findFragmentByTag(selectedFrag))).
                    commit();
        } else {
            /* if the fragment does not exist, add it to fragment manager. */
            getFragmentManager().beginTransaction().add(
                    R.id.fragment_frame, new cLogFrameFragment(), selectedFrag).commit();
        }
        if (getFragmentManager().findFragmentByTag(TAG) != null) {
            /* if the other fragment is visible, hide it. */
            getFragmentManager().beginTransaction().hide(
                    Objects.requireNonNull(getFragmentManager().findFragmentByTag(TAG))).commit();
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
    public void onInputModelsRetrieved(Map<Integer, ArrayList<cTreeModel>> inputModelSet) {

        for (Map.Entry<Integer, ArrayList<cTreeModel>> entry : inputModelSet.entrySet()) {
            /* human resources */
            if (entry.getKey() == HUMAN){
                cHumanFragment humanFrag;
                humanTreeModels = entry.getValue();
                humanFrag = (cHumanFragment) inputViewPagerAdapter.getItem(0);
                try {
                    humanFrag.getHumanAdapter().notifyTreeModelChanged(humanTreeModels);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            /* material & equipment resources */
            if (entry.getKey() == MATERIAL){
                cMaterialFragment materialFrag;
                materialTreeModels = entry.getValue();
                materialFrag = (cMaterialFragment) inputViewPagerAdapter.getItem(1);
                try {
                    materialFrag.getMaterialAdapter().notifyTreeModelChanged(materialTreeModels);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            /* income resources */
            if (entry.getKey() == INCOME){
                cIncomeFragment incomeFrag;
                incomeTreeModels = entry.getValue();
                incomeFrag = (cIncomeFragment) inputViewPagerAdapter.getItem(2);
                try {
                    incomeFrag.getIncomeAdapter().notifyTreeModelChanged(incomeTreeModels);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            /* budgeted expenses */
            if (entry.getKey() == EXPENSE){
                cExpenseFragment expenseFrag;
                expenseTreeModels = entry.getValue();
                expenseFrag = (cExpenseFragment) inputViewPagerAdapter.getItem(3);
                try {
                    expenseFrag.getExpenseAdapter().notifyTreeModelChanged(expenseTreeModels);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
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
