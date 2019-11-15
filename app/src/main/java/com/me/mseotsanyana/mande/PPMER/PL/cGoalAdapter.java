package com.me.mseotsanyana.mande.PPMER.PL;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.mseotsanyana.mande.PPMER.BLL.cGoalDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOrganizationDomain;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cGoalAdapter extends cTreeAdapter {
    public static final int ORGANIZATION  = 0;
    public static final int GOAL          = 1;

    public cGoalAdapter(Context context, List<cTreeModel> data, int expLevel){
        super(context, data, expLevel);
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType){
            case ORGANIZATION:
                view = inflater.inflate(R.layout.organization_goal_cardview, parent, false);
                viewHolder = new cOrganizationTreeViewHolder(view);
                break;
            case GOAL:
                view = inflater.inflate(R.layout.goal_specificaim_cardview, parent, false);
                viewHolder = new cGoalTreeViewHolder(view);
                break;
            default:
                viewHolder = null;
                break;
        }

        return viewHolder;
    }

    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, final int position){
        cNode node = visibleNodes.get(position);
        int r = node.numberOfChildren();
        cTreeModel obj = (cTreeModel) node.getObj();
        if (obj != null){
            switch (obj.getType()){
                case ORGANIZATION:
                    cOrganizationDomain organizationDomain = (cOrganizationDomain) obj.getModelObject();
                    cOrganizationTreeViewHolder AVH = ((cOrganizationTreeViewHolder)viewHolder);

                    AVH.setPaddingLeft(40 * node.getLevel());
                    AVH.textViewOrganization.setText(organizationDomain.getName());
                    AVH.textViewCountOrganization.setText("the organization has "+node.numberOfChildren()+" goal(s):");


                    if(node.isLeaf()){
                        AVH.iconViewOrganization.setVisibility(View.INVISIBLE);
                    }else{
                        AVH.iconViewOrganization.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            AVH.iconViewOrganization.setSelected(true);
                        } else {
                            AVH.iconViewOrganization.setSelected(false);
                        }
                    }

                    AVH.iconViewOrganization.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    break;

                case GOAL:
                    cGoalDomain goalDomain = (cGoalDomain)obj.getModelObject();
                    cGoalTreeViewHolder SVH = ((cGoalTreeViewHolder)viewHolder);

                    SVH.setPaddingLeft(40 * node.getLevel());
                    SVH.textViewGoal.setText(goalDomain.getGoalName());
                    //SVH.textViewCountGoal.setText("the specific aim has "+node.numberOfChildren()+" objective(s):");


                    if(node.isLeaf()){
                        SVH.iconViewGoal.setVisibility(View.INVISIBLE);
                    }else{
                        SVH.iconViewGoal.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            SVH.iconViewGoal.setSelected(true);
                        } else {
                            SVH.iconViewGoal.setSelected(false);
                        }
                    }

                    SVH.iconViewGoal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    break;
            }
        }
    }

    public static class cOrganizationTreeViewHolder extends cTreeViewHolder {
        ImageView iconViewOrganization;
        TextView textViewOrganization;
        TextView textViewCountOrganization;

        private View treeView;

        public cOrganizationTreeViewHolder(final View treeViewHolder){
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.iconViewOrganization = (ImageView)treeViewHolder.findViewById(R.id.organization_icon_iv);
            this.textViewCountOrganization = (TextView)treeViewHolder.findViewById(R.id.txtCountOrganization);
            this.textViewOrganization = (TextView)treeViewHolder.findViewById(R.id.txtOrganization);
        }

        public void setPaddingLeft(int paddingLeft)
        {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cGoalTreeViewHolder extends cTreeViewHolder {
        ImageView iconViewGoal;
        TextView textViewGoal;
        //TextView textViewCountGoal;

        private View treeView;

        public cGoalTreeViewHolder(View treeViewHolder){
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.iconViewGoal = (ImageView)treeViewHolder.findViewById(R.id.goal_icon_id);
            //this.textViewCountGoal = (TextView)treeViewHolder.findViewById(R.id.txtCountGoal);
            this.textViewGoal= (TextView)treeViewHolder.findViewById(R.id.txtGoal);
        }

        public void setPaddingLeft(int paddingLeft)
        {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
