package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.PL.presenters.session.iMenuPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cMenuAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

public class cMenuFragment extends Fragment implements iMenuPresenter.View{
    private static final String TAG = cMenuFragment.class.getSimpleName();

    private iMenuPresenter menuPresenter;

    private LinearLayout includeProgressBar;

    private cMenuAdapter menuAdapter;

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
        /* get all organizations from the database */
        //menuPresenter.resume();
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
    }

    private void initDataStructures() {
        List<cTreeModel> menuTree = new ArrayList<>();
        menuAdapter = new cMenuAdapter(getActivity(), menuTree);

//        menuPresenter = new cMenuPresenterImpl(
//                cThreadExecutorImpl.getInstance(),
//                cMainThreadImpl.getInstance(),
//                this,
//                new cSharedPreferenceFirestoreRepositoryImpl(getContext()),
//                new cMenuFirestoreRepositoryImpl(getContext()));
//
//
//        activity = ((AppCompatActivity) getActivity());
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
        view.findViewById(R.id.menuButton).setOnClickListener(view1 -> {

        });
    }

    // READ MENU ITEMS

    @Override
    public void onReadMenuFailed(String msg) {

    }

    @Override
    public void onReadMenuSucceeded(List<cTreeModel> treeModels) {
        try {
            menuAdapter.setTreeModel(treeModels);
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
}