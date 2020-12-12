package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cOutcomeAuxAdapter;
import com.me.mseotsanyana.mande.R;

public class cOutcomeAuxFragment extends Fragment {
    private static String TAG = cOutcomeAuxFragment.class.getSimpleName();

    private cOutcomeModel[] outcomeModels;

    public cOutcomeAuxFragment() {
    }

    public static cOutcomeAuxFragment newInstance(cOutcomeModel[] outcomeModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArray("OUTCOME_MODELS", outcomeModels);
        cOutcomeAuxFragment fragment = new cOutcomeAuxFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        outcomeModels = (cOutcomeModel[]) getArguments().getParcelableArray("OUTCOME_MODELS");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.impact_aux_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        cOutcomeAuxAdapter outcomeAuxAdapter = new cOutcomeAuxAdapter(getActivity(), outcomeModels);

        assert getArguments() != null;
        RecyclerView outcomeRecyclerView = view.findViewById(R.id.impactAuxRecyclerView);
        outcomeRecyclerView.setHasFixedSize(true);
        outcomeRecyclerView.setAdapter(outcomeAuxAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        outcomeRecyclerView.setLayoutManager(llm);

        Log.i(TAG, "Outcome Model Successfully Created ");
    }
}