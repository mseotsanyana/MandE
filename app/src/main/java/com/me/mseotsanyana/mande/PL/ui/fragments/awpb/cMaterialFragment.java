package com.me.mseotsanyana.mande.PL.ui.fragments.awpb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.wpb.cMaterialModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.awpb.cMaterialAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

public class cMaterialFragment extends Fragment {
    private static String TAG = cMaterialFragment.class.getSimpleName();

    private cMaterialAdapter materialAdapter;

    public cMaterialFragment() {
    }

    public static cMaterialFragment newInstance(ArrayList<cMaterialModel> materialModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("MATERIAL_MODELS", materialModels);
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
        ArrayList<cMaterialModel> materialModels = getArguments().getParcelableArrayList(
                "MATERIAL_MODELS");

        RecyclerView inputRecyclerView = view.findViewById(R.id.resourcesRecyclerView);
        inputRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        materialAdapter = new cMaterialAdapter(getActivity(), null,
                materialModels);
        inputRecyclerView.setAdapter(materialAdapter);
        inputRecyclerView.setLayoutManager(llm);
    }

    public cMaterialAdapter getMaterialAdapter() {
        return materialAdapter;
    }
}