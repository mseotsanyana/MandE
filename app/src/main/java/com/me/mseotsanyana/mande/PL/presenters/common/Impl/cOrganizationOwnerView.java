package com.me.mseotsanyana.mande.PL.presenters.common.Impl;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.DAL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerListener;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Layout;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Resolve;
import com.me.mseotsanyana.placeholderview.annotationlibrary.View;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ChildPosition;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ParentPosition;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.me_record_owner)
public class cOrganizationOwnerView {
    private static final String TAG = cOrganizationOwnerView.class.getSimpleName();

    @ParentPosition
    public int mParentPosition;

    @ChildPosition
    public int mChildPosition;

    @View(R.id.textViewCaption)
    public TextView textViewCaption;

    @View(R.id.textViewName)
    public TextView textViewName;

    @View(R.id.textViewDescription)
    public TextView textViewDescription;

    @View(R.id.singleSpinnerSearchOwners)
    public cSingleSpinnerSearch singleSpinnerSearchOwners;

    private Context context;
    private cOrganizationModel ownerModel;
    private String name;
    private String description;
    private ArrayList<cOrganizationModel> owners;
    private List<cKeyPairBoolData> keyPairBoolOwners;

    public cOrganizationOwnerView(Context context, cOrganizationModel ownerModel,
                                  ArrayList<cOrganizationModel> owners) {
        this.context = context;
        this.ownerModel = ownerModel;
        this.name = ownerModel.getName();
        this.description = ownerModel.getEmail();
        this.owners = owners;
    }

    @Resolve
    public void onResolved() {
        textViewCaption.setText(R.string.organizationOwner);
        textViewName.setText(name);
        textViewDescription.setText(description);

        // create a pair list of entity ids and names
        keyPairBoolOwners = new ArrayList<>();
        for (int i = 0; i < owners.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            idNameBool.setId(owners.get(i).getOrganizationID());
            idNameBool.setName(owners.get(i).getName());
            idNameBool.setObject(owners.get(i));
            if (owners.get(i).getOrganizationID() == ownerModel.getOrganizationID()){
                idNameBool.setSelected(true);
            }else {
                idNameBool.setSelected(false);
            }

            keyPairBoolOwners.add(idNameBool);
        }

        Gson gson = new Gson();

        // called when click spinner
        singleSpinnerSearchOwners.setItem(keyPairBoolOwners, -1, new cSingleSpinnerListener() {
            @Override
            public void onItemSelected(cKeyPairBoolData item) {
                textViewName.setText(item.getName());
                if (item.getObject() instanceof cUserModel){
                    textViewDescription.setText(((cUserModel)item.getObject()).getDescription());
                }
                if (item.getObject() instanceof cOrganizationModel){
                    textViewDescription.setText(((cOrganizationModel)item.getObject()).getEmail());
                }
                Log.d(TAG, " ================================ "+item.getName());
            }
        });
    }
}