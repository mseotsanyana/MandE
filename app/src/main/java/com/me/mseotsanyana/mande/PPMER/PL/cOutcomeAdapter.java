package com.me.mseotsanyana.mande.PPMER.PL;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.mseotsanyana.mande.PPMER.BLL.cProjectDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cOutcomeDomain;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cOutcomeAdapter extends cTreeAdapter {
    public static final int PROJECT = 0;
    public static final int OUTCOME = 1;

    public cOutcomeAdapter(Context context, List<cTreeModel> data, int expLevel){
        super(context, data, expLevel);
    }
/*
    public cOutcomeDomain getItem(int position) {
        return (cOutcomeDomain)treeModels.get(position).getModelObject();
    }
*/
    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType){
            case PROJECT:
                view = inflater.inflate(R.layout.project_outcome_cardview, parent, false);
                viewHolder = new cProjectTreeViewHolder(view);
                break;
            case OUTCOME:
                view = inflater.inflate(R.layout.outcome_cardview, parent, false);
                viewHolder = new cOutcomeTreeViewHolder(view);
                break;
            default:
                viewHolder = null;
                break;
        }

        return viewHolder;
    }

    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, final int position){
        cNode node = visibleNodes.get(position);

        cTreeModel obj = (cTreeModel) node.getObj();
        if (obj != null){
            switch (obj.getType()){
                case PROJECT:
                    cProjectDomain projectDomain = (cProjectDomain)obj.getModelObject();
                    cProjectTreeViewHolder SVH = ((cProjectTreeViewHolder)viewHolder);

                    SVH.setPaddingLeft(40 * node.getLevel());
                    SVH.textViewProject.setText(projectDomain.getProjectName());
                    SVH.textViewCountProject.setText("the project has "+node.numberOfChildren()+" outcome(s):");


                    if(node.isLeaf()){
                        SVH.iconViewProject.setVisibility(View.INVISIBLE);
                    }else{
                        SVH.iconViewProject.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            SVH.iconViewProject.setSelected(true);
                        } else {
                            SVH.iconViewProject.setSelected(false);
                        }
                    }

                    SVH.iconViewProject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    break;

                case OUTCOME:
                    cOutcomeDomain outcomeDomain = (cOutcomeDomain) obj.getModelObject();
                    cOutcomeTreeViewHolder AVH = ((cOutcomeTreeViewHolder)viewHolder);

                    AVH.setPaddingLeft(40 * node.getLevel());
                    AVH.textViewOutcome.setText(outcomeDomain.getOutcomeName());

                    break;
            }
        }
    }

    public static class cProjectTreeViewHolder extends cTreeViewHolder {
        ImageView iconViewProject;
        TextView textViewProject;
        TextView textViewCountProject;

        private View treeView;

        public cProjectTreeViewHolder(View treeViewHolder){
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.iconViewProject = (ImageView)treeViewHolder.findViewById(R.id.project_icon_id);
            this.textViewCountProject = (TextView)treeViewHolder.findViewById(R.id.txtCountProject);
            this.textViewProject= (TextView)treeViewHolder.findViewById(R.id.txtProject);
        }

        public void setPaddingLeft(int paddingLeft)
        {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cOutcomeTreeViewHolder extends cTreeViewHolder {
        TextView textViewOutcome;

        private View treeView;

        public cOutcomeTreeViewHolder(final View treeViewHolder){
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.textViewOutcome = (TextView)treeViewHolder.findViewById(R.id.txtName);
        }

        public void setPaddingLeft(int paddingLeft)
        {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
