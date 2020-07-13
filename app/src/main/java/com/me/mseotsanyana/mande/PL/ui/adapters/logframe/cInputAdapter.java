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
import com.me.mseotsanyana.mande.DAL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iActivityPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iInputPresenter;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewActivityListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewInputListener;
import com.me.mseotsanyana.mande.PL.ui.views.cInputBodyView;
import com.me.mseotsanyana.mande.PL.ui.views.cLogFrameHeaderView;
import com.me.mseotsanyana.mande.PL.ui.views.cOutcomeBodyView;
import com.me.mseotsanyana.mande.PL.ui.views.cQuestionBodyView;
import com.me.mseotsanyana.mande.PL.ui.views.cRaidBodyView;
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

public class cInputAdapter extends cTreeAdapter implements iViewInputListener{
    private static String TAG = cInputAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PARENT   = 0;
    private static final int CHILD    = 1;

    private static final int HUMAN    = 1;
    private static final int MATERIAL = 2;
    private static final int INCOME   = 3;
    private static final int EXPENSE  = 4;

    private final String[] bmb_caption = {
            "Sub-LogFrame Activities",
            "Child Activities",
            "Outputs",
            "Questions"
    };

    private int[] bmb_imageid = {
            R.drawable.dashboard_impact,
            R.drawable.dashboard_outcome,
            R.drawable.dashboard_output,
            R.drawable.dashboard_question,
            R.drawable.dashboard_risk
    };

    //private final iActivityPresenter.View activityPresenterView;
    private final iInputPresenter.View inputPresenterView;

    boolean humanIndex = false;

    public cInputAdapter(Context context, iInputPresenter.View inputPresenterView,
                        List<cTreeModel> inputTree, int expLevel){
        super(context, inputTree, expLevel);

        this.inputPresenterView = inputPresenterView;
        //this.inputPresenterView = inputPresenterView;
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case PARENT:
                view = inflater.inflate(R.layout.input_child_placeholderview, parent,
                        false);
                viewHolder = new cInputAdapter.cInputParentViewHolder(context, view);
                break;
            case CHILD:
                view = inflater.inflate(R.layout.input_child_placeholderview, parent,
                        false);
                viewHolder = new cInputAdapter.cInputChildViewHolder(view);
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
                    cInputModel inputParent = (cInputModel) obj.getModelObject();
                    cInputAdapter.cInputParentViewHolder IPH =
                            ((cInputAdapter.cInputParentViewHolder) viewHolder);

                    //IPH.inputPlaceholderView.removeAllViews();

                    if (inputParent.getResourceModel().getResourceTypeID() == HUMAN) {
                        if(!humanIndex){
                            IPH.inputPlaceholderView.addView(new cLogFrameHeaderView(
                                    context, "Human Resources"));
                            IPH.inputPlaceholderView.addView(new cInputBodyView(context,this, inputParent));
                            humanIndex = true;
                        }else {
                            IPH.inputPlaceholderView.addView(new cInputBodyView(
                                    context,this, inputParent));
                        }
                        Log.d(TAG, "HUMAN RESOURCES: " +
                                inputParent.getResourceModel().getName());
                    }
/*
                    if (inputParent.getResourceModel().getResourceTypeID() == MATERIAL) {
                        IPH.inputPlaceholderView.addChildView(IPH.materialView, new cInputBodyView(
                                context,this, inputParent));
                        Log.d(TAG, "MATERIAL RESOURCES: " +
                                inputParent.getResourceModel().getName());
                    }

                    if (inputParent.getResourceModel().getResourceTypeID() == INCOME) {
                        IPH.inputPlaceholderView.addChildView(IPH.incomeView, new cInputBodyView(
                                context,this, inputParent));
                        Log.d(TAG, "INCOME RESOURCES: " +
                                inputParent.getResourceModel().getName());
                    }

                    if (inputParent.getResourceModel().getResourceTypeID() == EXPENSE) {
                        IPH.inputPlaceholderView.addChildView(IPH.expenseView, new cInputBodyView(
                                context, this, inputParent));
                        Log.d(TAG, "EXPECTED EXPENSES: " +
                                inputParent.getResourceModel().getName());
                    }*/

                    break;

                case CHILD:
                    ArrayList<Object> objects = (ArrayList<Object>) obj.getModelObject();
                    cInputAdapter.cInputChildViewHolder ICH = ((cInputChildViewHolder) viewHolder);
                    ICH.setPaddingLeft(20 * node.getLevel());

                    ICH.inputPlaceholderView.removeAllViews();

                    for (int i = 0; i < objects.size(); i++) {

                        /* list of child logframe impacts
                        if (objects.get(i) instanceof cActivityModel) {
                            if (i == 0) {
                                COH.outputPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Impacts"));
                                COH.outputPlaceholderView.addView(new cActivityBodyView(
                                        context, this, (cActivityModel) objects.get(i)));
                                Log.d(TAG, "1. CHILD IMPACT: " + objects.get(i));
                            } else {
                                COH.outputPlaceholderView.addView(new cActivityBodyView(
                                        context, this, (cActivityModel) objects.get(i)));
                                Log.d(TAG, "2. CHILD IMPACT: " + objects.get(i));
                            }
                        }*/

                        /* list of child outcomes under this outcome
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
                        }*/

                        /* list of outputs under this outcome
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
                        }*/

                        /* list of questions under this input */
                        if (objects.get(i) instanceof cQuestionModel) {
                            if (i == 0) {
                                ICH.inputPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of Questions"));
                                ICH.inputPlaceholderView.addView(new cQuestionBodyView(
                                        context, (cQuestionModel) objects.get(i)));

                                Log.d(TAG, "1. QUESTION: " + objects.get(i));
                            } else {
                                ICH.inputPlaceholderView.addView(new cQuestionBodyView(context,
                                        (cQuestionModel) objects.get(i)));
                                Log.d(TAG, "2. QUESTION: " + objects.get(i));
                            }
                        }

                        /* list of RAIDs under this input */
                        if (objects.get(i) instanceof cRaidModel) {
                            if (i == 0) {
                                ICH.inputPlaceholderView.addView(new cLogFrameHeaderView(
                                        context, "List of RAID"));
                                ICH.inputPlaceholderView.addView(new cRaidBodyView(
                                        context, (cRaidModel) objects.get(i)));

                                Log.d(TAG, "1. RAID: " + objects.get(i));
                            } else {
                                ICH.inputPlaceholderView.addView(new cRaidBodyView(context,
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

    public static class cInputParentViewHolder extends cTreeViewHolder {
        private cExpandablePlaceHolderView inputPlaceholderView;
        private View treeView;

        private cLogFrameHeaderView humanView, materialView, incomeView, expenseView;

        private cInputParentViewHolder(Context context, final View treeViewHolder) {
            super(treeViewHolder);
            this.treeView = treeViewHolder;

            /* create expandable placeholder */
            this.inputPlaceholderView = treeViewHolder.findViewById(R.id.inputPlaceholderView);

            this.inputPlaceholderView.removeAllViews();

            /* create headers for input resources
            humanView    = new cLogFrameHeaderView(context, "Human Resources");
            materialView = new cLogFrameHeaderView(context, "Material Resources");
            incomeView   = new cLogFrameHeaderView(context, "Income Resources");
            expenseView  = new cLogFrameHeaderView(context, "Expected Expenses");*/

            /* add the heaters to the to the expandable placeholder
            this.inputPlaceholderView.addView(humanView);
            this.inputPlaceholderView.addView(materialView);
            this.inputPlaceholderView.addView(incomeView);
            this.inputPlaceholderView.addView(expenseView);*/
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cInputChildViewHolder extends cTreeViewHolder {
        cExpandablePlaceHolderView inputPlaceholderView;

        private View treeView;

        private cInputChildViewHolder(View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.inputPlaceholderView = treeViewHolder.findViewById(R.id.inputPlaceholderView);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
