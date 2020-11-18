package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iActivityPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iOutputPresenter;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewActivityListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewOutcomeListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewOutputListener;
import com.me.mseotsanyana.mande.PL.ui.views.cLogFrameHeaderView;
import com.me.mseotsanyana.mande.PL.ui.views.cOutcomeBodyView;
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

public class cOutputAdapter extends cTreeAdapter implements iViewOutcomeListener,
        iViewOutputListener, iViewActivityListener {
    private static String TAG = cOutputAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PARENT = 0;
    private static final int CHILD = 1;

    private final String[] bmb_caption = {
            "Sub-LogFrame Outcomes",
            "Child Outputs",
            "Activities",
            "Questions",
            "Assumptions/Risks"
    };

    private int[] bmb_imageid = {
            R.drawable.dashboard_outcome,
            R.drawable.dashboard_output,
            R.drawable.dashboard_activity,
            R.drawable.dashboard_question,
            R.drawable.dashboard_risk
    };

    private final iOutputPresenter.View outputPresenterView;
    private final iActivityPresenter.View activityPresenterView;

    public cOutputAdapter(Context context, iOutputPresenter.View outputPresenterView,
                          iActivityPresenter.View activityPresenterView,
                          List<cTreeModel> outputTree, int expLevel){
        super(context, outputTree, expLevel);

        this.outputPresenterView = outputPresenterView;
        this.activityPresenterView = activityPresenterView;
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
        switch (viewType) {
            case PARENT:
                view = inflater.inflate(R.layout.output_parent_cardview, parent,
                        false);
                viewHolder = new cOutputAdapter.cOutputParentViewHolder(view);
                break;
            case CHILD:
                view = inflater.inflate(R.layout.output_child_placeholderview, parent,
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
                    cOutputModel outputParent = (cOutputModel) obj.getModelObject();
                    cOutputAdapter.cOutputParentViewHolder OPH =
                            ((cOutputAdapter.cOutputParentViewHolder) viewHolder);

                    OPH.setPaddingLeft(20 * node.getLevel());

                    final int parentBackgroundColor = (position % 2 == 0) ? R.color.list_even :
                            R.color.list_odd;
                    OPH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            parentBackgroundColor));

                    OPH.textViewNameCaption.setText("output:");
                    OPH.textViewName.setText(outputParent.getName());
                    OPH.textViewDescription.setText(outputParent.getDescription());
                    OPH.textViewStartDate.setText(sdf.format(outputParent.getStartDate()));
                    OPH.textViewEndDate.setText(sdf.format(outputParent.getEndDate()));

                    OPH.bmbMenu.clearBuilders();
                    for (int i = 0; i < OPH.bmbMenu.getPiecePlaceEnum().pieceNumber(); i++) {
                        cTextOutsideCircleButton.Builder builder = new cTextOutsideCircleButton
                                .Builder()
                                .isRound(false)
                                .shadowCornerRadius(cUtil.dp2px(20))
                                .buttonCornerRadius(cUtil.dp2px(20))
                                .normalColor(Color.LTGRAY)
                                .pieceColor(context.getColor(R.color.colorPrimaryDark))
                                .normalImageRes(bmb_imageid[i])
                                .normalText(bmb_caption[i])
                                .listener(new OnBMClickListener() {
                                    @Override
                                    public void onBoomButtonClick(int index) {
                                        /* when the boom-button is clicked. */
                                        //IPH.logFrameListener.onClickBoomMenu(index);
                                    }
                                });
                        OPH.bmbMenu.addBuilder(builder);
                    }

                    OPH.bmbMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            OPH.bmbMenu.boom();
                        }
                    });

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
                    OPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                    OPH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!(OPH.expandableLayout.isExpanded())) {
                                OPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
                            } else {
                                OPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                            }

                            OPH.expandableLayout.toggle();
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
                    break;

                case CHILD:
                    ArrayList<Object> objects = (ArrayList<Object>) obj.getModelObject();
                    cOutputAdapter.cOutputChildViewHolder COH = ((cOutputAdapter.cOutputChildViewHolder) viewHolder);
                    COH.setPaddingLeft(20 * node.getLevel());

                    COH.outputPlaceholderView.removeAllViews();

                    for (int i = 0; i < objects.size(); i++) {

                        /* list of child logframe impacts */
                        if (objects.get(i) instanceof cOutcomeModel) {
                            if (i == 0) {
                                COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Impacts"));
                                COH.outputPlaceholderView.addView(new cOutcomeBodyView(
                                        context, this, (cOutcomeModel) objects.get(i)));
                                Log.d(TAG, "1. CHILD IMPACT: " + objects.get(i));
                            } else {
                                COH.outputPlaceholderView.addView(new cOutcomeBodyView(
                                        context, this, (cOutcomeModel) objects.get(i)));
                                Log.d(TAG, "2. CHILD IMPACT: " + objects.get(i));
                            }
                        }

                        /* list of child outcomes under this outcome */
                        if (objects.get(i) instanceof cOutcomeModel) {
                            if (i == 0) {
                                COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Outputs"));
                                COH.outputPlaceholderView.addView(new cOutcomeBodyView(
                                        context, this, (cOutcomeModel) objects.get(i)));
                                Log.d(TAG, "1. CHILD OUTCOME: " + objects.get(i));
                            } else {
                                COH.outputPlaceholderView.addView(new cOutcomeBodyView(
                                        context, this, (cOutcomeModel) objects.get(i)));
                                Log.d(TAG, "2. CHILD OUTCOME: " + objects.get(i));
                            }
                        }

                        /* list of outputs under this outcome */
                        if (objects.get(i) instanceof cOutputModel) {
                            if (i == 0) {
                                COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Outputs"));
                                COH.outputPlaceholderView.addView(new cOutputBodyView(
                                        context, this, (cOutputModel) objects.get(i)));
                                Log.d(TAG, "1. OUTPUT: " + objects.get(i));
                            } else {
                                COH.outputPlaceholderView.addView(new cOutputBodyView(
                                        context, this, (cOutputModel) objects.get(i)));
                                Log.d(TAG, "2. OUTPUT: " + objects.get(i));
                            }
                        }

                        /* list of questions under this outcome */
                        if (objects.get(i) instanceof cQuestionModel) {
                            if (i == 0) {
                                COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Questions"));
                                COH.outputPlaceholderView.addView(new cQuestionBodyView(
                                        context, (cQuestionModel) objects.get(i)));

                                Log.d(TAG, "1. QUESTION: " + objects.get(i));
                            } else {
                                COH.outputPlaceholderView.addView(new cQuestionBodyView(context,
                                        (cQuestionModel) objects.get(i)));
                                Log.d(TAG, "2. QUESTION: " + objects.get(i));
                            }
                        }

                        /* list of RAIDs under this outcome */
                        if (objects.get(i) instanceof cRaidModel) {
                            if (i == 0) {
                                COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of RAID"));
                                COH.outputPlaceholderView.addView(new cRaidBodyView(
                                        context, (cRaidModel) objects.get(i)));

                                Log.d(TAG, "1. RAID: " + objects.get(i));
                            } else {
                                COH.outputPlaceholderView.addView(new cRaidBodyView(context,
                                        (cRaidModel) objects.get(i)));
                                Log.d(TAG, "2. RAID: " + objects.get(i));
                            }
                        }
                    }
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

    public static class cOutputParentViewHolder extends cTreeViewHolder {
        private CardView cardView;
        private cExpandableLayout expandableLayout;

        private AppCompatTextView textViewExpandIcon;
        private AppCompatTextView textViewNameCaption;
        private AppCompatTextView textViewName;
        private AppCompatTextView textViewDescription;
        private AppCompatTextView textViewStartDate;
        private AppCompatTextView textViewEndDate;

        private AppCompatTextView textViewDetailIcon;
        private cBoomMenuButton bmbMenu;
        private AppCompatTextView textViewSyncIcon;
        private AppCompatTextView textViewDeleteIcon;
        private AppCompatTextView textViewUpdateIcon;
        private AppCompatTextView textViewCreateIcon;

        private View treeView;

        private cOutputParentViewHolder(final View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;

            this.cardView = treeViewHolder.findViewById(R.id.cardView);
            this.expandableLayout = treeViewHolder.findViewById(R.id.expandableLayout);
            this.textViewExpandIcon = treeViewHolder.findViewById(R.id.textViewExpandIcon);
            this.bmbMenu = treeViewHolder.findViewById(R.id.bmbMenu);
            this.textViewNameCaption = treeViewHolder.findViewById(R.id.textViewNameCaption);
            //this.textViewOrganization = treeViewHolder.findViewById(R.id.textViewOrganization);
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
        cExpandablePlaceHolderView outputPlaceholderView;

        private View treeView;

        private cOutputChildViewHolder(View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.outputPlaceholderView = treeViewHolder.findViewById(R.id.outputPlaceholderView);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
