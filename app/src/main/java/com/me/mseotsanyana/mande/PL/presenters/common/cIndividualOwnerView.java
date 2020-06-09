package com.me.mseotsanyana.mande.PL.presenters.common;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
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
public class cIndividualOwnerView {
    private static final String TAG = cIndividualOwnerView.class.getSimpleName();

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
    private cUserModel ownerModel;
    private String name;
    private String surname;
    private String description;
    private ArrayList<cUserModel> owners;
    private List<cKeyPairBoolData> keyPairBoolOwners;

    public cIndividualOwnerView(Context context, cUserModel userModel, ArrayList<cUserModel> owners) {
        this.context = context;
        this.ownerModel = userModel;
        this.name = userModel.getName();
        this.surname = userModel.getSurname();
        this.description = userModel.getEmail();
        this.owners = owners;
    }

    @Resolve
    public void onResolved() {
        textViewCaption.setText(R.string.individualOwner);
        textViewName.setText(name+" "+surname);
        textViewDescription.setText(description);

        // create a pair list of entity ids and names
        keyPairBoolOwners = new ArrayList<>();
        for (int i = 0; i < owners.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();

            idNameBool.setId(owners.get(i).getUserID());
            idNameBool.setName(owners.get(i).getName()+" "+owners.get(i).getSurname());
            idNameBool.setObject(owners.get(i));
            if (owners.get(i).getUserID() == ownerModel.getUserID()){
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
                textViewDescription.setText(((cUserModel)item.getObject()).getEmail());
                Log.d(TAG, " ================================ "+item);
            }
        });
    }
}