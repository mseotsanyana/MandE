package com.me.mseotsanyana.mande.PL.ui.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.PL.presenters.common.Impl.cOrganizationOwnerView;
import com.me.mseotsanyana.mande.PL.presenters.common.cHeadingView;
import com.me.mseotsanyana.mande.PL.presenters.common.cIndividualOwnerView;
import com.me.mseotsanyana.mande.PL.presenters.common.cRolesView;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandablePlaceHolderView;

import java.util.ArrayList;

public class cOwnershipFragment extends Fragment {

    public static final String TITLE = "Ownership";

    cExpandablePlaceHolderView expandablePlaceholderView;

    private long ownerID;
    private long ownOrgID;
    private int groupBITS;
    private int primaryRoles;
    private int secondaryRoles;
    ArrayList<cRoleModel> roleModels;
    ArrayList<cUserModel> userModels;
    ArrayList<cOrganizationModel> organizationModels;

    public static cOwnershipFragment newInstance(long ownerID, long ownOrgID, int groupBITS,
                                                 ArrayList<cUserModel> userModels,
                                                 ArrayList<cOrganizationModel> organizationModels,
                                                 int primaryRoles, int secondaryRoles,
                                                 ArrayList<cRoleModel> roleModels) {
        Bundle bundle = new Bundle();

        bundle.putLong("OWNER_ID", ownerID);
        bundle.putLong("OWN_ORG_ID", ownOrgID);
        bundle.putInt("GROUP_BITS", groupBITS);
        bundle.putInt("PRIMARY_ROLE", primaryRoles);
        bundle.putInt("SECONDARY_ROLE", secondaryRoles);
        bundle.putParcelableArrayList("ROLE_MODELS", roleModels);
        bundle.putParcelableArrayList("INDIVIDUAL_OWNER", userModels);
        bundle.putParcelableArrayList("ORGANIZATION_OWNER", organizationModels);

        cOwnershipFragment fragment = new cOwnershipFragment();
        fragment.setArguments(bundle);

        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.ownerID = getArguments().getInt("OWNER_ID", 0);
        this.ownOrgID = getArguments().getInt("OWN_ORG_ID", 0);
        this.groupBITS = getArguments().getInt("GROUP_BITS", 0);
        this.primaryRoles = getArguments().getInt("PRIMARY_ROLE", 0);
        this.secondaryRoles = getArguments().getInt("SECONDARY_ROLE", 0);
        this.roleModels = getArguments().getParcelableArrayList("ROLE_MODELS");
        this.userModels = getArguments().getParcelableArrayList("INDIVIDUAL_OWNER");
        this.organizationModels = getArguments().getParcelableArrayList("ORGANIZATION_OWNER");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_ownership_placeholderview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        expandablePlaceholderView = (cExpandablePlaceHolderView) view.findViewById(
                R.id.expandablePlaceholderView);

        /* individual owners */
        expandablePlaceholderView.addView(new cHeadingView(getContext(),
                "Individual and Organization Owners"));
        expandablePlaceholderView.addView(new cIndividualOwnerView(getContext(),
                getIndividualOwner(), userModels));
        expandablePlaceholderView.addView(new cOrganizationOwnerView(getContext(),
                getOrganizationOwner(), organizationModels));

        /* primary roles */
        expandablePlaceholderView.addView(new cHeadingView(getContext(), "Primary Role"));
        for (int i = 0; i < roleModels.size(); i++){
            expandablePlaceholderView.addView(new cRolesView(getContext(),
                    roleModels.get(i).getName(), roleModels.get(i).getDescription(),
                    (groupBITS & primaryRoles &
                            roleModels.get(i).getRoleID()) == roleModels.get(i).getRoleID()));
        }

        /* secondary roles */
        expandablePlaceholderView.addView(new cHeadingView(getContext(), "Secondary Role"));
        for (int i = 0; i < roleModels.size(); i++){
            expandablePlaceholderView.addView(new cRolesView(getContext(),
                    roleModels.get(i).getName(), roleModels.get(i).getDescription(),
                    (groupBITS & secondaryRoles &
                            roleModels.get(i).getRoleID()) == roleModels.get(i).getRoleID()));
        }

    }

    cUserModel getIndividualOwner(){
        cUserModel ownerModel = null;
        for (int i = 0; i < userModels.size(); i++){
            if (userModels.get(i).getUserID() == ownerID){
                ownerModel = userModels.get(i);
                break;
            }
        }

        return ownerModel;
    }

    cOrganizationModel getOrganizationOwner(){
        cOrganizationModel organizationModel = null;
        for (int i = 0; i < organizationModels.size(); i++){
            if (organizationModels.get(i).getOrganizationID() == ownOrgID){
                organizationModel = organizationModels.get(i);
                break;
            }
        }

        return organizationModel;
    }
}
