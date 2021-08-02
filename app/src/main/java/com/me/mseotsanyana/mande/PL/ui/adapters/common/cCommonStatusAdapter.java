package com.me.mseotsanyana.mande.PL.ui.adapters.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.R;

import java.util.ArrayList;

/**
 * Created by mseotsanyana on 2018/01/22.
 */

public class cCommonStatusAdapter extends RecyclerView.Adapter<cCommonStatusAdapter.cStatusViewHolder> {
    private static final String TAG = cCommonStatusAdapter.class.getSimpleName();

    //private Context context;
    private ArrayList<cStatusModel> statusModels;
    private int statusBITS;

    public cCommonStatusAdapter(ArrayList<cStatusModel> statusModels, int statusBITS) {
        //this.context = context;
        this.statusModels = statusModels;
        this.statusBITS = statusBITS;
    }

    @Override
    public cStatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.me_status_cardview, parent, false);

        return new cStatusViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cStatusViewHolder SH, final int position) {
        SH.textViewName.setText(statusModels.get(position).getName());
        SH.textViewDescription.setText(statusModels.get(position).getDescription());
        SH.switchStatus.setChecked(statusModels.get(position).isChecked());
        SH.switchStatus.setTag(statusModels.get(position));

        SH.switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SwitchMaterial sc = (SwitchMaterial) buttonView;
                cStatusModel statusModel = (cStatusModel) sc.getTag();
                int statusID = statusModel.getStatusServerID();

                if (((SwitchMaterial) buttonView).isChecked()) {
                    statusBITS |= statusID;
                } else {
                    statusBITS &= ~statusID;
                }

                statusModels.get(position).setChecked((statusBITS & statusID) == statusID);
            }
        });
    }

    @Override
    public int getItemCount() {
        return statusModels.size();
    }

    public ArrayList<cStatusModel> getItems() {
        return statusModels;
    }

    /**
     * cViewHolder class
     */
    public class cStatusViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewDescription;
        public SwitchMaterial switchStatus;

        public cStatusViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewName);
            textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
            switchStatus = (SwitchMaterial) view.findViewById(R.id.switchStatus);
        }
    }
}
