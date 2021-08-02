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
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cModuleViewPagerAdapter;
import com.me.mseotsanyana.mande.R;

import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/04.
 */

public class cPermissionFragment extends Fragment {
    private static final String TAG = cPermissionFragment.class.getSimpleName();
    //private static final SimpleDateFormat tsdf = cConstant.TIMESTAMP_FORMAT_DATE;
    //private static final SimpleDateFormat ssdf = cConstant.SHORT_FORMAT_DATE;

    private Toolbar toolbar;

    private cModuleViewPagerAdapter moduleViewPagerAdapter;

    private AppCompatActivity activity;

    public cPermissionFragment(){

    }

    public static cPermissionFragment newInstance() {
        return new cPermissionFragment();
    }

    /*
    * this event fires 1st, before creation of fragment or any views
    * the onAttach method is called when the Fragment instance is
    * associated with an Activity and this does not mean the activity
    * is fully initialized.
    */
    @Override
    public void onAttach(@androidx.annotation.NonNull Context context) {
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
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.session_modules_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, Bundle savedInstanceState) {
        /* initialise data structures */
        initDataStructures();

        /* initialize appBar Layout */
        initAppBarLayout(view);

        /* initialise view pager */
        initViewPager(view);

        /* show the back arrow button */
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    private void initDataStructures() {
        /* items of modules */
        //entityModels = new ArrayList<>();
        //menuModels   = new ArrayList<>();

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
        ViewPager2 moduleViewPager2 = view.findViewById(R.id.moduleViewPager2);

        moduleViewPagerAdapter = new cModuleViewPagerAdapter(requireActivity());

        moduleViewPagerAdapter.addFrag(cEntityFragment.newInstance(), "entity permissions");
        moduleViewPagerAdapter.addFrag(cMenuFragment.newInstance(), "menu permissions");

        moduleViewPager2.setAdapter(moduleViewPagerAdapter);

        /* setup the tab layout and add tabs to the view pager2 */
        TabLayout moduleTabLayout = view.findViewById(R.id.moduleTabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(moduleTabLayout,
                moduleViewPager2, (tab, position) ->
                tab.setText(moduleViewPagerAdapter.getPageTitle(position)));
        tabLayoutMediator.attach();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        /* get inflated option menu */
        Menu toolBarMenu = setToolBar();

        toolbar.setOnMenuItemClickListener(
                this::onOptionsItemSelected);

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
