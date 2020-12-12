package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cQuestionAux1Adapter;
import com.me.mseotsanyana.mande.PL.ui.fragments.awpb.cHumanFragment;
import com.me.mseotsanyana.mande.R;

import java.util.ArrayList;

/**
 * Created by mseotsanyana on 2016/12/13.
 */

public class cQuestionAuxiliaryFragment extends Fragment {
    private static String TAG = cHumanFragment.class.getSimpleName();

    private cQuestionAux1Adapter questionAuxiliaryAdapter;

    public cQuestionAuxiliaryFragment() {
    }

    public static cQuestionAuxiliaryFragment newInstance(ArrayList<cQuestionModel> questionModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("QUESTION_MODELS", questionModels);
        cQuestionAuxiliaryFragment fragment = new cQuestionAuxiliaryFragment();
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
        ArrayList<cQuestionModel> questionModels = getArguments().getParcelableArrayList(
                "QUESTION_MODELS");

        RecyclerView inputRecyclerView = view.findViewById(R.id.resourcesRecyclerView);
        inputRecyclerView.setHasFixedSize(true);
/*
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        questionAuxiliaryAdapter = new cQuestionAuxiliaryAdapter(getActivity(),
                null, questionModels, -1);
        inputRecyclerView.setAdapter(questionAuxiliaryAdapter);
        inputRecyclerView.setLayoutManager(llm);

 */
    }

    public cQuestionAux1Adapter getHumanAdapter() {
        return questionAuxiliaryAdapter;
    }
}
