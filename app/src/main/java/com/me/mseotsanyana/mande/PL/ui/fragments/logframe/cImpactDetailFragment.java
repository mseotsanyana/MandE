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
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cImpactViewPagerAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/13.
 */

public class cImpactDetailFragment extends Fragment {
    private static String TAG = cImpactDetailFragment.class.getSimpleName();

    private Toolbar toolbar;
    private LinearLayout impactDetailProgressBar;
    cImpactViewPagerAdapter impactDetailViewPagerAdapter;

    /* outcome interface */
    // private iInputPresenter inputPresenter;

    private AppCompatActivity activity;

    private cOutcomeModel[] outcomeModels;
    private cQuestionModel[] questionModels;
    private ArrayList<cRaidModel> raidModels;

    public cImpactDetailFragment() {

    }

    public static cImpactDetailFragment newInstance() {
        cImpactDetailFragment fragment = new cImpactDetailFragment();
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

        this.outcomeModels = cImpactDetailFragmentArgs.fromBundle(
                requireArguments()).getOutcomeModels();
        this.questionModels = cImpactDetailFragmentArgs.fromBundle(
                requireArguments()).getQuestionModels();
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all outputs from the database */
        //inputPresenter.resume();
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.impact_detail_list_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        init();
        impactDetailView(view);
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
        collapsingToolbarLayout.setTitle("List of Impact Details");

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
        /* initialise tab models */
        activity = ((AppCompatActivity) getActivity());
    }

    private void impactDetailView(View view) {
        //inputProgressBar = view.findViewById(R.id.impactDetailProgressBar);
        //expandablePlaceHolderView = view.findViewById(R.id.inputPlaceholderView);

        /* setup the pager views */
        final ViewPager impactDetailViewPager = view.findViewById(R.id.impactDetailViewPager);
        setupViewPager(impactDetailViewPager);
        /* tab layout view */
        TabLayout impactDetailTabLayout = view.findViewById(R.id.impactDetailTabLayout);
        impactDetailTabLayout.setupWithViewPager(impactDetailViewPager);

        impactDetailTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                impactDetailViewPager.setCurrentItem(tab.getPosition());
                Log.d(TAG, "onTabSelected: pos: " + tab.getPosition());
                cOutcomeAuxFragment outcomeAuxFragment;
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
        impactDetailViewPagerAdapter = new cImpactViewPagerAdapter(getChildFragmentManager(),
               FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        impactDetailViewPagerAdapter.addFrag(
                cOutcomeAuxFragment.newInstance(outcomeModels),"outcomes");
        impactDetailViewPagerAdapter.addFrag(
                cQuestionAuxFragment.newInstance(questionModels),"questions");
        impactDetailViewPagerAdapter.addFrag(
                cOutcomeAuxFragment.newInstance(outcomeModels),"raid");

        // use a number higher than half your fragments.
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(impactDetailViewPagerAdapter);
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
        switch (item.getItemId()) {
            case R.id.helpItem:
                //showFragment(cLogFrameFragment.class.getSimpleName());
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

        /*MenuItem homeIcon = toolBarMenu.findItem(R.id.helpItem);
        TextDrawable faIcon = new TextDrawable(requireContext());
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(cFontManager.getTypeface(requireContext(), cFontManager.FONTAWESOME));
        faIcon.setText(requireContext().getResources().getText(R.string.fa_home));
        faIcon.setTextColor(Color.WHITE);

        homeIcon.setIcon(faIcon);*/
        return toolbar.getMenu();
    }
}
