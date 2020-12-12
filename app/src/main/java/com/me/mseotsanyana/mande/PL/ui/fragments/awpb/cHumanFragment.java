package com.me.mseotsanyana.mande.PL.ui.fragments.awpb;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.mseotsanyana.mande.BLL.model.wpb.cHumanModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.awpb.cHumanAdapter;
import com.me.mseotsanyana.mande.R;

import java.util.ArrayList;

public class cHumanFragment extends Fragment {
    private static String TAG = cHumanFragment.class.getSimpleName();

    ArrayList<cHumanModel> humanModels;
    private cHumanAdapter humanAdapter;

    public cHumanFragment() {
    }

    public static cHumanFragment newInstance(ArrayList<cHumanModel> humanModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("HUMAN_MODELS", humanModels);
        cHumanFragment fragment = new cHumanFragment();
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
        humanModels = getArguments().getParcelableArrayList(
                "HUMAN_MODELS");

        RecyclerView inputRecyclerView = view.findViewById(R.id.resourcesRecyclerView);
        inputRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        humanAdapter = new cHumanAdapter(getActivity(), null, humanModels);
        inputRecyclerView.setAdapter(humanAdapter);
        inputRecyclerView.setLayoutManager(llm);
    }

    public cHumanAdapter getHumanAdapter() {
        return humanAdapter;
    }
}