package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cImpactOutcomeAdapter;
import com.me.mseotsanyana.mande.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cImpactOutcomeFragment extends Fragment {
    //private static String TAG = cImpactOutcomeFragment.class.getSimpleName();

    private List<cOutcomeModel> outcomeModels;

    public cImpactOutcomeFragment() {
    }

    public static cImpactOutcomeFragment newInstance(List<cOutcomeModel> outcomeModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("OUTCOME_MODELS", (ArrayList<? extends Parcelable>) outcomeModels);
        cImpactOutcomeFragment fragment = new cImpactOutcomeFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        //this.requireView().invalidate();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        outcomeModels = getArguments().getParcelableArrayList("OUTCOME_MODELS");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.impact_outcome_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        cImpactOutcomeAdapter outcomeAuxAdapter = new cImpactOutcomeAdapter(getActivity(),
                outcomeModels);

        assert getArguments() != null;
        RecyclerView outcomeRecyclerView = view.findViewById(R.id.impactOutcomeRecyclerView);
        outcomeRecyclerView.setHasFixedSize(true);
        outcomeRecyclerView.setAdapter(outcomeAuxAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        outcomeRecyclerView.setLayoutManager(llm);

        //Log.i(TAG, "Outcome Model Successfully Created ");
    }
}