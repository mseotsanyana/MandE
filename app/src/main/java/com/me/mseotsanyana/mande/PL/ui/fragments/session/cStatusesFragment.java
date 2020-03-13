package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.domain.session.cStatusDomain;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cStatusAdapter;
import com.me.mseotsanyana.mande.R;

import java.util.ArrayList;

public class cStatusesFragment extends Fragment {
    private static final String TAG = cStatusesFragment.class.getSimpleName();

    private ArrayList<cStatusDomain> statusDomains;

    private cStatusAdapter statusAdapter;

    private RecyclerView recyclerViewStatus;
    private AppCompatCheckBox appCompatCheckBoxAllStatus;

    public cStatusesFragment() {
        // required empty public constructor
    }

    public cStatusesFragment newInstance(ArrayList<cStatusDomain> statusDomains) {
        Bundle bundle = new Bundle();

        cStatusesFragment fragment = new cStatusesFragment();
        bundle.putParcelableArrayList("STATUSES", statusDomains);

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
        // inflate the layout for this fragment
        return inflater.inflate(R.layout.operation_statuses, container,
                false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerViewStatus = (RecyclerView)
                view.findViewById(R.id.recyclerViewStatus);
        appCompatCheckBoxAllStatus = (AppCompatCheckBox)
                view.findViewById(R.id.appCompatCheckBoxAllStatus);

        statusDomains = getArguments().getParcelableArrayList("STATUSES");

        Gson gson = new Gson();
        Log.d(TAG, gson.toJson(appCompatCheckBoxAllStatus.isChecked()));

        statusAdapter = new cStatusAdapter(getContext(), statusDomains,
                0, appCompatCheckBoxAllStatus);

        recyclerViewStatus.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewStatus.setAdapter(statusAdapter);
        recyclerViewStatus.setLayoutManager(llm);

        appCompatCheckBoxAllStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appCompatCheckBoxAllStatus.isChecked()) {
                    for (cStatusDomain domain : statusDomains) {
                        domain.setState(true);
                    }
                } else {
                    for (cStatusDomain domain : statusDomains) {
                        domain.setState(false);
                    }
                }
                statusAdapter.notifyDataSetChanged();
            }
        });
     }
}