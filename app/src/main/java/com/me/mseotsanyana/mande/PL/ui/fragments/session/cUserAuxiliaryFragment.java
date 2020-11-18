package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cUserAuxiliaryAdapter;
import com.me.mseotsanyana.mande.R;

import java.util.ArrayList;

/**
 * Created by mseotsanyana on 2018/01/22.
 */

public class cUserAuxiliaryFragment extends Fragment {
    private static String TAG = cUserAuxiliaryFragment.class.getSimpleName();

    private cUserAuxiliaryAdapter userAuxiliaryAdapter;

    public cUserAuxiliaryFragment() {
    }

    public static cUserAuxiliaryFragment newInstance(ArrayList<cUserModel> userModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("USER_MODELS", userModels);
        cUserAuxiliaryFragment fragment = new cUserAuxiliaryFragment();
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
        ArrayList<cUserModel> userModels = getArguments().getParcelableArrayList(
                "USER_MODELS");

        RecyclerView userRecyclerView = view.findViewById(R.id.resourcesRecyclerView);
        userRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        //userAuxiliaryAdapter = new cUserAuxiliaryAdapter(getActivity(), null, userModels, -1);
        userRecyclerView.setAdapter(userAuxiliaryAdapter);
        userRecyclerView.setLayoutManager(llm);
    }

    public cUserAuxiliaryAdapter getUserAuxiliaryAdapter() {
        return userAuxiliaryAdapter;
    }
}
