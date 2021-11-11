package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cMenuFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cMenuPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iMenuPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cMenuAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cMenuFragment extends Fragment implements iMenuPresenter.View{
    private static final String TAG = cMenuFragment.class.getSimpleName();

    private Toolbar toolbar;

    private iMenuPresenter menuPresenter;

    private LinearLayout includeProgressBar;

    private cMenuAdapter menuAdapter;

    private AppCompatActivity activity;

    public cMenuFragment() {
    }

    public static cMenuFragment newInstance() {
        return new cMenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        menuPresenter.readMenuItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.session_menu_items_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* create data structures */
        initDataStructures();

        /* create RecyclerView */
        initMenuViews(view);

        /* apply button */
        initButton(view);

        /* show the back arrow button */
        toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    private void initDataStructures() {
        //List<cTreeModel> permissionTree = new ArrayList<>();

        menuAdapter = new cMenuAdapter(getActivity(), new ArrayList<>());

        menuPresenter = new cMenuPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cMenuFirestoreRepositoryImpl(getContext()));

        activity = ((AppCompatActivity) getActivity());
    }

    private void initMenuViews(View view) {
        includeProgressBar = view.findViewById(R.id.includeProgressBar);
        RecyclerView menuRecyclerView = view.findViewById(R.id.menuRecyclerView);

        menuRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        menuRecyclerView.setAdapter(menuAdapter);
        menuRecyclerView.setLayoutManager(llm);
    }

    private void initButton(View view) {
//        view.findViewById(R.id.menuButton).setOnClickListener(view1 -> {
//
//        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        /* get inflated option menu */
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



    // READ MENU ITEMS


    @Override
    public void onReadMenuFailed(String msg) {

    }

    @Override
    public void onReadMenuSucceeded(List<cTreeModel> treeModels) {
        Gson gson = new Gson();
        Log.d(TAG, "PERM MENU = "+gson.toJson(treeModels));
        try {
            menuAdapter.setTreeModel(treeModels);
            //menuAdapter.setMenuWithSubMenu(treeModels);
            //Log.d(TAG, gson.toJson(menuAdapter.getTreeModel()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // PRESENTER FUNCTIONS

    @Override
    public void showProgress() {
        includeProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        includeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    public cMenuAdapter getMenuAdapter() {
        return menuAdapter;
    }

//    public void setMenuAdapter(cMenuAdapter menuAdapter) {
//        this.menuAdapter = menuAdapter;
//    }
}