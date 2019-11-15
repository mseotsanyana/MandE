package com.me.mseotsanyana.mande.PPMER.PL;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class cOrganizationMoreAdapter extends RecyclerView.Adapter<cOrganizationMoreAdapter.cOrganizationMoreViewHolder> {

    Context context;
    private List<cOrganizationRecord> detailList = new ArrayList<>();
    LayoutInflater inflater;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    public cOrganizationMoreAdapter(Context context, List<cOrganizationRecord> detailList) {
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
    public cOrganizationMoreViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.organization_more_cardview, viewGroup, false);

        return new cOrganizationMoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cOrganizationMoreViewHolder organizationMoreViewHolder, int position) {
        cOrganizationRecord organizationRecord = detailList.get(position);

        organizationMoreViewHolder.valHeader.setText(organizationRecord.itemTitle);
        organizationMoreViewHolder.valBodyInfo.setText(organizationRecord.itemValue);

    }

    public class cOrganizationMoreViewHolder extends RecyclerView.ViewHolder {
        protected TextView valHeader;
        protected TextView valBodyInfo;


        public cOrganizationMoreViewHolder(final View view) {
            super(view);

            valHeader = (TextView) view.findViewById(R.id.valHeader);
            valBodyInfo = (TextView) view.findViewById(R.id.valBodyInfo);

        }
    }
}
