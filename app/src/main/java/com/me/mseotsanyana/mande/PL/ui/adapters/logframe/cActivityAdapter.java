package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.bmblibrary.BoomButtons.OnBMClickListener;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cTextOutsideCircleButton;
import com.me.mseotsanyana.bmblibrary.cBoomMenuButton;
import com.me.mseotsanyana.bmblibrary.cUtil;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;

import com.me.mseotsanyana.mande.BLL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iActivityPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iInputPresenter;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewActivityListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewInputListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewOutputListener;
import com.me.mseotsanyana.mande.PL.ui.views.cLogFrameHeaderView;
import com.me.mseotsanyana.mande.PL.ui.views.cOutputBodyView;
import com.me.mseotsanyana.mande.PL.ui.views.cQuestionBodyView;
import com.me.mseotsanyana.mande.PL.ui.views.cRaidBodyView;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandablePlaceHolderView;
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

public class cActivityAdapter extends cTreeAdapter implements iViewOutputListener,
        iViewActivityListener, iViewInputListener, Filterable {
    private static String TAG = cActivityAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PARENT = 0;
    private static final int CHILD = 1;

    private final iActivityPresenter.View activityPresenterView;
    private final iInputPresenter.View inputPresenterView;

    private List<cTreeModel> filteredTreeModels;

    public cActivityAdapter(Context context, iActivityPresenter.View activityPresenterView,
                            iInputPresenter.View inputPresenterView, List<cTreeModel> activityTree,
                            int expLevel){
        super(context, activityTree, expLevel);

        this.activityPresenterView = activityPresenterView;
        this.inputPresenterView = inputPresenterView;

        this.filteredTreeModels = activityTree;
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case PARENT:
                view = inflater.inflate(R.layout.component_parent_cardview, parent,
                        false);
                viewHolder = new cActivityAdapter.cActivityParentViewHolder(view);
                break;
            case CHILD:
                view = inflater.inflate(R.layout.component_child_cardview, parent,
                        false);
                viewHolder = new cActivityAdapter.cActivityChildViewHolder(view);
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
                    cActivityModel parentActivity = (cActivityModel) obj.getModelObject();
                    cActivityAdapter.cActivityParentViewHolder APH =
                            ((cActivityAdapter.cActivityParentViewHolder) viewHolder);

                    //final int parentBackgroundColor = (position % 2 == 0) ? R.color.list_even :
                    //        R.color.list_odd;
                    APH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            R.color.parent_body_colour));

                    APH.textViewParentCaption.setText(
                            context.getResources().getString(R.string.output_caption));
                    APH.textViewNameCaption.setText(
                            context.getResources().getString(R.string.activity_caption));
                    APH.textViewDescriptionCaption.setText(
                            context.getResources().getString(R.string.description_caption));
                    APH.textViewStartDateCaption.setText(
                            context.getResources().getString(R.string.startdate_caption));
                    APH.textViewEndDateCaption.setText(
                            context.getResources().getString(R.string.enddate_caption));

                    APH.textViewParent.setText(parentActivity.getOutputModel().getName());
                    APH.textViewName.setText(parentActivity.getName());
                    APH.textViewDescription.setText(parentActivity.getDescription());
                    APH.textViewStartDate.setText(sdf.format(parentActivity.getStartDate()));
                    APH.textViewEndDate.setText(sdf.format(parentActivity.getEndDate()));

                    /* the collapse and expansion of the parent logframe */
                    if (node.isLeaf()) {
                        APH.textViewExpandIcon.setVisibility(View.GONE);
                    } else {

                        APH.textViewExpandIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            APH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            APH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            APH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            APH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            APH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            APH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }

                    APH.textViewExpandIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    /* collapse and expansion of the details */
                    APH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    APH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));
                    APH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    APH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_details));
                    APH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                    /* icon for saving updated record */
                    APH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    APH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    APH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    APH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    APH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickUpdateLogFrame(position,
                            //        parentLogFrameModel);
                        }
                    });

                    /* icon for deleting a record */
                    APH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    APH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    APH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    APH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    APH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickDeleteLogFrame(position,
                            //       parentLogFrameModel.getLogFrameID());
                        }
                    });

                    /* icon for syncing a record */
                    APH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    APH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    APH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    APH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
                    APH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickSyncLogFrame(position,
                            //       parentLogFrameModel);
                        }
                    });

                    /* icon for creating a record */
                    APH.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
                    APH.textViewCreateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    APH.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    APH.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
                    APH.textViewCreateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickCreateSubLogFrame(
                            //       parentLogFrameModel.getLogFrameID(), new cLogFrameModel());
                        }
                    });

                    APH.setPaddingLeft(20 * node.getLevel());

                    break;

                case CHILD:
                    cActivityModel childActivity = (cActivityModel) obj.getModelObject();
                    cActivityAdapter.cActivityChildViewHolder ACH =
                            ((cActivityAdapter.cActivityChildViewHolder) viewHolder);

                    //final int childBackgroundColor = (position % 2 == 0) ? R.color.list_even :
                    //        R.color.list_odd;
                    ACH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            R.color.child_body_colour));

                    ACH.textViewParentCaption.setText(
                            context.getResources().getString(R.string.output_caption));
                    ACH.textViewNameCaption.setText(
                            context.getResources().getString(R.string.activity_caption));
                    ACH.textViewDescriptionCaption.setText(
                            context.getResources().getString(R.string.description_caption));
                    ACH.textViewStartDateCaption.setText(
                            context.getResources().getString(R.string.startdate_caption));
                    ACH.textViewEndDateCaption.setText(
                            context.getResources().getString(R.string.enddate_caption));

                    ACH.textViewParent.setText(childActivity.getOutputModel().getName());
                    ACH.textViewName.setText(childActivity.getName());
                    ACH.textViewDescription.setText(childActivity.getDescription());
                    ACH.textViewStartDate.setText(sdf.format(childActivity.getStartDate()));
                    ACH.textViewEndDate.setText(sdf.format(childActivity.getEndDate()));

                    /* collapse and expansion of the details */
                    ACH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    ACH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));
                    ACH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorAccent));
                    ACH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_details));
                    ACH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
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
                    ACH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    ACH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    ACH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorAccent));
                    ACH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
                    ACH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickSyncLogFrame(position,
                            //       parentLogFrameModel);
                        }
                    });

                    /* icon for deleting a record */
                    ACH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    ACH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    ACH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorAccent));
                    ACH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    ACH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickDeleteLogFrame(position,
                            //       parentLogFrameModel.getLogFrameID());
                        }
                    });

                    /* icon for saving updated record */
                    ACH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    ACH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    ACH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorAccent));
                    ACH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    ACH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickUpdateLogFrame(position,
                            //        parentLogFrameModel);
                        }
                    });

                    ACH.setPaddingLeft(20 * node.getLevel());

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
    public void onClickUpdateInput(int position, cInputModel inputModel) {

    }

    @Override
    public void onClickDeleteInput(int position, long outputID) {

    }

    @Override
    public void onClickSyncInput(int position, cInputModel inputModel) {

    }

    @Override
    public void onClickBMBInput(int index) {

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
                        if (((cActivityModel)treeModel.getModelObject()).getName().toLowerCase().
                                contains(charString.toLowerCase()) ||
                                ((cActivityModel)treeModel.getModelObject()).getOutputModel().
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

    public static class cActivityParentViewHolder extends cTreeViewHolder {
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
        private cActivityParentViewHolder(final View treeViewHolder) {
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

    public static class cActivityChildViewHolder extends cTreeViewHolder {
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

        private cActivityChildViewHolder(View treeViewHolder) {
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
//                    COH.outputPlaceholderView.removeAllViews();
//
//                    for (int i = 0; i < objects.size(); i++) {
//
//                        /* list of child logframe impacts */
//                        if (objects.get(i) instanceof cImpactModel) {
//                            if (i == 0) {
//                                COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
//                                        context, "List of Outputs"));
//                                COH.outputPlaceholderView.addView(new cOutputBodyView(
//                                        context, this, (cOutputModel) objects.get(i)));
//                                Log.d(TAG, "1. CHILD IMPACT: " + objects.get(i));
//                            } else {
//                                COH.outputPlaceholderView.addView(new cOutputBodyView(
//                                        context, this, (cOutputModel) objects.get(i)));
//                                Log.d(TAG, "2. CHILD IMPACT: " + objects.get(i));
//                            }
//                        }
//
//                        /* list of child outcomes under this outcome */
//                        if (objects.get(i) instanceof cOutputModel) {
//                            if (i == 0) {
//                                COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
//                                        context, "List of Outputs"));
//                                COH.outputPlaceholderView.addView(new cOutputBodyView(
//                                        context, this, (cOutputModel) objects.get(i)));
//                                Log.d(TAG, "1. CHILD OUTCOME: " + objects.get(i));
//                            } else {
//                                COH.outputPlaceholderView.addView(new cOutputBodyView(
//                                        context, this, (cOutputModel) objects.get(i)));
//                                Log.d(TAG, "2. CHILD OUTCOME: " + objects.get(i));
//                            }
//                        }
//
//                        /* list of outputs under this outcome */
//                        if (objects.get(i) instanceof cOutputModel) {
//                            if (i == 0) {
//                                COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
//                                        context, "List of Outputs"));
//                                COH.outputPlaceholderView.addView(new cOutputBodyView(
//                                        context, this, (cOutputModel) objects.get(i)));
//                                Log.d(TAG, "1. OUTPUT: " + objects.get(i));
//                            } else {
//                                COH.outputPlaceholderView.addView(new cOutputBodyView(
//                                        context, this, (cOutputModel) objects.get(i)));
//                                Log.d(TAG, "2. OUTPUT: " + objects.get(i));
//                            }
//                        }
//
//                        /* list of questions under this outcome */
//                        if (objects.get(i) instanceof cQuestionModel) {
//                            if (i == 0) {
//                                COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
//                                        context, "List of Questions"));
//                                COH.outputPlaceholderView.addView(new cQuestionBodyView(
//                                        context, (cQuestionModel) objects.get(i)));
//
//                                Log.d(TAG, "1. QUESTION: " + objects.get(i));
//                            } else {
//                                COH.outputPlaceholderView.addView(new cQuestionBodyView(context,
//                                        (cQuestionModel) objects.get(i)));
//                                Log.d(TAG, "2. QUESTION: " + objects.get(i));
//                            }
//                        }
//
//                        /* list of RAIDs under this outcome */
//                        if (objects.get(i) instanceof cRaidModel) {
//                            if (i == 0) {
//                                COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
//                                        context, "List of RAID"));
//                                COH.outputPlaceholderView.addView(new cRaidBodyView(
//                                        context, (cRaidModel) objects.get(i)));
//
//                                Log.d(TAG, "1. RAID: " + objects.get(i));
//                            } else {
//                                COH.outputPlaceholderView.addView(new cRaidBodyView(context,
//                                        (cRaidModel) objects.get(i)));
//                                Log.d(TAG, "2. RAID: " + objects.get(i));
//                            }
//                        }
//                    }