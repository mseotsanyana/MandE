package com.me.mseotsanyana.mande.PPMER.PL;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.mseotsanyana.mande.PPMER.BLL.cActivityDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cOutputDomain;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cActivityAdapter extends cTreeAdapter {
    public static final int OUTPUT = 0;
    public static final int ACTIVITY = 1;

    public cActivityAdapter(Context context, List<cTreeModel> data, int expLevel){
        super(context, data, expLevel);
    }
/*
    public cActivityDomain getItem(int position) {
        return (cActivityDomain)treeModels.get(position).getModelObject();
    }
*/
    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType){
            case OUTPUT:
                view = inflater.inflate(R.layout.output_activity_cardview, parent, false);
                viewHolder = new cOutputTreeViewHolder(view);
                break;
            case ACTIVITY:
                view = inflater.inflate(R.layout.activity_cardview, parent, false);
                viewHolder = new cActivityTreeViewHolder(view);
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
                case OUTPUT:
                    cOutputDomain outputDomain = (cOutputDomain)obj.getModelObject();
                    cOutputTreeViewHolder OVH = ((cOutputTreeViewHolder)viewHolder);

                    OVH.setPaddingLeft(40 * node.getLevel());
                    OVH.textViewOutput.setText(outputDomain.getOutputName());
                    OVH.textViewCountOutput.setText("the output has "+node.numberOfChildren()+" activities:");


                    if(node.isLeaf()){
                        OVH.iconViewOutput.setVisibility(View.INVISIBLE);
                    }else{
                        OVH.iconViewOutput.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            OVH.iconViewOutput.setSelected(true);
                        } else {
                            OVH.iconViewOutput.setSelected(false);
                        }
                    }

                    OVH.iconViewOutput.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    break;

                case ACTIVITY:
                    cActivityDomain activityDomain = (cActivityDomain) obj.getModelObject();
                    cActivityTreeViewHolder AVH = ((cActivityTreeViewHolder)viewHolder);

                    AVH.setPaddingLeft(40 * node.getLevel());
                    AVH.textViewActivity.setText(activityDomain.getActivityName());

                    break;
            }
        }
    }

    public static class cOutputTreeViewHolder extends cTreeViewHolder {
        ImageView iconViewOutput;
        TextView textViewOutput;
        TextView textViewCountOutput;

        private View treeView;

        public cOutputTreeViewHolder(View treeViewHolder){
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.iconViewOutput = (ImageView)treeViewHolder.findViewById(R.id.output_icon_id);
            this.textViewCountOutput = (TextView)treeViewHolder.findViewById(R.id.txtCountOutput);
            this.textViewOutput = (TextView)treeViewHolder.findViewById(R.id.txtOutput);
        }

        public void setPaddingLeft(int paddingLeft)
        {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cActivityTreeViewHolder extends cTreeViewHolder {
        TextView textViewActivity;

        private View treeView;

        public cActivityTreeViewHolder(final View treeViewHolder){
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.textViewActivity = (TextView)treeViewHolder.findViewById(R.id.txtName);
        }

        public void setPaddingLeft(int paddingLeft)
        {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
