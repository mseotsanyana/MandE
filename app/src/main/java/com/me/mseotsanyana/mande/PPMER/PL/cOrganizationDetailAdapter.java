package com.me.mseotsanyana.mande.PPMER.PL;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.mseotsanyana.mande.UTILITY.cOrganizationRecord;
import com.me.mseotsanyana.mande.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by mseotsanyana on 2016/08/02.
 */

public class cOrganizationDetailAdapter extends RecyclerView.Adapter<cOrganizationDetailAdapter.cOrganizationDetailViewHolder> {

    Context context;
    private List<cOrganizationRecord> detailList = new ArrayList<>();
    LayoutInflater inflater;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    public cOrganizationDetailAdapter(Context context, List<cOrganizationRecord> detailList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.detailList = detailList;
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public cOrganizationRecord getItem(int position) {
        return detailList.get(position);
    }

    public void removeItem(int position) {
        detailList.remove(position);
    }

    public void addItem(cOrganizationRecord item) {
        detailList.add(item);
    }

    public void updateItem(cOrganizationRecord item, int position) {
        removeItem(position);
        detailList.add(position, item);
    }

    @Override
    public cOrganizationDetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.organization_detail_cardview, viewGroup, false);

        return new cOrganizationDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cOrganizationDetailViewHolder organizationDetailViewHolder, int position) {
        cOrganizationRecord organizationRecord = detailList.get(position);

        organizationDetailViewHolder.valHeader.setText(organizationRecord.itemTitle);
        organizationDetailViewHolder.valBodyInfo.setText(organizationRecord.itemValue);

        // physical address
        if (organizationRecord.identifier == 0) {
            organizationDetailViewHolder.valBodyIcon.setImageResource(R.drawable.ic_organization_address);
        }
        // contact number
        if (organizationRecord.identifier == 1) {
            organizationDetailViewHolder.valBodyIcon.setImageResource(R.drawable.ic_old_mobile_phone);
            organizationDetailViewHolder.valGotoIcon.setImageResource(R.drawable.ic_right_chevron);
        }
        // fax number
        if (organizationRecord.identifier == 2) {
            organizationDetailViewHolder.valBodyIcon.setImageResource(R.drawable.ic_fax_top_view);
        }
        // email address
        if (organizationRecord.identifier == 3) {
            organizationDetailViewHolder.valBodyIcon.setImageResource(R.drawable.ic_opened_email_envelope);
            organizationDetailViewHolder.valGotoIcon.setImageResource(R.drawable.ic_right_chevron);
        }
        // official website
        if (organizationRecord.identifier == 4) {
            organizationDetailViewHolder.valBodyIcon.setImageResource(R.drawable.ic_website);
            organizationDetailViewHolder.valGotoIcon.setImageResource(R.drawable.ic_right_chevron);
        }

    }

    public class cOrganizationDetailViewHolder extends RecyclerView.ViewHolder {
        protected TextView valHeader;
        protected TextView valBodyInfo;
        protected ImageView valBodyIcon;
        protected ImageView valGotoIcon;

        public cOrganizationDetailViewHolder(final View view) {
            super(view);

            valHeader = (TextView) view.findViewById(R.id.valHeader);
            valBodyInfo = (TextView) view.findViewById(R.id.valBodyInfo);
            valBodyIcon = (ImageView) view.findViewById(R.id.valBodyIcon);
            valGotoIcon = (ImageView) view.findViewById(R.id.valGotoIcon);
        }
    }
}
