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

import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cEntityPermissionAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

public class cEntityFragment extends Fragment {
    private static String TAG = cEntityFragment.class.getSimpleName();

    //private iEntityPermissionPresenter entityPermissionPresenter;

    private LinearLayout includeProgressBar;

    private List<cEntityModel> entityModels;
    private List<cTreeModel> entityOperationTree;
    private cEntityPermissionAdapter entityPermissionAdapter;

    public cEntityFragment() {
    }

    public static cEntityFragment newInstance() {
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("ENTITY_MODELS", entityModels);
//        cEntityFragment fragment = new cEntityFragment();
//        fragment.setArguments(bundle);

        return new cEntityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all organizations from the database */
        //menuPresenter.readEntitys();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.session_organizations_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* create data structures */
        //initDataStructures();

        /* create RecyclerView */
        initEntityViews(view);

        /* draggable floating button */
        initDraggableFAB(view);
    }

    private void initDataStructures() {
        assert getArguments() != null;
//        entityModels = null;//getArguments().getParcelableArrayList("ENTITY_MODELS");
//        entityPermissionAdapter = new cEntityPermissionAdapter(getActivity(), entityModels);

//        entityPresenter = new cEntityPresenterImpl(
//                cThreadExecutorImpl.getInstance(),
//                cMainThreadImpl.getInstance(),
//                this,
//                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
//                new cEntityFirestoreRepositoryImpl(getContext()));
//
//
//        activity = ((AppCompatActivity) getActivity());
    }

    private void initEntityViews(View view) {
        includeProgressBar = view.findViewById(R.id.includeProgressBar);
        RecyclerView orgRecyclerView = view.findViewById(R.id.orgRecyclerView);

        orgRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        //orgRecyclerView.setAdapter(entityAdapter);
        orgRecyclerView.setLayoutManager(llm);
    }

    private void initDraggableFAB(View view) {
        view.findViewById(R.id.organizationFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onClickCreateEntity();
            }
        });
    }

}