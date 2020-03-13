package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.mseotsanyana.mande.UTIL.BLL.cGoalDomain;
import com.me.mseotsanyana.mande.UTIL.BLL.cProjectDomain;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cProjectAdapter extends cTreeAdapter {
    public static final int GOAL    = 0;
    public static final int PROJECT = 1;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    public cProjectAdapter(Context context, List<cTreeModel> data, int expLevel){
        super(context, data, expLevel);
    }

    public cProjectDomain getItem(int position) {
        return (cProjectDomain)treeModels.get(position).getModelObject();
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType){
            case GOAL:
                view = inflater.inflate(R.layout.goal_project_cardview, parent, false);
                viewHolder = new cGoalTreeViewHolder(view);
                break;
            case PROJECT:
                view = inflater.inflate(R.layout.project_cardview, parent, false);
                viewHolder = new cProjectTreeViewHolder(view);
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
                case GOAL:
                    cGoalDomain goalDomain = (cGoalDomain)obj.getModelObject();
                    cGoalTreeViewHolder SVH = ((cGoalTreeViewHolder)viewHolder);

                    SVH.setPaddingLeft(20 * node.getLevel());
                    SVH.textViewGoal.setText(goalDomain.getGoalName());
                    SVH.textViewCountGoal.setText("the goal has "+node.numberOfChildren()+" project(s):");


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

                case PROJECT:
                    cProjectDomain projectDomain = (cProjectDomain) obj.getModelObject();
                    cProjectTreeViewHolder AVH = ((cProjectTreeViewHolder)viewHolder);

                    AVH.setPaddingLeft(20 * node.getLevel());
                    AVH.textViewProject.setText(projectDomain.getProjectName());
                    AVH.textViewDescription.setText(projectDomain.getProjectDescription());
                    AVH.textViewCountry.setText(projectDomain.getCountry());
                    AVH.textViewRegion.setText(projectDomain.getRegion());
                    AVH.textViewStatus.setText(String.valueOf(projectDomain.getProjectStatus()).toString());
                    AVH.textViewStartDate.setText(formatter.format(projectDomain.getStartDate()));
                    AVH.textViewCloseDate.setText(formatter.format(projectDomain.getCloseDate()));
                    AVH.textViewCreateDate.setText(formatter.format(projectDomain.getCreateDate()));
                    AVH.textViewManager.setText(String.valueOf(projectDomain.getProjectManagerID()));

                    break;
            }
        }
    }

    public static class cGoalTreeViewHolder extends cTreeViewHolder {
        ImageView iconViewGoal;
        TextView textViewGoal;
        TextView textViewCountGoal;

        private View treeView;

        public cGoalTreeViewHolder(View treeViewHolder){
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.iconViewGoal = (ImageView)treeViewHolder.findViewById(R.id.goal_icon_id);
            this.textViewCountGoal = (TextView)treeViewHolder.findViewById(R.id.txtCountGoal);
            this.textViewGoal= (TextView)treeViewHolder.findViewById(R.id.txtGoal);
        }

        public void setPaddingLeft(int paddingLeft)
        {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cProjectTreeViewHolder extends cTreeViewHolder {
        TextView textViewProject;
        TextView textViewDescription;
        TextView textViewCountry;
        TextView textViewRegion;
        TextView textViewStatus;
        TextView textViewStartDate;
        TextView textViewCloseDate;
        TextView textViewCreateDate;
        TextView textViewManager;


        private View treeView;

        public cProjectTreeViewHolder(final View treeViewHolder){
            super(treeViewHolder);
            treeView = treeViewHolder;

            this.textViewProject = (TextView)treeViewHolder.findViewById(R.id.txtName);
            this.textViewDescription = (TextView)treeViewHolder.findViewById(R.id.valDescription);
            this.textViewCountry = (TextView)treeViewHolder.findViewById(R.id.valCountry);
            this.textViewRegion = (TextView)treeViewHolder.findViewById(R.id.valRegion);
            this.textViewStatus = (TextView)treeViewHolder.findViewById(R.id.valStatus);
            this.textViewStartDate = (TextView)treeViewHolder.findViewById(R.id.valStartDate);
            this.textViewCloseDate = (TextView)treeViewHolder.findViewById(R.id.valCloseDate);
            this.textViewCreateDate = (TextView)treeViewHolder.findViewById(R.id.valCreateDate);
            this.textViewManager = (TextView)treeViewHolder.findViewById(R.id.valManager);
        }

        public void setPaddingLeft(int paddingLeft)
        {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
