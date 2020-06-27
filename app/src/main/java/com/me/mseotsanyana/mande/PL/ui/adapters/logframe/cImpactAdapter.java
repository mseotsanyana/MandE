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
import com.me.mseotsanyana.mande.DAL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iImpactPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iOutcomePresenter;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewImpactListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewOutcomeListener;
import com.me.mseotsanyana.mande.PL.ui.views.cLogFrameHeaderView;
import com.me.mseotsanyana.mande.PL.ui.views.cOutcomeBodyView;
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

public class cImpactAdapter extends cTreeAdapter implements iViewImpactListener,
        iViewOutcomeListener {
    private static String TAG = cImpactAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PARENT = 0;
    private static final int CHILD = 1;

    private final iImpactPresenter.View impactPresenterView;
    private final iOutcomePresenter.View outcomePresenterView;

    private final String[] bmb_caption = {
            "Child Impacts",
            "Outcomes",
            "Questions",
            "Assumptions/Risks"
    };

    private int[] bmb_imageid = {
            R.drawable.dashboard_impact,
            R.drawable.dashboard_outcome,
            R.drawable.dashboard_question,
            R.drawable.dashboard_risk
    };

    public cImpactAdapter(Context context, iImpactPresenter.View impactPresenterView,
                          iOutcomePresenter.View outcomePresenterView,
                          List<cTreeModel> impactTree, int expLevel) {
        super(context, impactTree, expLevel);

        this.impactPresenterView = impactPresenterView;
        this.outcomePresenterView = outcomePresenterView;
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case PARENT:
                view = inflater.inflate(R.layout.impact_parent_cardview, parent,
                        false);
                viewHolder = new cImpactParentViewHolder(view);
                break;
            case CHILD:
                view = inflater.inflate(R.layout.impact_child_placeholderview, parent,
                        false);
                viewHolder = new cImpactChildViewHolder(view);
                break;
            default:
                viewHolder = null;
                break;
        }

        return viewHolder;
    }

    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        cNode node = visibleNodes.get(position);
        cTreeModel obj = (cTreeModel) node.getObj();

        if (obj != null) {
            switch (obj.getType()) {
                case PARENT:
                    cImpactModel impactParent = (cImpactModel) obj.getModelObject();
                    cImpactParentViewHolder IPH = ((cImpactParentViewHolder) viewHolder);

                    IPH.setPaddingLeft(20 * node.getLevel());

                    final int parentBackgroundColor = (position % 2 == 0) ? R.color.list_even :
                            R.color.list_odd;
                    IPH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            parentBackgroundColor));

                    //IPH.textViewOrgCaption.setText("logical framework:");
                    //IPH.textViewOrganization.setText(impactParent.getLogFrameModel().
                    //        getName());
                    IPH.textViewNameCaption.setText("impact:");
                    IPH.textViewName.setText(impactParent.getName());
                    IPH.textViewDescription.setText(impactParent.getDescription());
                    IPH.textViewStartDate.setText(sdf.format(impactParent.getStartDate()));
                    IPH.textViewEndDate.setText(sdf.format(impactParent.getEndDate()));

                    IPH.bmbMenu.clearBuilders();
                    for (int i = 0; i < IPH.bmbMenu.getPiecePlaceEnum().pieceNumber(); i++) {
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
                        IPH.bmbMenu.addBuilder(builder);
                    }

                    IPH.bmbMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            IPH.bmbMenu.boom();
                        }
                    });

                    /* the collapse and expansion of the parent logframe */
                    if (node.isLeaf()) {
                        IPH.textViewExpandIcon.setVisibility(View.GONE);
                    } else {

                        IPH.textViewExpandIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            IPH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            IPH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            IPH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            IPH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            IPH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            IPH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }

                    IPH.textViewExpandIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    /* collapse and expansion of the details */
                    IPH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    IPH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));
                    IPH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    IPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                    IPH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!(IPH.expandableLayout.isExpanded())) {
                                IPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
                            } else {
                                IPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                            }

                            IPH.expandableLayout.toggle();
                        }
                    });

                    /* icon for saving updated record */
                    IPH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    IPH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    IPH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    IPH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    IPH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickUpdateLogFrame(position,
                            //        parentLogFrameModel);
                        }
                    });

                    /* icon for deleting a record */
                    IPH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    IPH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    IPH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    IPH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    IPH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickDeleteLogFrame(position,
                            //       parentLogFrameModel.getLogFrameID());
                        }
                    });

                    /* icon for syncing a record */
                    IPH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    IPH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    IPH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    IPH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
                    IPH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickSyncLogFrame(position,
                            //       parentLogFrameModel);
                        }
                    });

                    /* icon for creating a record */
                    IPH.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
                    IPH.textViewCreateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    IPH.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    IPH.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
                    IPH.textViewCreateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickCreateSubLogFrame(
                            //       parentLogFrameModel.getLogFrameID(), new cLogFrameModel());
                        }
                    });

                    break;

                case CHILD:
                    ArrayList<Object> objects = (ArrayList<Object>) obj.getModelObject();
                    cImpactChildViewHolder ICH = ((cImpactChildViewHolder) viewHolder);
                    ICH.setPaddingLeft(20 * node.getLevel());

                    ICH.impactPlaceholderView.removeAllViews();

                    for (int i = 0; i< objects.size(); i++){
                        /* list of child logframe impacts */
                        if (objects.get(i) instanceof cImpactModel) {
                            if (i == 0) {
                                Log.d(TAG, "1. IMPACT: " + objects.get(i));
                            } else {
                                Log.d(TAG, "2. IMPACT: " + objects.get(i));
                            }
                        }

                        /* list of outcomes under this impact */
                        if (objects.get(i) instanceof cOutcomeModel) {
                            if (i == 0) {
                                ICH.impactPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Outcomes"));
                                ICH.impactPlaceholderView.addView(new cOutcomeBodyView(
                                        context, this, (cOutcomeModel) objects.get(i)));
                                Log.d(TAG, "1. OUTCOME: " + objects.get(i));
                            } else {
                                ICH.impactPlaceholderView.addView(new cOutcomeBodyView(
                                        context,this, (cOutcomeModel) objects.get(i)));
                                Log.d(TAG, "2. OUTCOME: " + objects.get(i));
                            }
                        }

                        /* list of questions under this impact */
                        if (objects.get(i) instanceof cQuestionModel) {
                            if (i == 0) {
                                ICH.impactPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Questions"));
                                ICH.impactPlaceholderView.addView(new cQuestionBodyView(
                                        context, (cQuestionModel) objects.get(i)));

                                Log.d(TAG, "1. QUESTION: " + objects.get(i));
                            } else {
                                ICH.impactPlaceholderView.addView(new cQuestionBodyView(context,
                                        (cQuestionModel) objects.get(i)));
                                Log.d(TAG, "2. QUESTION: " + objects.get(i));
                            }
                        }

                        /* list of RAIDs under this impact */
                        if (objects.get(i) instanceof cRaidModel) {
                            if (i == 0) {
                                ICH.impactPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of RAID"));
                                ICH.impactPlaceholderView.addView(new cRaidBodyView(
                                        context, (cRaidModel) objects.get(i)));

                                Log.d(TAG, "1. RAID: " + objects.get(i));
                            } else {
                                ICH.impactPlaceholderView.addView(new cRaidBodyView(context,
                                        (cRaidModel) objects.get(i)));
                                Log.d(TAG, "2. RAID: " + objects.get(i));
                            }
                        }
                    }

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + obj.getType());
            }
        }
    }

    /* impact click events */

    @Override
    public void onClickBMBImpact(int index, int impactID) {
        this.impactPresenterView.onClickBMBImpact(index);
    }

    @Override
    public void onClickCreateSubImpact(long impactID, cImpactModel impactModel) {
        this.impactPresenterView.onClickCreateImpact(impactModel);
    }

    @Override
    public void onClickUpdateImpact(int position, cImpactModel impactModel) {
        this.impactPresenterView.onClickUpdateImpact(position, impactModel);
    }

    @Override
    public void onClickDeleteImpact(int position, long impactID) {
        this.impactPresenterView.onClickDeleteImpact(position, impactID);
    }

    @Override
    public void onClickSyncImpact(int position, cImpactModel impactModel) {
        this.impactPresenterView.onClickSyncImpact(impactModel);
    }
    /* outcome click events */

    @Override
    public void onClickUpdateOutcome(int position, cOutcomeModel outcomeModel) {
        this.outcomePresenterView.onClickUpdateOutcome(position, outcomeModel);
    }

    @Override
    public void onClickDeleteOutcome(int position, int outcomeID) {
        this.outcomePresenterView.onClickDeleteOutcome(position, outcomeID);
    }

    @Override
    public void onClickSyncOutcome(int position, cOutcomeModel outcomeModel) {
        this.outcomePresenterView.onClickSyncOutcome(outcomeModel);
    }

    @Override
    public void onClickBMBOutcome(int index) {
        this.outcomePresenterView.onClickBMBOutcome(index);
    }

    public static class cImpactParentViewHolder extends cTreeViewHolder {
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

        private cImpactParentViewHolder(final View treeViewHolder) {
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

    public static class cImpactChildViewHolder extends cTreeViewHolder {
        cExpandablePlaceHolderView impactPlaceholderView;

        private View treeView;

        private cImpactChildViewHolder(View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.impactPlaceholderView = treeViewHolder.findViewById(R.id.impactPlaceholderView);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
