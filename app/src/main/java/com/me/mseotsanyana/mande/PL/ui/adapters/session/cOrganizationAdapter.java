package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cOrganizationFragment;
import com.me.mseotsanyana.mande.UTIL.cOrganizationRecord;
import com.me.mseotsanyana.mande.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;


/**
 * Created by mseotsanyana on 2016/08/02.
 */

public class cOrganizationAdapter extends RecyclerView.Adapter<cOrganizationAdapter.cOrganizationViewHolder> {

    Context context;
    private List<cOrganizationModel> organizationList = new ArrayList<>();
    cOrganizationRecord itemDetail;

    LayoutInflater inflater;

    private cOrganizationFragment organizationFragment;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    private HashSet<Integer> mExpandedPositionSet = new HashSet<>();

    public cOrganizationAdapter(Context context, List<cOrganizationModel> organizationList,
                                cOrganizationFragment organizationFragment) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.organizationList = organizationList;
        this.organizationFragment = organizationFragment;
    }

    public cOrganizationAdapter(Context context, List<cOrganizationModel> organizationList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.organizationList = null;// organizationList;
        //this.organizationFragment = organizationFragment;
    }

    @Override
    public int getItemCount() {
        return organizationList.size();
    }

    public cOrganizationModel getItem(int position) {
        return null;//organizationList.get(position);
    }

    public void removeItem(int position) {
        organizationList.remove(position);
    }

    public void addItem(cOrganizationModel organizationDomain) {
        //organizationList.add(organizationDomain);
    }

    public void updateItem(cOrganizationModel organizationDomain, int position) {
        removeItem(position);
        //organizationList.add(position, organizationDomain);
    }

    private List<cOrganizationRecord> organizationDetailList(cOrganizationModel organizationDomain) {
        List<cOrganizationRecord> detailList = new ArrayList<>();

        itemDetail = new cOrganizationRecord();
        itemDetail.identifier = 0;
        itemDetail.itemTitle = "Address";
        //itemDetail.itemValue = organizationDomain.getPhysicalAddress();
        detailList.add(itemDetail);

        itemDetail = new cOrganizationRecord();
        itemDetail.identifier = 1;
        itemDetail.itemTitle = "Contact";
        itemDetail.itemValue = organizationDomain.getPhone();
        detailList.add(itemDetail);

        itemDetail = new cOrganizationRecord();
        itemDetail.identifier = 2;
        itemDetail.itemTitle = "Fax";
        itemDetail.itemValue = organizationDomain.getFax();
        detailList.add(itemDetail);

        itemDetail = new cOrganizationRecord();
        itemDetail.identifier = 3;
        itemDetail.itemTitle = "Email";
        itemDetail.itemValue = organizationDomain.getEmail();
        detailList.add(itemDetail);

        itemDetail = new cOrganizationRecord();
        itemDetail.identifier = 4;
        itemDetail.itemTitle = "Official Website";
        itemDetail.itemValue = organizationDomain.getWebsite();
        detailList.add(itemDetail);

        return detailList;
    }

    private List<cOrganizationRecord> organizationMoreList(cOrganizationModel organizationDomain) {
        List<cOrganizationRecord> moreList = new ArrayList<>();

        itemDetail = new cOrganizationRecord();
        itemDetail.identifier = 5;
        itemDetail.itemTitle = "Vision";
        itemDetail.itemValue = organizationDomain.getVision();
        moreList.add(itemDetail);

        itemDetail = new cOrganizationRecord();
        itemDetail.identifier = 6;
        itemDetail.itemTitle = "Mission";
        itemDetail.itemValue = organizationDomain.getMission();
        moreList.add(itemDetail);

        return moreList;
    }

    @Override
    public cOrganizationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.organization_cardview, viewGroup, false);

        return new cOrganizationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cOrganizationViewHolder organizationViewHolder, int position) {
        cOrganizationModel ci = organizationList.get(position);

        List<cOrganizationRecord> detailList = organizationDetailList(ci);
        List<cOrganizationRecord> moreList   = organizationMoreList(ci);

        organizationViewHolder.txtOrganization.setText(ci.getName());

        // first level
        organizationViewHolder.detailRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm_detail = new LinearLayoutManager(context);
        llm_detail.setOrientation(LinearLayoutManager.VERTICAL);
        cOrganizationDetailAdapter adapter_detail = new cOrganizationDetailAdapter(context, detailList);
        organizationViewHolder.detailRecyclerView.setAdapter(adapter_detail);
        organizationViewHolder.detailRecyclerView.setLayoutManager(llm_detail);

        // second level
        organizationViewHolder.moreRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm_more = new LinearLayoutManager(context);
        llm_more.setOrientation(LinearLayoutManager.VERTICAL);
        cOrganizationMoreAdapter adapter_more = new cOrganizationMoreAdapter(context, moreList);
        organizationViewHolder.moreRecyclerView.setAdapter(adapter_more);
        organizationViewHolder.moreRecyclerView.setLayoutManager(llm_more);

        // second level - values
        organizationViewHolder.valValueHeader.setText("Values");

        organizationViewHolder.valuesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm_values = new LinearLayoutManager(context);
        llm_more.setOrientation(LinearLayoutManager.VERTICAL);
//--        cOrganizationValueAdapter adapter_values = new cOrganizationValueAdapter(context, ci.getValues());
//--        organizationViewHolder.valuesRecyclerView.setAdapter(adapter_values);
        organizationViewHolder.valuesRecyclerView.setLayoutManager(llm_values);
        organizationViewHolder.updateItem(position);
    }

    public class cOrganizationViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtOrganization;
        protected RecyclerView detailRecyclerView;
        protected RecyclerView moreRecyclerView;
        protected RecyclerView valuesRecyclerView;
        protected CardView valuesCardView;
        protected TextView valValueHeader;

        private cExpandableLayout expandableLayout;

        public cOrganizationViewHolder(final View itemView) {
            super(itemView);

            txtOrganization =  (TextView) itemView.findViewById(R.id.organization_id);
            detailRecyclerView = (RecyclerView) itemView.findViewById(R.id.organization_detail_recyclerview_id);
            moreRecyclerView = (RecyclerView) itemView.findViewById(R.id.organization_more_recyclerview_id);
            valuesRecyclerView = (RecyclerView) itemView.findViewById(R.id.organization_values_recyclerview_id);

            valuesCardView = (CardView) itemView.findViewById(R.id.card_view_values);
            valValueHeader = (TextView) itemView.findViewById(R.id.valValueHeader);

            expandableLayout = (cExpandableLayout) itemView.findViewById(R.id.organization_expandable_layout);

        }

        private void updateItem(final int position) {
            expandableLayout.setOnExpandListener(new cExpandableLayout.OnExpandListener() {
                @Override
                public void onExpand(boolean expanded) {
                    registerExpand(position);
                }
            });
            expandableLayout.setExpand(mExpandedPositionSet.contains(position));

        }
    }

    private void registerExpand(int position) {
        if (mExpandedPositionSet.contains(position)) {
            removeExpand(position);
        }else {
            addExpand(position);
        }
    }

    private void removeExpand(int position) {
        mExpandedPositionSet.remove(position);
    }

    private void addExpand(int position) {
        mExpandedPositionSet.add(position);
    }
}