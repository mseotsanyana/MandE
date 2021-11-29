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

import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cQuestionAuxAdapter;
import com.me.mseotsanyana.mande.R;

public class cQuestionAuxFragment extends Fragment {
    private static String TAG = cQuestionAuxFragment.class.getSimpleName();

    private cQuestionModel[] questionModels;

    public cQuestionAuxFragment() {
    }

    public static cQuestionAuxFragment newInstance(cQuestionModel[] questionModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArray("QUESTION_MODELS", questionModels);
        cQuestionAuxFragment fragment = new cQuestionAuxFragment();
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
        questionModels = (cQuestionModel[]) getArguments().getParcelableArray("QUESTION_MODELS");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.impact_outcome_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        cQuestionAuxAdapter questionAuxAdapter = new cQuestionAuxAdapter(getActivity(), questionModels);

        assert getArguments() != null;
        RecyclerView questionRecyclerView = view.findViewById(R.id.impactOutcomeRecyclerView);
        questionRecyclerView.setHasFixedSize(true);
        questionRecyclerView.setAdapter(questionAuxAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        questionRecyclerView.setLayoutManager(llm);

        Log.i(TAG, "Question Model Successfully Created ");
    }
}