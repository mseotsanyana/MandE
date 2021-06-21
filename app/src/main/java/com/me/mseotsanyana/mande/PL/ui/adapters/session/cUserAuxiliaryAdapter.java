package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.BLL.model.wpb.cMaterialModel;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iInputPresenter;
import com.me.mseotsanyana.mande.PL.ui.views.cActivityBodyView;
import com.me.mseotsanyana.mande.PL.ui.views.cLogFrameHeaderView;
import com.me.mseotsanyana.mande.PL.ui.views.cQuestionBodyView;
import com.me.mseotsanyana.mande.PL.ui.views.cUserBodyView;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandablePlaceHolderView;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cUserAuxiliaryAdapter extends cTreeAdapter {
    private static String TAG = cUserAuxiliaryAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PARENT = 0;
    private static final int CHILD  = 1;

    private final String[] bmb_caption = {
            "Sub-LogFrame Activities",
            "Human Resources",
            "Questions",
            "Journal Entries"
    };

    private int[] bmb_imageid = {
            R.drawable.dashboard_activity,
            R.drawable.dashboard_resource,
            R.drawable.dashboard_question,
            R.drawable.dashboard_budget
    };

    //private final iActivityPresenter.View activityPresenterView;
    //private final iInputPresenter.View inputPresenterView;


    public cUserAuxiliaryAdapter(Context context, iInputPresenter.View inputPresenterView,
                                 List<cTreeModel> inputTree, int expLevel){
        super(context, inputTree, expLevel);

        //this.inputPresenterView = inputPresenterView;
        //this.inputPresenterView = inputPresenterView;
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case PARENT:
                view = inflater.inflate(R.layout.resources_parent_cardview, parent,
                        false);
                viewHolder = new cMaterialParentViewHolder(context, view);
                break;
            case CHILD:
                view = inflater.inflate(R.layout.resources_child_placeholderview, parent,
                        false);
                viewHolder = new cHumanChildViewHolder(view);
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
                    cMaterialModel humanModel = (cMaterialModel) obj.getModelObject();
                    cMaterialParentViewHolder HPH =
                            ((cUserAuxiliaryAdapter.cMaterialParentViewHolder) viewHolder);

                    HPH.setPaddingLeft(20 * node.getLevel());

                    final int parentBackgroundColor = R.color.list_body;
                            //(position%2 == 0) ? R.color.list_even : R.color.list_odd;
                    HPH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            parentBackgroundColor));

                    HPH.textViewParentCaption.setText(R.string.activity_caption);
                    HPH.textViewParent.setText(humanModel.getActivityModel().getName());
                    HPH.textViewNameCaption.setText(R.string.input_caption);
                    HPH.textViewName.setText(humanModel.getResourceTypeModel().getName());
                    HPH.textViewNumberCaption.setText(R.string.input_number_caption);
                    HPH.textViewNumber.setText(String.valueOf(humanModel.getQuantity()));
                    HPH.textViewDescription.setText(humanModel.getResourceTypeModel().getDescription());
                    HPH.textViewStartDate.setText(sdf.format(humanModel.getStartDate()));
                    HPH.textViewEndDate.setText(sdf.format(humanModel.getEndDate()));

                    /* the collapse and expansion of the parent logframe */
                    if (node.isLeaf()) {
                        HPH.textViewExpandIcon.setVisibility(View.GONE);
                    } else {
                        HPH.textViewExpandIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            HPH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            HPH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            HPH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            HPH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            HPH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            HPH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }

                    /* toggling with an expand icon */
                    HPH.textViewExpandIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    /* toggling with a header */
                    HPH.linearLayoutHeader.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            expandOrCollapse(position);
                        }
                    });

                    /* collapse and expansion of the details */
                    HPH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    HPH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));
                    HPH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    HPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                    HPH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!(HPH.expandableLayout.isExpanded())) {
                                HPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
                            } else {
                                HPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                            }

                            HPH.expandableLayout.toggle();
                        }
                    });

                    /* icon for creating a record */
                    HPH.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
                    HPH.textViewCreateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    HPH.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    HPH.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
                    HPH.textViewCreateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //PVH.logFrameListener.onClickCreateSubLogFrame(parentLogFrame.getLogFrameID(), new cLogFrameModel());
                        }
                    });

                    /* icon for saving updated record */
                    HPH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    HPH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    HPH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    HPH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    HPH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
                        }
                    });

                    /* icon for deleting a record */
                    HPH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    HPH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    HPH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    HPH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    HPH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
                        }
                    });

                    /* icon for syncing a record */
                    HPH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    HPH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    HPH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    HPH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
                    HPH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
                        }
                    });

                    HPH.bmbMenu.clearBuilders();
                    for (int i = 0; i < HPH.bmbMenu.getPiecePlaceEnum().pieceNumber(); i++) {
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
                                        // HPH.logFrameListener.onClickBMBLogFrame(index, parentLogFrame.getLogFrameID());
                                    }
                                });
                        HPH.bmbMenu.addBuilder(builder);
                    }

                    HPH.bmbMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HPH.bmbMenu.boom();
                        }
                    });

                    /* setup the common details */
                    //HPH.setViewPager(parentLogFrame);

                    break;

                case CHILD:
                    ArrayList<Object> objects = (ArrayList<Object>) obj.getModelObject();
                    cUserAuxiliaryAdapter.cHumanChildViewHolder HCH = ((cHumanChildViewHolder) viewHolder);
                    HCH.setPaddingLeft(20 * node.getLevel());

                    HCH.resourcesPlaceholderView.removeAllViews();

                    for (int i = 0; i < objects.size(); i++) {
                        /* list of users under human group */
                        if (objects.get(i) instanceof cUserModel) {
                            if (i == 0) {
                                HCH.resourcesPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Users"));
                                //HCH.resourcesPlaceholderView.addView(new cUserBodyView(
                                //        context, null, (cUserModel) objects.get(i)));
                                Log.d(TAG, "1. CHILD USER: " + objects.get(i));
                            } else {
                                //HCH.resourcesPlaceholderView.addView(new cUserBodyView(
                                //        context, null, (cUserModel) objects.get(i)));
                                Log.d(TAG, "2. CHILD USER: " + objects.get(i));
                            }
                        }

                        /* list of sub-logframe activities under this input */
                        if (objects.get(i) instanceof cActivityModel) {
                            if (i == 0) {
                                HCH.resourcesPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Activities"));
                                HCH.resourcesPlaceholderView.addView(new cActivityBodyView(
                                        context, (cActivityModel) objects.get(i)));
                                Log.d(TAG, "1. CHILD ACTIVITY: " + objects.get(i));
                            } else {
                                HCH.resourcesPlaceholderView.addView(new cActivityBodyView(
                                        context, (cActivityModel) objects.get(i)));
                                Log.d(TAG, "2. CHILD ACTIVITY: " + objects.get(i));
                            }
                        }

                        /* list of questions under this input */
                        if (objects.get(i) instanceof cQuestionModel) {
                            if (i == 0) {
                                HCH.resourcesPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Questions"));
                                HCH.resourcesPlaceholderView.addView(new cQuestionBodyView(
                                        context, (cQuestionModel) objects.get(i)));

                                Log.d(TAG, "1. QUESTION: " + objects.get(i));
                            } else {
                                HCH.resourcesPlaceholderView.addView(new cQuestionBodyView(context,
                                        (cQuestionModel) objects.get(i)));
                                Log.d(TAG, "2. QUESTION: " + objects.get(i));
                            }
                        }

                        /* list of journal entries under this input */
                        if (objects.get(i) instanceof cQuestionModel) {
                            if (i == 0) {
                                HCH.resourcesPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Journal Entries"));
                                HCH.resourcesPlaceholderView.addView(new cQuestionBodyView(
                                        context, (cQuestionModel) objects.get(i)));

                                Log.d(TAG, "1. QUESTION: " + objects.get(i));
                            } else {
                                HCH.resourcesPlaceholderView.addView(new cQuestionBodyView(context,
                                        (cQuestionModel) objects.get(i)));
                                Log.d(TAG, "2. QUESTION: " + objects.get(i));
                            }
                        }
                    }
                    break;
            }
        }
    }

    public static class cMaterialParentViewHolder extends cTreeViewHolder {
        private CardView cardView;
        private cExpandableLayout expandableLayout;
        private LinearLayout linearLayoutHeader;

        private AppCompatTextView textViewExpandIcon;
        private AppCompatTextView textViewParentCaption;
        private AppCompatTextView textViewParent;
        private AppCompatTextView textViewName;
        private AppCompatTextView textViewNameCaption;
        private AppCompatTextView textViewNumberCaption;
        private AppCompatTextView textViewNumber;
        private AppCompatTextView textViewDescription;
        private AppCompatTextView textViewStartDate;
        private AppCompatTextView textViewEndDate;

        private cBoomMenuButton bmbMenu;
        private AppCompatTextView textViewSyncIcon;
        private AppCompatTextView textViewDeleteIcon;
        private AppCompatTextView textViewUpdateIcon;
        private AppCompatTextView textViewCreateIcon;
        private AppCompatTextView textViewDetailIcon;

        private View treeView;

        private cMaterialParentViewHolder(Context context, final View treeViewHolder) {
            super(treeViewHolder);
            this.treeView = treeViewHolder;

            this.cardView = treeViewHolder.findViewById(R.id.cardView);
            this.expandableLayout = treeViewHolder.findViewById(R.id.expandableLayout);
            this.linearLayoutHeader = treeViewHolder.findViewById(R.id.linearLayoutHeader);

            this.textViewExpandIcon = treeViewHolder.findViewById(R.id.textViewExpandIcon);
            this.textViewParentCaption = treeViewHolder.findViewById(R.id.textViewParentCaption);
            this.textViewParent = treeViewHolder.findViewById(R.id.textViewParent);
            this.textViewNameCaption = treeViewHolder.findViewById(R.id.textViewNameCaption);
            this.textViewName = treeViewHolder.findViewById(R.id.textViewName);
            this.textViewNumberCaption = treeViewHolder.findViewById(R.id.textViewNumberCaption);
            this.textViewNumber = treeViewHolder.findViewById(R.id.textViewNumber);
            this.textViewDescription = treeViewHolder.findViewById(R.id.textViewDescription);
            this.textViewStartDate = treeViewHolder.findViewById(R.id.textViewStartDate);
            this.textViewEndDate = treeViewHolder.findViewById(R.id.textViewEndDate);
            this.textViewDetailIcon = treeViewHolder.findViewById(R.id.textViewDetailIcon);
            this.textViewCreateIcon = treeViewHolder.findViewById(R.id.textViewCreateIcon);
            this.textViewUpdateIcon = treeViewHolder.findViewById(R.id.textViewUpdateIcon);
            this.textViewDeleteIcon = treeViewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewSyncIcon = treeViewHolder.findViewById(R.id.textViewSyncIcon);
            this.bmbMenu = treeViewHolder.findViewById(R.id.bmbMenu);


        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cHumanChildViewHolder extends cTreeViewHolder {
        cExpandablePlaceHolderView resourcesPlaceholderView;

        private View treeView;

        private cHumanChildViewHolder(View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.resourcesPlaceholderView = treeViewHolder.findViewById(R.id.resourcesPlaceholderView);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
