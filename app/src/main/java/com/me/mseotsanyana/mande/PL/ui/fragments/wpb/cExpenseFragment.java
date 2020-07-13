package com.me.mseotsanyana.mande.PL.ui.fragments.wpb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.PL.ui.adapters.awpb.cExpenseAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

public class cExpenseFragment extends Fragment {
    private static String TAG = cExpenseFragment.class.getSimpleName();

    private cExpenseAdapter expenseAdapter;

    public cExpenseFragment() {
    }

    public static cExpenseFragment newInstance(ArrayList<cTreeModel> expenseTreeModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("EXPENSE_TREE_MODELS", expenseTreeModels);
        cExpenseFragment fragment = new cExpenseFragment();
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
        ArrayList<cTreeModel> expenseTreeModels = getArguments().getParcelableArrayList(
                "EXPENSE_TREE_MODELS");

        RecyclerView inputRecyclerView = view.findViewById(R.id.resourcesRecyclerView);
        inputRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        expenseAdapter = new cExpenseAdapter(getActivity(), null,
                expenseTreeModels, -1);
        inputRecyclerView.setAdapter(expenseAdapter);
        inputRecyclerView.setLayoutManager(llm);
    }

    public cExpenseAdapter getExpenseAdapter() {
        return expenseAdapter;
    }
}