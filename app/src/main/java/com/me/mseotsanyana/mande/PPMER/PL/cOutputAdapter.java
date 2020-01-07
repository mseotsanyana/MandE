package com.me.mseotsanyana.mande.PPMER.PL;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.mseotsanyana.mande.PPMER.BLL.domain.cOutcomeDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.domain.cOutputDomain;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cOutputAdapter extends cTreeAdapter {
    public static final int OUTCOME = 0;
    public static final int OUTPUT  = 1;

    public cOutputAdapter(Context context, List<cTreeModel> data, int expLevel){
        super(context, data, expLevel);
    }
/*
    public cOutputDomain getItem(int position) {
        return (cOutputDomain)treeModels.get(position).getModelObject();
    }
*/
    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType){
            case OUTCOME:
                view = inflater.inflate(R.layout.outcome_output_cardview, parent, false);
                viewHolder = new cOutcomeTreeViewHolder(view);
                break;
            case OUTPUT:
                view = inflater.inflate(R.layout.output_cardview, parent, false);
                viewHolder = new cOutputTreeViewHolder(view);
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
                case OUTCOME:
                    cOutcomeDomain outcomeDomain = (cOutcomeDomain)obj.getModelObject();
                    cOutcomeTreeViewHolder SVH = ((cOutcomeTreeViewHolder)viewHolder);

                    SVH.setPaddingLeft(40 * node.getLevel());
                    SVH.textViewOutcome.setText(outcomeDomain.getOutcomeName());
                    SVH.textViewCountOutcome.setText("the outcome has "+node.numberOfChildren()+" output(s):");


                    if(node.isLeaf()){
                        SVH.iconViewOutcome.setVisibility(View.INVISIBLE);
                    }else{
                        SVH.iconViewOutcome.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            SVH.iconViewOutcome.setSelected(true);
                        } else {
                            SVH.iconViewOutcome.setSelected(false);
                        }
                    }

                    SVH.iconViewOutcome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    break;

                case OUTPUT:
                    cOutputDomain outputDomain = (cOutputDomain) obj.getModelObject();
                    cOutputTreeViewHolder AVH = ((cOutputTreeViewHolder)viewHolder);

                    AVH.setPaddingLeft(40 * node.getLevel());
                    AVH.textViewOutput.setText(outputDomain.getOutputName());

                    break;
            }
        }
    }

    public static class cOutcomeTreeViewHolder extends cTreeViewHolder {
        ImageView iconViewOutcome;
        TextView textViewOutcome;
        TextView textViewCountOutcome;

        private View treeView;

        public cOutcomeTreeViewHolder(View treeViewHolder){
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.iconViewOutcome = (ImageView)treeViewHolder.findViewById(R.id.outcome_icon_id);
            this.textViewCountOutcome = (TextView)treeViewHolder.findViewById(R.id.txtCountOutcome);
            this.textViewOutcome = (TextView)treeViewHolder.findViewById(R.id.txtOutcome);
        }

        public void setPaddingLeft(int paddingLeft)
        {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cOutputTreeViewHolder extends cTreeViewHolder {
        TextView textViewOutput;

        private View treeView;

        public cOutputTreeViewHolder(final View treeViewHolder){
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.textViewOutput = (TextView)treeViewHolder.findViewById(R.id.txtName);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
