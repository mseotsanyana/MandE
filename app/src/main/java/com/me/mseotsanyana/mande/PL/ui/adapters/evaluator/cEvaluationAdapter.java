package com.me.mseotsanyana.mande.PL.ui.adapters.evaluator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.bmblibrary.BoomButtons.OnBMClickListener;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cTextOutsideCircleButton;
import com.me.mseotsanyana.bmblibrary.cBoomMenuButton;
import com.me.mseotsanyana.bmblibrary.cUtil;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cEvaluationModel;
import com.me.mseotsanyana.mande.PL.presenters.evaluator.iEvaluationPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iActivityPresenter;
import com.me.mseotsanyana.mande.PL.ui.listeners.evaluator.iViewEvaluationListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewLogFrameListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewOutputListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandablePlaceHolderView;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cEvaluationAdapter extends cTreeAdapter  implements iViewEvaluationListener {
    private static String TAG = cEvaluationAdapter.class.getSimpleName();
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

    private final iEvaluationPresenter.View evaluationPresenterView;

    public cEvaluationAdapter(Context context, iEvaluationPresenter.View evaluationPresenterView,
                              List<cTreeModel> data, int expLevel){
        super(context, data, expLevel);
        this.evaluationPresenterView = evaluationPresenterView;
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case PARENT:
                view = inflater.inflate(R.layout.evaluation_parent_cardview, parent,
                        false);
                viewHolder = new cEvaluationParentViewHolder(view, this);
                break;
            case CHILD:
                view = inflater.inflate(R.layout.activity_child_placeholderview, parent,
                        false);
                viewHolder = new cEvaluationChildViewHolder(view);
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
                    cEvaluationModel evaluationModel = (cEvaluationModel) obj.getModelObject();
                    cEvaluationParentViewHolder EPH = ((cEvaluationParentViewHolder) viewHolder);

                    EPH.setPaddingLeft(20 * node.getLevel());

                    final int parentBackgroundColor = (position % 2 == 0) ? R.color.list_even :
                            R.color.list_odd;
                    EPH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            parentBackgroundColor));

                    EPH.textViewName.setText(evaluationModel.getName());
                    EPH.textViewDescription.setText(evaluationModel.getDescription());
                    EPH.textViewDescription.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d(TAG, "Clicked me to trigger the questionnaire");
                            EPH.evaluationListener.OnClickEvaluationItem(evaluationModel);
                        }
                    });

                    EPH.textViewStartDate.setText(sdf.format(evaluationModel.getStartDate()));
                    EPH.textViewEndDate.setText(sdf.format(evaluationModel.getEndDate()));

                    EPH.bmbMenu.clearBuilders();
                    for (int i = 0; i < EPH.bmbMenu.getPiecePlaceEnum().pieceNumber(); i++) {
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
                        EPH.bmbMenu.addBuilder(builder);
                    }

                    EPH.bmbMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EPH.bmbMenu.boom();
                        }
                    });

                    /* the collapse and expansion of the parent logframe */
                    if (node.isLeaf()) {
                        EPH.textViewExpandIcon.setVisibility(View.GONE);
                    } else {

                        EPH.textViewExpandIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            EPH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            EPH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            EPH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            EPH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            EPH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            EPH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }

                    EPH.textViewExpandIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    /* collapse and expansion of the details */
                    EPH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    EPH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));
                    EPH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    EPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                    EPH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!(EPH.expandableLayout.isExpanded())) {
                                EPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
                            } else {
                                EPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                            }

                            EPH.expandableLayout.toggle();
                        }
                    });

                    /* icon for saving updated record */
                    EPH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    EPH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    EPH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    EPH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    EPH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickUpdateLogFrame(position,
                            //        parentLogFrameModel);
                        }
                    });

                    /* icon for deleting a record */
                    EPH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    EPH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    EPH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    EPH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    EPH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickDeleteLogFrame(position,
                            //       parentLogFrameModel.getLogFrameID());
                        }
                    });

                    /* icon for syncing a record */
                    EPH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    EPH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    EPH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    EPH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
                    EPH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickSyncLogFrame(position,
                            //       parentLogFrameModel);
                        }
                    });

                    /* icon for creating a record */
                    EPH.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
                    EPH.textViewCreateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    EPH.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    EPH.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
                    EPH.textViewCreateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickCreateSubLogFrame(
                            //       parentLogFrameModel.getLogFrameID(), new cLogFrameModel());
                        }
                    });
                    break;

                case CHILD:
                    /*ArrayList<Object> objects = (ArrayList<Object>) obj.getModelObject();
                    cOutputAdapter.cOutputChildViewHolder COH = ((cOutputAdapter.cOutputChildViewHolder) viewHolder);
                    COH.setPaddingLeft(20 * node.getLevel());*/

                    break;
            }
        }
    }

    @Override
    public void OnClickEvaluationItem(cEvaluationModel evaluationModel) {
        evaluationPresenterView.onEvaluationSelected(evaluationModel);
    }

    public static class cEvaluationParentViewHolder extends cTreeViewHolder {
        private CardView cardView;
        private cExpandableLayout expandableLayout;

        private AppCompatTextView textViewExpandIcon;
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

        private iViewEvaluationListener evaluationListener;

        private cEvaluationParentViewHolder(final View treeViewHolder,
                                            iViewEvaluationListener evaluationListener) {
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.evaluationListener = evaluationListener;

            this.cardView = treeViewHolder.findViewById(R.id.cardView);
            this.expandableLayout = treeViewHolder.findViewById(R.id.expandableLayout);
            this.textViewExpandIcon = treeViewHolder.findViewById(R.id.textViewExpandIcon);
            this.bmbMenu = treeViewHolder.findViewById(R.id.bmbMenu);
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

    public static class cEvaluationChildViewHolder extends cTreeViewHolder {
        cExpandablePlaceHolderView activityPlaceholderView;

        private View treeView;

        private cEvaluationChildViewHolder(View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.activityPlaceholderView = treeViewHolder.findViewById(R.id.activityPlaceholderView);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
