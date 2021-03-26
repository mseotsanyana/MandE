package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.session.cAgreementModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cAgreementAdapter;
import com.me.mseotsanyana.mande.R;

import java.util.ArrayList;

public class cAgreementFragment extends Fragment {
    private static String TAG = cAgreementFragment.class.getSimpleName();

    private ArrayList<cAgreementModel> agreementModels;
    private cAgreementAdapter agreementAdapter;

    public cAgreementFragment() {
    }

    public static cAgreementFragment newInstance(ArrayList<cAgreementModel> agreementModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("AGREEMENT_MODELS", agreementModels);
        cAgreementFragment fragment = new cAgreementFragment();
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
        return inflater.inflate(R.layout.session_agreements_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        assert getArguments() != null;
        agreementModels = getArguments().getParcelableArrayList("AGREEMENT_MODELS");

        RecyclerView agreementRecyclerView = view.findViewById(R.id.agreementRecyclerView);
        agreementRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        agreementAdapter = new cAgreementAdapter(getActivity(), null, agreementModels);
        agreementRecyclerView.setAdapter(agreementAdapter);
        agreementRecyclerView.setLayoutManager(llm);
    }

    public cAgreementAdapter getAgreementAdapter() {
        return agreementAdapter;
    }
}