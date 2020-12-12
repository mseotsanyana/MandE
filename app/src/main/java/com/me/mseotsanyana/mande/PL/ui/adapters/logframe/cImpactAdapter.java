package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iImpactPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iOutcomePresenter;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewImpactListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewOutcomeListener;
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

public class cImpactAdapter extends cTreeAdapter implements iViewImpactListener,
        iViewOutcomeListener, Filterable {
    private static String TAG = cImpactAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PARENT = 0;
    private static final int CHILD = 1;

    private final iImpactPresenter.View impactPresenterView;
    private final iOutcomePresenter.View outcomePresenterView;

    private List<cTreeModel> filteredTreeModels;

    public cImpactAdapter(Context context, iImpactPresenter.View impactPresenterView,
                          iOutcomePresenter.View outcomePresenterView,
                          List<cTreeModel> impactTree, int expLevel) {
        super(context, impactTree, expLevel);

        this.impactPresenterView = impactPresenterView;
        this.outcomePresenterView = outcomePresenterView;

        this.filteredTreeModels = impactTree;
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case PARENT:
                view = inflater.inflate(R.layout.component_parent_cardview, parent,
                        false);
                viewHolder = new cImpactParentViewHolder(view, this);
                break;
            case CHILD:
                view = inflater.inflate(R.layout.component_child_cardview, parent,
                        false);
                viewHolder = new cImpactChildViewHolder(view, this);
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
                    cImpactModel parentImpact = (cImpactModel) obj.getModelObject();
                    cImpactParentViewHolder IPH = ((cImpactParentViewHolder) viewHolder);

                    //final int parentBackgroundColor = (position % 2 == 0) ? R.color.list_even :
                    //        R.color.list_odd;
                    IPH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            R.color.parent_body_colour));

                    /* impact component does have parent link */
                    IPH.linearLayoutHeader.setVisibility(View.GONE);

                    IPH.textViewNameCaption.setText(
                            context.getResources().getString(R.string.impact_caption));
                    IPH.textViewDescriptionCaption.setText(
                            context.getResources().getString(R.string.description_caption));
                    IPH.textViewStartDateCaption.setText(
                            context.getResources().getString(R.string.startdate_caption));
                    IPH.textViewEndDateCaption.setText(
                            context.getResources().getString(R.string.enddate_caption));

                    IPH.textViewName.setText(parentImpact.getName());
                    IPH.textViewDescription.setText(parentImpact.getDescription());
                    IPH.textViewStartDate.setText(sdf.format(parentImpact.getStartDate()));
                    IPH.textViewEndDate.setText(sdf.format(parentImpact.getEndDate()));

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
                    IPH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_details));
                    IPH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            cOutcomeModel[] outcomeModels =
                                    new cOutcomeModel[parentImpact.getOutcomeModelSet().size()];
                            parentImpact.getOutcomeModelSet().toArray(outcomeModels);
                            cQuestionModel[] questionModels =
                                    new cQuestionModel[parentImpact.getQuestionModelSet().size()];
                            parentImpact.getQuestionModelSet().toArray(questionModels);

                            IPH.impactListener.onClickDetailImpact(outcomeModels, questionModels);

                            /* set of outcomes under the impact
                            ArrayList<cOutcomeModel> outcomes = new ArrayList<>(parentImpact.getOutcomeModelSet());*/

                            /* set of questions under the impact
                            ArrayList<cQuestionModel> questions = new ArrayList<>(parentImpact.getQuestionModelSet());*/

                            /* set of raids under the impact
                            ArrayList<cRaidModel> raids = new ArrayList<>(parentImpact.getRaidModelSet());*/

                            //IPH.expandableLayout.toggle();
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

                    IPH.setPaddingLeft(20 * node.getLevel());

                    break;

                case CHILD:
                    cImpactModel childImpact = (cImpactModel) obj.getModelObject();
                    cImpactChildViewHolder ICH = ((cImpactChildViewHolder) viewHolder);

                    //final int childBackgroundColor = (position % 2 == 0) ? R.color.list_even :
                    //        R.color.list_odd;
                    ICH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            R.color.child_body_colour));

                    /* impact component does have parent link */
                    ICH.linearLayoutHeader.setVisibility(View.GONE);

                    ICH.textViewNameCaption.setText(
                            context.getResources().getString(R.string.impact_caption));
                    ICH.textViewDescriptionCaption.setText(
                            context.getResources().getString(R.string.description_caption));
                    ICH.textViewStartDateCaption.setText(
                            context.getResources().getString(R.string.startdate_caption));
                    ICH.textViewEndDateCaption.setText(
                            context.getResources().getString(R.string.enddate_caption));

                    ICH.textViewName.setText(childImpact.getName());
                    ICH.textViewDescription.setText(childImpact.getDescription());
                    ICH.textViewStartDate.setText(sdf.format(childImpact.getStartDate()));
                    ICH.textViewEndDate.setText(sdf.format(childImpact.getEndDate()));

                    /* the collapse and expansion of the parent logframe */
                    if (node.isLeaf()) {
                        ICH.textViewExpandIcon.setVisibility(View.GONE);
                    } else {

                        ICH.textViewExpandIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            ICH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            ICH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            ICH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            ICH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            ICH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            ICH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }
                    ICH.textViewExpandIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    /* collapse and expansion of the details */
                    ICH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    ICH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));
                    ICH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorAccent));
                    ICH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_details));
                    ICH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cOutcomeModel[] outcomeModels =
                                    new cOutcomeModel[childImpact.getOutcomeModelSet().size()];
                            childImpact.getOutcomeModelSet().toArray(outcomeModels);
                            cQuestionModel[] questionModels =
                                    new cQuestionModel[childImpact.getQuestionModelSet().size()];
                            childImpact.getQuestionModelSet().toArray(questionModels);

                            ICH.impactListener.onClickDetailImpact(outcomeModels, questionModels);

                            /* set of outcomes under the impact
                            ArrayList<cOutcomeModel> outcomes = new ArrayList<>(childImpact.getOutcomeModelSet());*/

                            /* set of questions under the impact
                            ArrayList<cQuestionModel> questions = new ArrayList<>(childImpact.getQuestionModelSet());*/

                            /* set of raids under the impact
                            ArrayList<cRaidModel> raids = new ArrayList<>(childImpact.getRaidModelSet());*/

                        }
                    });

                    /* icon for syncing a record */
                    ICH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    ICH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    ICH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorAccent));
                    ICH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
                    ICH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickSyncLogFrame(position,
                            //       parentLogFrameModel);
                        }
                    });

                    /* icon for deleting a record */
                    ICH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    ICH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    ICH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorAccent));
                    ICH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    ICH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickDeleteLogFrame(position,
                            //       parentLogFrameModel.getLogFrameID());
                        }
                    });

                    /* icon for saving updated record */
                    ICH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    ICH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    ICH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorAccent));
                    ICH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    ICH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //IPH.logFrameListener.onClickUpdateLogFrame(position,
                            //        parentLogFrameModel);
                        }
                    });

                    ICH.setPaddingLeft(20 * node.getLevel());

                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + obj.getType());
            }
        }
    }

    /* impact click events */

    @Override
    public void onClickBMBImpact(int index, long impactID) {
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

    @Override
    public void onClickDetailImpact(cOutcomeModel[] outcomeModels, cQuestionModel[] questionModels) {
        this.impactPresenterView.onClickDetailImpact(outcomeModels, questionModels);
    }

    /* outcome click events */

    @Override
    public void onClickUpdateOutcome(cOutcomeModel outcomeModel, int position) {
        this.outcomePresenterView.onClickUpdateOutcome(outcomeModel, position);
    }

    @Override
    public void onClickDeleteOutcome(long outcomeID, int position) {
        this.outcomePresenterView.onClickDeleteOutcome(outcomeID, position);
    }

    @Override
    public void onClickSyncOutcome(cOutcomeModel outcomeModel, int position) {
        this.outcomePresenterView.onClickSyncOutcome(outcomeModel);
    }

    @Override
    public void onClickBMBOutcome(int index) {
        this.outcomePresenterView.onClickBMBOutcome(index);
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
                        if (((cImpactModel)treeModel.getModelObject()).getName().toLowerCase().
                                contains(charString.toLowerCase())) {
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

    public static class cImpactParentViewHolder extends cTreeViewHolder {
        private CardView cardView;
        private AppCompatTextView textViewExpandIcon;
        private LinearLayout linearLayoutHeader;

        private AppCompatTextView textViewNameCaption;
        private AppCompatTextView textViewDescriptionCaption;
        private AppCompatTextView textViewStartDateCaption;
        private AppCompatTextView textViewEndDateCaption;

        private AppCompatTextView textViewName;
        private AppCompatTextView textViewDescription;
        private AppCompatTextView textViewStartDate;
        private AppCompatTextView textViewEndDate;

        private AppCompatTextView textViewDetailIcon;
        private AppCompatTextView textViewSyncIcon;
        private AppCompatTextView textViewDeleteIcon;
        private AppCompatTextView textViewUpdateIcon;
        private AppCompatTextView textViewCreateIcon;

        private View treeView;
        private iViewImpactListener impactListener;

        private cImpactParentViewHolder(final View treeViewHolder,
                                        iViewImpactListener impactListener) {
            super(treeViewHolder);
            this.treeView = treeViewHolder;
            this.impactListener = impactListener;

            this.cardView = treeViewHolder.findViewById(R.id.cardView);
            this.textViewExpandIcon = treeViewHolder.findViewById(R.id.textViewExpandIcon);
            this.linearLayoutHeader = treeViewHolder.findViewById(R.id.linearLayoutHeader);
            this.textViewNameCaption = treeViewHolder.findViewById(R.id.textViewNameCaption);
            this.textViewDescriptionCaption = treeViewHolder.findViewById(R.id.textViewDescriptionCaption);
            this.textViewStartDateCaption = treeViewHolder.findViewById(R.id.textViewStartDateCaption);
            this.textViewEndDateCaption = treeViewHolder.findViewById(R.id.textViewEndDateCaption);
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
        private CardView cardView;
        private AppCompatTextView textViewExpandIcon;
        private LinearLayout linearLayoutHeader;

        private AppCompatTextView textViewNameCaption;
        private AppCompatTextView textViewDescriptionCaption;
        private AppCompatTextView textViewStartDateCaption;
        private AppCompatTextView textViewEndDateCaption;

        private AppCompatTextView textViewName;
        private AppCompatTextView textViewDescription;
        private AppCompatTextView textViewStartDate;
        private AppCompatTextView textViewEndDate;

        private AppCompatTextView textViewDetailIcon;
        private AppCompatTextView textViewSyncIcon;
        private AppCompatTextView textViewDeleteIcon;
        private AppCompatTextView textViewUpdateIcon;

        private View treeView;
        private iViewImpactListener impactListener;

        private cImpactChildViewHolder(View treeViewHolder, iViewImpactListener impactListener) {
            super(treeViewHolder);
            this.treeView = treeViewHolder;
            this.impactListener = impactListener;

            this.cardView = treeViewHolder.findViewById(R.id.cardView);
            this.textViewExpandIcon = treeViewHolder.findViewById(R.id.textViewExpandIcon);
            this.linearLayoutHeader = treeViewHolder.findViewById(R.id.linearLayoutHeader);

            this.textViewNameCaption = treeViewHolder.findViewById(R.id.textViewNameCaption);
            this.textViewDescriptionCaption = treeViewHolder.findViewById(R.id.textViewDescriptionCaption);
            this.textViewStartDateCaption = treeViewHolder.findViewById(R.id.textViewStartDateCaption);
            this.textViewEndDateCaption = treeViewHolder.findViewById(R.id.textViewEndDateCaption);

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
