package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.firebase.session.cOrganizationFirebaseRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cSessionManagerImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cOrganizationPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iOrganizationPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cOrganizationAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerListener;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cOrganizationFragment extends Fragment implements iOrganizationPresenter.View{
    private static String TAG = cOrganizationFragment.class.getSimpleName();

    private iOrganizationPresenter organizationPresenter;

    private LinearLayout includeProgressBar;

    private ArrayList<cOrganizationModel> organizationModels;
    private cOrganizationAdapter organizationAdapter;

    private final String[] ORG_TYPE = {"National Partner","Donor","Beneficiary","Implementing Agency"};

    public cOrganizationFragment() {
    }

    public static cOrganizationFragment newInstance(ArrayList<cOrganizationModel> organizationModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ORGANIZATION_MODELS", organizationModels);
        cOrganizationFragment fragment = new cOrganizationFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all organizations from the database */
        organizationPresenter.readOrganizations();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.session_organizations_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* create data structures */
        initDataStructures();

        /* create RecyclerView */
        initOrganizationViews(view);

        /* draggable floating button */
        initDraggableFAB(view);
    }

    private void initDataStructures() {
        assert getArguments() != null;
        organizationModels = getArguments().getParcelableArrayList("ORGANIZATION_MODELS");
        organizationAdapter = new cOrganizationAdapter(getActivity(), organizationModels);

        organizationPresenter = new cOrganizationPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSessionManagerImpl(getContext()),
                new cOrganizationFirebaseRepositoryImpl(getContext()));


        //activity = ((AppCompatActivity) getActivity());
    }

    private void initOrganizationViews(View view) {
        includeProgressBar = view.findViewById(R.id.includeProgressBar);
        RecyclerView orgRecyclerView = view.findViewById(R.id.orgRecyclerView);

        orgRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        orgRecyclerView.setAdapter(organizationAdapter);
        orgRecyclerView.setLayoutManager(llm);
    }

    private void initDraggableFAB(View view) {
        view.findViewById(R.id.organizationFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCreateOrganization();
            }
        });
    }

    // READ

    @Override
    public void onReadOrganizationsFailed(String msg) {

    }

    @Override
    public void onReadOrganizationsSucceeded(ArrayList<cOrganizationModel> organizationModels) {
        this.organizationAdapter.setOrganizationModels(organizationModels);
        this.organizationAdapter.notifyDataSetChanged();

        Gson gson = new Gson();
        Log.d(TAG, "===================== "+gson.toJson(organizationModels));
    }

    // CREATE

    @Override
    public void onClickCreateOrganization() {
        createAlertDialog();
    }

    @Override
    public void onCreateOrganizationFailed(String msg) {

    }

    @Override
    public void onCreateOrganizationSucceeded(String msg) {

    }

    // PRESENTER FUNCTIONS

    @Override
    public void showProgress() {
        includeProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        includeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    // OTHER

    public cOrganizationAdapter getOrganizationAdapter() {
        return organizationAdapter;
    }

    private void createAlertDialog() {
        /* inflate the resource for create and update */
        LayoutInflater inflater = this.getLayoutInflater();
        View createView = inflater.inflate(R.layout.session_org_create_update, null);

        /* instantiates create views */
        final int[] org_index = new int[1];
        TextView textViewTitle = createView.findViewById(R.id.textViewTitle);
        TextView textViewOrgType = createView.findViewById(R.id.textViewOrgType);
        cSingleSpinnerSearch singleSpinner = createView.findViewById(R.id.singleSpinner);
        AppCompatEditText editTextName = createView.findViewById(R.id.editTextName);
        AppCompatEditText editTextEmail = createView.findViewById(R.id.editTextEmail);
        AppCompatEditText editTextWebsite = createView.findViewById(R.id.editTextWebsite);

        /* set a title of the create view */
        textViewTitle.setText(requireContext().getResources().getText(
                R.string.organization_create_title));

        /* populate the logical model with the create views */
        /* 1. create selection dialog box for organizations */
        List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
        for (int i = 0; i < ORG_TYPE.length; i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            idNameBool.setId(i);
            idNameBool.setName(ORG_TYPE[i]);
            idNameBool.setSelected(false);
            keyPairBoolOrgs.add(idNameBool);
        }

        Gson gson = new Gson();


        // called when click spinner
        singleSpinner.setItem(keyPairBoolOrgs, -1, new cSingleSpinnerListener() {
            @Override
            public void onItemSelected(cKeyPairBoolData item) {
                /* assign selected organization name to the view */
                textViewOrgType.setText(item.getName());
                org_index[0] = (int) item.getId();
                Log.d(TAG, "====================== "+gson.toJson(item.getName()));
            }
        });

        /* create or cancel action */
        MaterialAlertDialogBuilder alertDialogBuilder =
                new MaterialAlertDialogBuilder(requireActivity(), R.style.AlertDialogTheme);
        alertDialogBuilder.setPositiveButton(getContext().getResources().getText(
                R.string.Save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                createOrganization(org_index[0],
                        Objects.requireNonNull(editTextName.getText()).toString(),
                        Objects.requireNonNull(editTextEmail.getText()).toString(),
                        Objects.requireNonNull(editTextWebsite.getText()).toString());
            }
        });

        alertDialogBuilder.setNegativeButton(getContext().getResources().getText(
                R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setView(createView)
                .show();
    }

    private void createOrganization(int org_type, String name, String email, String website){
        cOrganizationModel organizationModel = new cOrganizationModel(org_type, name, email, website);
        organizationPresenter.createOrganization(organizationModel);
    }


    private void deleteAlertDialog(int resID, String title, String message, int position,
                                   String organizationID) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireContext());

        // setting icon to dialog
        TextDrawable faIcon = new TextDrawable(requireContext());
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(cFontManager.getTypeface(requireContext(), cFontManager.FONTAWESOME));
        faIcon.setText(requireContext().getResources().getText(resID));
        faIcon.setTextColor(requireContext().getColor(R.color.colorAccent));
        alertDialogBuilder.setIcon(faIcon);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(requireContext().getResources().getText(
                        R.string.Yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteOrganizationModel(organizationID, position);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(requireContext().getResources().getText(
                        R.string.No), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void deleteOrganizationModel(String logFrameID, int position){
        //organizationPresenter.deleteLogFrameModel(logFrameID, position);
    }
}