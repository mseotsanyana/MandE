package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iActivityPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iOutputPresenter;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewActivityListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewOutcomeListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewOutputListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cOutputAdapter extends cTreeAdapter implements iViewOutcomeListener,
        iViewOutputListener, iViewActivityListener, Filterable {
    private static String TAG = cOutputAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PARENT = 0;
    private static final int CHILD = 1;

    private final iOutputPresenter.View outputPresenterView;
    private final iActivityPresenter.View activityPresenterView;

    private List<cTreeModel> filteredTreeModels;

    public cOutputAdapter(Context context, iOutputPresenter.View outputPresenterView,
                          iActivityPresenter.View activityPresenterView,
                          List<cTreeModel> outputTree, int expLevel){
        super(context, outputTree, expLevel);

        this.outputPresenterView = outputPresenterView;
        this.activityPresenterView = activityPresenterView;

        this.filteredTreeModels = outputTree;
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case PARENT:
                view = inflater.inflate(R.layout.component_parent_cardview, parent,
                        false);
                viewHolder = new cOutputAdapter.cOutputParentViewHolder(view);
                break;
            case CHILD:
                view = inflater.inflate(R.layout.component_child_cardview, parent,
                        false);
                viewHolder = new cOutputAdapter.cOutputChildViewHolder(view);
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
            switch (obj.getType()) {
                case PARENT:
                    cOutputModel parentOutput = (cOutputModel) obj.getModelObject();
                    cOutputAdapter.cOutputParentViewHolder OPH =
                            ((cOutputAdapter.cOutputParentViewHolder) viewHolder);

                    //final int parentBackgroundColor = (position % 2 == 0) ? R.color.list_even :
                    //        R.color.list_odd;
                    OPH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            R.color.parent_body_colour));

                    OPH.textViewParentCaption.setText(
                            context.getResources().getString(R.string.outcome_caption));
                    OPH.textViewNameCaption.setText(
                            context.getResources().getString(R.string.output_caption));
                    OPH.textViewDescriptionCaption.setText(
                            context.getResources().getString(R.string.description_caption));
                    OPH.textViewStartDateCaption.setText(
                            context.getResources().getString(R.string.startdate_caption));
                    OPH.textViewEndDateCaption.setText(
                            context.getResources().getString(R.string.enddate_caption));

                    OPH.textViewParent.setText(parentOutput.getOutcomeModel().getName());
                    OPH.textViewName.setText(parentOutput.getName());
                    OPH.textViewDescription.setText(parentOutput.getDescription());
                    OPH.textViewStartDate.setText(sdf.format(parentOutput.getStartDate()));
                    OPH.textViewEndDate.setText(sdf.format(parentOutput.getEndDate()));

                    /* the collapse and expansion of the parent logframe */
                    if (node.isLeaf()) {
                        OPH.textViewExpandIcon.setVisibility(View.GONE);
                    } else {

                        OPH.textViewExpandIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            OPH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            OPH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            OPH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            OPH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            OPH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            OPH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }

                    OPH.textViewExpandIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    /* collapse and expansion of the details */
                    OPH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    OPH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));
                    OPH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    OPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_details));
                    OPH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                    /* icon for saving updated record */
                    OPH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    OPH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    OPH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    OPH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    OPH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickUpdateLogFrame(position,
                            //        parentLogFrameModel);
                        }
                    });

                    /* icon for deleting a record */
                    OPH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    OPH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    OPH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    OPH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    OPH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickDeleteLogFrame(position,
                            //       parentLogFrameModel.getLogFrameID());
                        }
                    });

                    /* icon for syncing a record */
                    OPH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    OPH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    OPH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    OPH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
                    OPH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickSyncLogFrame(position,
                            //       parentLogFrameModel);
                        }
                    });

                    /* icon for creating a record */
                    OPH.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
                    OPH.textViewCreateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    OPH.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    OPH.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
                    OPH.textViewCreateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickCreateSubLogFrame(
                            //       parentLogFrameModel.getLogFrameID(), new cLogFrameModel());
                        }
                    });

                    OPH.setPaddingLeft(20 * node.getLevel());

                    break;

                case CHILD:
                    cOutputModel childOutput = (cOutputModel) obj.getModelObject();
                    cOutputAdapter.cOutputChildViewHolder OCH =
                            ((cOutputAdapter.cOutputChildViewHolder) viewHolder);

                    //final int childBackgroundColor = (position % 2 == 0) ? R.color.list_even :
                    //        R.color.list_odd;
                    OCH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            R.color.child_body_colour));

                    OCH.textViewParentCaption.setText(
                            context.getResources().getString(R.string.outcome_caption));
                    OCH.textViewNameCaption.setText(
                            context.getResources().getString(R.string.output_caption));
                    OCH.textViewDescriptionCaption.setText(
                            context.getResources().getString(R.string.description_caption));
                    OCH.textViewStartDateCaption.setText(
                            context.getResources().getString(R.string.startdate_caption));
                    OCH.textViewEndDateCaption.setText(
                            context.getResources().getString(R.string.enddate_caption));

                    OCH.textViewParent.setText(childOutput.getOutcomeModel().getName());
                    OCH.textViewName.setText(childOutput.getName());
                    OCH.textViewDescription.setText(childOutput.getDescription());
                    OCH.textViewStartDate.setText(sdf.format(childOutput.getStartDate()));
                    OCH.textViewEndDate.setText(sdf.format(childOutput.getEndDate()));

                    /* collapse and expansion of the details */
                    OCH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    OCH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));
                    OCH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorAccent));
                    OCH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_details));
                    OCH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*cOutcomeModel[] outcomeModels =
                                    new cOutcomeModel[childImpact.getOutcomeModelSet().size()];
                            childImpact.getOutcomeModelSet().toArray(outcomeModels);
                            cQuestionModel[] questionModels =
                                    new cQuestionModel[childImpact.getQuestionModelSet().size()];
                            childImpact.getQuestionModelSet().toArray(questionModels);

                            ICH.impactListener.onClickDetailImpact(outcomeModels, questionModels);*/

                            /* set of outcomes under the impact
                            ArrayList<cOutcomeModel> outcomes = new ArrayList<>(childImpact.getOutcomeModelSet());*/

                            /* set of questions under the impact
                            ArrayList<cQuestionModel> questions = new ArrayList<>(childImpact.getQuestionModelSet());*/

                            /* set of raids under the impact
                            ArrayList<cRaidModel> raids = new ArrayList<>(childImpact.getRaidModelSet());*/
                        }
                    });

                    /* icon for syncing a record */
                    OCH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    OCH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    OCH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorAccent));
                    OCH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
                    OCH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickSyncLogFrame(position,
                            //       parentLogFrameModel);
                        }
                    });

                    /* icon for deleting a record */
                    OCH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    OCH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    OCH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorAccent));
                    OCH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    OCH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickDeleteLogFrame(position,
                            //       parentLogFrameModel.getLogFrameID());
                        }
                    });

                    /* icon for saving updated record */
                    OCH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    OCH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    OCH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorAccent));
                    OCH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    OCH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickUpdateLogFrame(position,
                            //        parentLogFrameModel);
                        }
                    });

                    OCH.setPaddingLeft(20 * node.getLevel());

                    break;
            }
        }
    }

    @Override
    public void onClickUpdateActivity(int position, cActivityModel activityModel) {

    }

    @Override
    public void onClickDeleteActivity(int position, long outputID) {

    }

    @Override
    public void onClickSyncActivity(int position, cActivityModel activityModel) {

    }

    @Override
    public void onClickBMBActivity(int index) {

    }

    @Override
    public void onClickUpdateOutcome(cOutcomeModel outcomeModel, int position) {

    }

    @Override
    public void onClickDeleteOutcome(long outcomeID, int position) {

    }

    @Override
    public void onClickSyncOutcome(cOutcomeModel outcomeModel, int position) {

    }

    @Override
    public void onClickBMBOutcome(int index) {

    }

    @Override
    public void onClickUpdateOutput(int position, cOutputModel outputModel) {

    }

    @Override
    public void onClickDeleteOutput(int position, long outputID) {

    }

    @Override
    public void onClickSyncOutput(int position, cOutputModel outputModel) {

    }

    @Override
    public void onClickBMBOutput(int index) {

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredTreeModels = getTreeModel();
                } else {

                    ArrayList<cTreeModel> filteredList = new ArrayList<>();
                    for (cTreeModel treeModel : getTreeModel()) {
                        if (((cOutputModel)treeModel.getModelObject()).getName().toLowerCase().
                                contains(charString.toLowerCase()) ||
                                ((cOutputModel)treeModel.getModelObject()).getOutcomeModel().
                                        getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(treeModel);
                        }
                    }

                    filteredTreeModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredTreeModels.size();
                filterResults.values = filteredTreeModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //assert (ArrayList<cTreeModel>) filterResults.values != null;
                filteredTreeModels = (ArrayList<cTreeModel>) filterResults.values;

                try {
                    notifyTreeModelChanged(filteredTreeModels);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static class cOutputParentViewHolder extends cTreeViewHolder {
        private View treeView;
        private CardView cardView;
        private AppCompatTextView textViewExpandIcon;

        private AppCompatTextView textViewParentCaption;
        private AppCompatTextView textViewNameCaption;
        private AppCompatTextView textViewDescriptionCaption;
        private AppCompatTextView textViewStartDateCaption;
        private AppCompatTextView textViewEndDateCaption;

        private AppCompatTextView textViewParent;
        private AppCompatTextView textViewName;
        private AppCompatTextView textViewDescription;
        private AppCompatTextView textViewStartDate;
        private AppCompatTextView textViewEndDate;

        private AppCompatTextView textViewDetailIcon;
        private AppCompatTextView textViewSyncIcon;
        private AppCompatTextView textViewDeleteIcon;
        private AppCompatTextView textViewUpdateIcon;
        private AppCompatTextView textViewCreateIcon;

        private cOutputParentViewHolder(final View treeViewHolder) {
            super(treeViewHolder);
            this.treeView = treeViewHolder;

            this.cardView = treeViewHolder.findViewById(R.id.cardView);
            this.textViewExpandIcon = treeViewHolder.findViewById(R.id.textViewExpandIcon);

            this.textViewParentCaption = treeViewHolder.findViewById(R.id.textViewParentCaption);
            this.textViewNameCaption = treeViewHolder.findViewById(R.id.textViewNameCaption);
            this.textViewDescriptionCaption = treeViewHolder.findViewById(R.id.textViewDescriptionCaption);
            this.textViewStartDateCaption = treeViewHolder.findViewById(R.id.textViewStartDateCaption);
            this.textViewEndDateCaption = treeViewHolder.findViewById(R.id.textViewEndDateCaption);
            this.textViewParent = treeViewHolder.findViewById(R.id.textViewParent);
            this.textViewName = treeViewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = treeViewHolder.findViewById(R.id.textViewDescription);
            this.textViewStartDate = treeViewHolder.findViewById(R.id.textViewStartDate);
            this.textViewEndDate = treeViewHolder.findViewById(R.id.textViewEndDate);
            this.textViewDetailIcon = treeViewHolder.findViewById(R.id.textViewDetailIcon);
            this.textViewSyncIcon = treeViewHolder.findViewById(R.id.textViewSyncIcon);
            this.textViewDeleteIcon = treeViewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewUpdateIcon = treeViewHolder.findViewById(R.id.textViewUpdateIcon);
            this.textViewCreateIcon = treeViewHolder.findViewById(R.id.textViewCreateIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cOutputChildViewHolder extends cTreeViewHolder {
        private View treeView;
        private CardView cardView;

        private AppCompatTextView textViewParentCaption;
        private AppCompatTextView textViewNameCaption;
        private AppCompatTextView textViewDescriptionCaption;
        private AppCompatTextView textViewStartDateCaption;
        private AppCompatTextView textViewEndDateCaption;

        private AppCompatTextView textViewParent;
        private AppCompatTextView textViewName;
        private AppCompatTextView textViewDescription;
        private AppCompatTextView textViewStartDate;
        private AppCompatTextView textViewEndDate;

        private AppCompatTextView textViewDetailIcon;
        private AppCompatTextView textViewSyncIcon;
        private AppCompatTextView textViewDeleteIcon;
        private AppCompatTextView textViewUpdateIcon;

        private cOutputChildViewHolder(View treeViewHolder) {
            super(treeViewHolder);
            this.treeView = treeViewHolder;
            this.cardView = treeViewHolder.findViewById(R.id.cardView);

            this.textViewParentCaption = treeViewHolder.findViewById(R.id.textViewParentCaption);
            this.textViewNameCaption = treeViewHolder.findViewById(R.id.textViewNameCaption);
            this.textViewDescriptionCaption = treeViewHolder.findViewById(R.id.textViewDescriptionCaption);
            this.textViewStartDateCaption = treeViewHolder.findViewById(R.id.textViewStartDateCaption);
            this.textViewEndDateCaption = treeViewHolder.findViewById(R.id.textViewEndDateCaption);
            this.textViewParent = treeViewHolder.findViewById(R.id.textViewParent);
            this.textViewName = treeViewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = treeViewHolder.findViewById(R.id.textViewDescription);
            this.textViewStartDate = treeViewHolder.findViewById(R.id.textViewStartDate);
            this.textViewEndDate = treeViewHolder.findViewById(R.id.textViewEndDate);
            this.textViewDetailIcon = treeViewHolder.findViewById(R.id.textViewDetailIcon);
            this.textViewSyncIcon = treeViewHolder.findViewById(R.id.textViewSyncIcon);
            this.textViewDeleteIcon = treeViewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewUpdateIcon = treeViewHolder.findViewById(R.id.textViewUpdateIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}


//    ArrayList<Object> objects = (ArrayList<Object>) obj.getModelObject();
//    cOutputAdapter.cOutputChildViewHolder COH = ((cOutputAdapter.cOutputChildViewHolder) viewHolder);
//                    COH.setPaddingLeft(20 * node.getLevel());
//
//                            COH.outputPlaceholderView.removeAllViews();
//
//                            for (int i = 0; i < objects.size(); i++) {
//
//        /* list of child logframe impacts */
//        if (objects.get(i) instanceof cOutcomeModel) {
//        if (i == 0) {
//        COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
//        context, "List of Impacts"));
//        COH.outputPlaceholderView.addView(new cOutcomeBodyView(
//        context, this, (cOutcomeModel) objects.get(i)));
//        Log.d(TAG, "1. CHILD IMPACT: " + objects.get(i));
//        } else {
//        COH.outputPlaceholderView.addView(new cOutcomeBodyView(
//        context, this, (cOutcomeModel) objects.get(i)));
//        Log.d(TAG, "2. CHILD IMPACT: " + objects.get(i));
//        }
//        }
//
//        /* list of child outcomes under this outcome */
//        if (objects.get(i) instanceof cOutcomeModel) {
//        if (i == 0) {
//        COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
//        context, "List of Outputs"));
//        COH.outputPlaceholderView.addView(new cOutcomeBodyView(
//        context, this, (cOutcomeModel) objects.get(i)));
//        Log.d(TAG, "1. CHILD OUTCOME: " + objects.get(i));
//        } else {
//        COH.outputPlaceholderView.addView(new cOutcomeBodyView(
//        context, this, (cOutcomeModel) objects.get(i)));
//        Log.d(TAG, "2. CHILD OUTCOME: " + objects.get(i));
//        }
//        }
//
//        /* list of outputs under this outcome */
//        if (objects.get(i) instanceof cOutputModel) {
//        if (i == 0) {
//        COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
//        context, "List of Outputs"));
//        COH.outputPlaceholderView.addView(new cOutputBodyView(
//        context, this, (cOutputModel) objects.get(i)));
//        Log.d(TAG, "1. OUTPUT: " + objects.get(i));
//        } else {
//        COH.outputPlaceholderView.addView(new cOutputBodyView(
//        context, this, (cOutputModel) objects.get(i)));
//        Log.d(TAG, "2. OUTPUT: " + objects.get(i));
//        }
//        }
//
//        /* list of questions under this outcome */
//        if (objects.get(i) instanceof cQuestionModel) {
//        if (i == 0) {
//        COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
//        context, "List of Questions"));
//        COH.outputPlaceholderView.addView(new cQuestionBodyView(
//        context, (cQuestionModel) objects.get(i)));
//
//        Log.d(TAG, "1. QUESTION: " + objects.get(i));
//        } else {
//        COH.outputPlaceholderView.addView(new cQuestionBodyView(context,
//        (cQuestionModel) objects.get(i)));
//        Log.d(TAG, "2. QUESTION: " + objects.get(i));
//        }
//        }
//
//        /* list of RAIDs under this outcome */
//        if (objects.get(i) instanceof cRaidModel) {
//        if (i == 0) {
//        COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
//        context, "List of RAID"));
//        COH.outputPlaceholderView.addView(new cRaidBodyView(
//        context, (cRaidModel) objects.get(i)));
//
//        Log.d(TAG, "1. RAID: " + objects.get(i));
//        } else {
//        COH.outputPlaceholderView.addView(new cRaidBodyView(context,
//        (cRaidModel) objects.get(i)));
//        Log.d(TAG, "2. RAID: " + objects.get(i));
//        }
//        }
//        }