package com.me.mseotsanyana.mande.PL.presenters.common;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Layout;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Resolve;
import com.me.mseotsanyana.placeholderview.annotationlibrary.View;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ChildPosition;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ParentPosition;

@Layout(R.layout.me_ownership_role)
public class cRolesView {
    @ParentPosition
    public int mParentPosition;

    @ChildPosition
    public int mChildPosition;

    @View(R.id.textViewName)
    public TextView textViewName;

    @View(R.id.textViewDescription)
    public TextView textViewDescription;

    @View(R.id.switchStatus)
    public com.google.android.material.switchmaterial.SwitchMaterial switchStatus;

    private Context context;
    private String name;
    private String description;
    private boolean roleBITS;

    public cRolesView(Context context, String name, String description, boolean roleBITS) {
        this.context = context;
        this.name = name;
        this.description = description;
        this.roleBITS = roleBITS;
    }

    @Resolve
    public void onResolved() {
        textViewName.setText(name);
        textViewDescription.setText(description);
        switchStatus.setChecked(roleBITS);
        /*switchStatus.setChecked(roleModels.get(position).isState());
        switchStatus.setTag(statusModels.get(position));
        switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SwitchMaterial sc = (SwitchMaterial) buttonView;
                cStatusModel statusModel = (cStatusModel) sc.getTag();
                int statusID = statusModel.getStatusID();

                if (((SwitchMaterial) buttonView).isChecked()) {
                    statusBITS |= statusID;
                } else {
                    statusBITS &= ~statusID;
                }

                statusModels.get(position).setState((statusBITS & statusID) == statusID);
            }
        });*/
    }
}
