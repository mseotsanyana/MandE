package com.me.mseotsanyana.mande.PPMER.PL;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.mseotsanyana.mande.PPMER.BLL.cGoalDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.domain.cImpactDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cSpecificAimDomain;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cTriangleAdapter extends cTreeAdapter {
    //private cTriangleFragment triangleFragment;
    //private cTreeModel objModel;

    public static final int OVERALL_AIM  = 0;
    public static final int SPECIFIC_AIM = 1;
    public static final int OBJECTIVE    = 2;

    public cTriangleAdapter(Context context, List<cTreeModel> data, int expLevel){
        super(context, data, expLevel);
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType){
            case OVERALL_AIM:
                view = inflater.inflate(R.layout.goal_specificaim_cardview, parent, false);
                viewHolder = new cGoalTreeViewHolder(view);
                break;
            case SPECIFIC_AIM:
                view = inflater.inflate(R.layout.cardview_specificaim, parent, false);
                viewHolder = new cSpecificAimTreeViewHolder(view);
                break;
            case OBJECTIVE:
                view = inflater.inflate(R.layout.cardview_objective, parent, false);
                viewHolder = new cObjectiveTreeViewHolder(view);
                break;
            default:
                viewHolder = null;
                break;
        }

        return viewHolder;
    }

    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, final int position){
        cNode node = visibleNodes.get(position);
        //int r = node.numberOfChildren();
        cTreeModel obj = (cTreeModel) node.getObj();

        if (obj != null){
            switch (obj.getType()){
                case OVERALL_AIM:
                    cGoalDomain goalDomain = (cGoalDomain) obj.getModelObject();
                    cGoalTreeViewHolder AVH = ((cGoalTreeViewHolder)viewHolder);

                    AVH.setPaddingLeft(40 * node.getLevel());
                    AVH.textViewGoal.setText(goalDomain.getGoalName());
                    //AVH.textViewCountGoal.setText("the overall aim has "+node.numberOfChildren()+" specific aim(s):");


                    if(node.isLeaf()){
                        AVH.iconViewGoal.setVisibility(View.INVISIBLE);
                    }else{
                        AVH.iconViewGoal.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            AVH.iconViewGoal.setSelected(true);
                        } else {
                            AVH.iconViewGoal.setSelected(false);
                        }
                    }

                    AVH.iconViewGoal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    break;

                case SPECIFIC_AIM:
                    cSpecificAimDomain specificAimDomain = (cSpecificAimDomain)obj.getModelObject();
                    cSpecificAimTreeViewHolder SVH = ((cSpecificAimTreeViewHolder)viewHolder);

                    SVH.setPaddingLeft(40 * node.getLevel());
                    SVH.textViewSpecificAim.setText(specificAimDomain.getSpecificAimName());
                    //SVH.textViewCountSpecificAim.setText("the specific aim has "+node.numberOfChildren()+" objective(s):");


                    if(node.isLeaf()){
                        SVH.iconViewSpecificAim.setVisibility(View.INVISIBLE);
                    }else{
                        SVH.iconViewSpecificAim.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            SVH.iconViewSpecificAim.setSelected(true);
                        } else {
                            SVH.iconViewSpecificAim.setSelected(false);
                        }
                    }

                    SVH.iconViewSpecificAim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    break;

                case OBJECTIVE:
                    cImpactDomain objectiveDomain = (cImpactDomain)obj.getModelObject();
                    cObjectiveTreeViewHolder OVH = ((cObjectiveTreeViewHolder)viewHolder);

                    OVH.setPaddingLeft(40 * node.getLevel());
                    OVH.textViewObjective.setText(objectiveDomain.getObjectiveName());

/*
                    if(node.isLeaf()){
                        OVH.iconViewObjective.setVisibility(View.INVISIBLE);
                    }else{
                        OVH.iconViewObjective.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            OVH.iconViewObjective.setSelected(true);
                        } else {
                            OVH.iconViewObjective.setSelected(false);
                        }
                    }

                    OVH.iconViewObjective.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });
*/

                    break;

            }
        }
    }

    public static class cGoalTreeViewHolder extends cTreeViewHolder {
        ImageView iconViewGoal;
        TextView textViewGoal;
        TextView textViewCountGoal;

        private View treeView;

        public cGoalTreeViewHolder(final View treeViewHolder){
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.iconViewGoal = (ImageView)treeViewHolder.findViewById(R.id.goal_icon_id);
            //this.textViewCountOverallAim = (TextView)treeViewHolder.findViewById(R.id.txtCountGoal);
            this.textViewGoal = (TextView)treeViewHolder.findViewById(R.id.txtGoal);
        }
        public void setPaddingLeft(int paddingLeft)
        {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cSpecificAimTreeViewHolder extends cTreeViewHolder {
        ImageView iconViewSpecificAim;
        TextView textViewSpecificAim;
        TextView textViewCountSpecificAim;

        private View treeView;

        public cSpecificAimTreeViewHolder(View treeViewHolder){
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.iconViewSpecificAim = (ImageView)treeViewHolder.findViewById(R.id.specificationAim_icon_iv);
            this.textViewCountSpecificAim = (TextView)treeViewHolder.findViewById(R.id.txtCountSpecificAim);
            this.textViewSpecificAim = (TextView)treeViewHolder.findViewById(R.id.txtSpecificAim);
        }
        public void setPaddingLeft(int paddingLeft)
        {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cObjectiveTreeViewHolder extends cTreeViewHolder {
        //ImageView iconViewObjective;
        TextView textViewObjective;
        private View treeView;

        public cObjectiveTreeViewHolder(View treeViewHolder){
            super(treeViewHolder);
            treeView = treeViewHolder;
            //this.iconViewObjective = (ImageView)treeViewHolder.findViewById(R.id.objective_icon_iv);
            this.textViewObjective = (TextView)treeViewHolder.findViewById(R.id.txtObjective);
        }
        public void setPaddingLeft(int paddingLeft)
        {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }

    }
}
