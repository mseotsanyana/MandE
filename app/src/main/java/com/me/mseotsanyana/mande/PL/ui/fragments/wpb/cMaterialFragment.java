package com.me.mseotsanyana.mande.PL.ui.fragments.wpb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.PL.ui.adapters.awpb.cMaterialAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

public class cMaterialFragment extends Fragment {
    private static String TAG = cMaterialFragment.class.getSimpleName();

    private cMaterialAdapter materialAdapter;

    public cMaterialFragment() {
    }

    public static cMaterialFragment newInstance(ArrayList<cTreeModel> materialTreeModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("MATERIAL_TREE_MODELS", materialTreeModels);
        cMaterialFragment fragment = new cMaterialFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.resources_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        assert getArguments() != null;
        ArrayList<cTreeModel> materialTreeModels = getArguments().getParcelableArrayList(
                "MATERIAL_TREE_MODELS");

        RecyclerView inputRecyclerView = view.findViewById(R.id.resourcesRecyclerView);
        inputRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        materialAdapter = new cMaterialAdapter(getActivity(), null,
                materialTreeModels, -1);
        inputRecyclerView.setAdapter(materialAdapter);
        inputRecyclerView.setLayoutManager(llm);
    }

    public cMaterialAdapter getMaterialAdapter() {
        return materialAdapter;
    }
}