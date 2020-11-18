package com.me.mseotsanyana.mande.PL.ui.fragments.raid;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentTransaction;

import com.me.mseotsanyana.mande.PL.ui.fragments.logframe.cLogFrameFragment;
import com.me.mseotsanyana.mande.R;

/**
 * Created by mseotsanyana on 2016/12/13.
 */

public class cRaidLogFragment extends Fragment {
    private static String TAG = cRaidLogFragment.class.getSimpleName();

    private Toolbar toolBar;

    cRaidLogFragment(){

    }

    public cRaidLogFragment newInstance() {
        cRaidLogFragment fragment = new cRaidLogFragment();
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
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.risk_list_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        initFab(view);

        // initialize the toolbar
        toolBar = view.findViewById(R.id.me_toolbar);
        toolBar.setTitle(R.string.risk_list_title);
        toolBar.setTitleTextColor(Color.WHITE);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolBar);
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

        toolBar.inflateMenu(R.menu.me_toolbar_menu);
        Menu toolBarMenu = toolBar.getMenu();

        //MenuItem toolBarMenuItem = toolBarMenu.findItem(R.id.homeItem);

        toolBar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        SearchManager searchManager = (SearchManager) requireActivity().
                getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) toolBarMenu.findItem(R.id.searchItem).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
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
}
