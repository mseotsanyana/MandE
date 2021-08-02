package com.me.mseotsanyana.mande.PL.ui.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.common.cCommonStatusAdapter;
import com.me.mseotsanyana.mande.R;

import java.util.ArrayList;

public class cStatusFragment extends Fragment {

    public static final String TITLE = "Status";

    private RecyclerView recyclerView;

    private int statusBITS;
    private ArrayList<cStatusModel> statusModels;

    private cCommonStatusAdapter commonStatusAdapter;

    public static cStatusFragment newInstance(int statusBITS, ArrayList<cStatusModel> statusModels) {
        Bundle bundle = new Bundle();

        bundle.putInt("STATUS_BITS", statusBITS);
        //-bundle.putParcelableArrayList("STATUS_MODELS", statusModels);

        cStatusFragment fragment = new cStatusFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.statusBITS = getArguments().getInt("STATUS_BITS", 0);
        //-this.statusModels = getArguments().getParcelableArrayList("STATUS_MODELS");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_status_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
/*
FIXME: by cleaning the "cBitwise"
        for (int i = 0; i < cBitwise.statuses.length; i++) {
            cStatusModel statusModel = new cStatusModel();
            statusModel.setStatusID(cBitwise.statuses[i]);
            statusModel.setName(cBitwise.status_names[i]);
            statusModel.setDescription(cBitwise.status_descriptions[i]);
            statusModels.add(statusModel);
        }
*/
        commonStatusAdapter = new cCommonStatusAdapter(statusModels, statusBITS);

        recyclerView.setAdapter(commonStatusAdapter);
    }
}
