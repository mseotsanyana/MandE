package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.bmblibrary.BoomButtons.OnBMClickListener;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cTextOutsideCircleButton;
import com.me.mseotsanyana.bmblibrary.cBoomMenuButton;
import com.me.mseotsanyana.bmblibrary.cUtil;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.BLL.domain.logframe.cActivityDomain;
import com.me.mseotsanyana.mande.BLL.domain.logframe.cOutputDomain;
import com.me.mseotsanyana.mande.DAL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iActivityPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iInputPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iOutcomePresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iOutputPresenter;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewActivityListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewImpactListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewInputListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewOutcomeListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewOutputListener;
import com.me.mseotsanyana.mande.PL.ui.views.cImpactBodyView;
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

public class cActivityAdapter extends cTreeAdapter implements iViewOutputListener,
        iViewActivityListener, iViewInputListener {
    private static String TAG = cActivityAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PARENT = 0;
    private static final int CHILD = 1;

    private final String[] bmb_caption = {
            "Sub-LogFrame Impacts",
            "Child Outcomes",
            "Outputs",
            "Questions",
            "Assumptions/Risks"
    };

    private int[] bmb_imageid = {
            R.drawable.dashboard_impact,
            R.drawable.dashboard_outcome,
            R.drawable.dashboard_output,
            R.drawable.dashboard_question,
            R.drawable.dashboard_risk
    };

    private final iActivityPresenter.View activityPresenterView;
    private final iInputPresenter.View inputPresenterView;


    public cActivityAdapter(Context context, iActivityPresenter.View activityPresenterView,
                            iInputPresenter.View inputPresenterView, List<cTreeModel> data, int expLevel){
        super(context, data, expLevel);

        this.activityPresenterView = activityPresenterView;
        this.inputPresenterView = inputPresenterView;
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case PARENT:
                view = inflater.inflate(R.layout.activity_parent_cardview, parent,
                        false);
                viewHolder = new cActivityAdapter.cActivityParentViewHolder(view);
                break;
            case CHILD:
                view = inflater.inflate(R.layout.activity_child_placeholderview, parent,
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
                    cActivityModel activityParent = (cActivityModel) obj.getModelObject();
                    cActivityAdapter.cActivityParentViewHolder APH =
                            ((cActivityAdapter.cActivityParentViewHolder) viewHolder);

                    APH.setPaddingLeft(20 * node.getLevel());

                    final int parentBackgroundColor = (position % 2 == 0) ? R.color.list_even :
                            R.color.list_odd;
                    APH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            parentBackgroundColor));

                    APH.textViewNameCaption.setText("activity:");
                    APH.textViewName.setText(activityParent.getName());
                    APH.textViewDescription.setText(activityParent.getDescription());
                    APH.textViewStartDate.setText(sdf.format(activityParent.getStartDate()));
                    APH.textViewEndDate.setText(sdf.format(activityParent.getEndDate()));

                    APH.bmbMenu.clearBuilders();
                    for (int i = 0; i < APH.bmbMenu.getPiecePlaceEnum().pieceNumber(); i++) {
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
                        APH.bmbMenu.addBuilder(builder);
                    }

                    APH.bmbMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            APH.bmbMenu.boom();
                        }
                    });

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
                    APH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                    APH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!(APH.expandableLayout.isExpanded())) {
                                APH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
                            } else {
                                APH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                            }

                            APH.expandableLayout.toggle();
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
                    break;

                case CHILD:
                    ArrayList<Object> objects = (ArrayList<Object>) obj.getModelObject();
                    cOutputAdapter.cOutputChildViewHolder COH = ((cOutputAdapter.cOutputChildViewHolder) viewHolder);
                    COH.setPaddingLeft(20 * node.getLevel());

                    COH.outputPlaceholderView.removeAllViews();

                    for (int i = 0; i < objects.size(); i++) {

                        /* list of child logframe impacts */
                        if (objects.get(i) instanceof cImpactModel) {
                            if (i == 0) {
                                COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Outputs"));
                                COH.outputPlaceholderView.addView(new cOutputBodyView(
                                        context, this, (cOutputModel) objects.get(i)));
                                Log.d(TAG, "1. CHILD IMPACT: " + objects.get(i));
                            } else {
                                COH.outputPlaceholderView.addView(new cOutputBodyView(
                                        context, this, (cOutputModel) objects.get(i)));
                                Log.d(TAG, "2. CHILD IMPACT: " + objects.get(i));
                            }
                        }

                        /* list of child outcomes under this outcome */
                        if (objects.get(i) instanceof cOutputModel) {
                            if (i == 0) {
                                COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Outputs"));
                                COH.outputPlaceholderView.addView(new cOutputBodyView(
                                        context, this, (cOutputModel) objects.get(i)));
                                Log.d(TAG, "1. CHILD OUTCOME: " + objects.get(i));
                            } else {
                                COH.outputPlaceholderView.addView(new cOutputBodyView(
                                        context, this, (cOutputModel) objects.get(i)));
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

    public static class cActivityParentViewHolder extends cTreeViewHolder {
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

        private cActivityParentViewHolder(final View treeViewHolder) {
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

    public static class cActivityChildViewHolder extends cTreeViewHolder {
        cExpandablePlaceHolderView activityPlaceholderView;

        private View treeView;

        private cActivityChildViewHolder(View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.activityPlaceholderView = treeViewHolder.findViewById(R.id.activityPlaceholderView);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
