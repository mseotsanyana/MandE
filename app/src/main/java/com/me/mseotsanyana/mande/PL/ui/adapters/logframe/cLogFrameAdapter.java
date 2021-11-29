package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.mseotsanyana.bmblibrary.BoomButtons.cTextOutsideCircleButton;
import com.me.mseotsanyana.bmblibrary.cBoomMenuButton;
import com.me.mseotsanyana.bmblibrary.cUtil;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iLogFramePresenter;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewLogFrameListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.quickactionlibrary.cCustomActionItemText;
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

public class cLogFrameAdapter extends cTreeAdapter implements iViewLogFrameListener,
        Filterable {
    private static String TAG = cLogFrameAdapter.class.getSimpleName();
    private static final SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PARENT_LOGFRAME = 0;
    private static final int CHILD_LOGFRAME = 1;

    private final iLogFramePresenter.View logframePresenterView;

    public FragmentManager fragmentManager;

    private List<cTreeModel> filteredTreeModels;

    private final String[] bmb_caption = {
            "Impacts (or Goals)",
            "Outcomes",
            "Outputs",
            "Activities",
            "Inputs"
//            "Key Performance Questions (KPQs)",
//            "Key Performance Indicators (KPIs)",
//            "Risks, Assumptions, Issues, Dependencies (RAID)",
//            "Work Plan and Budget (WP&B)",
//            "Book Keeping",
//            "Monitoring",
//            "Evaluation"
    };

    private final int[] bmb_imageid = {
            R.drawable.dashboard_impact,
            R.drawable.dashboard_outcome,
            R.drawable.dashboard_output,
            R.drawable.dashboard_activity,
            R.drawable.dashboard_input
//            R.drawable.dashboard_question,
//            R.drawable.dashboard_indicator,
//            R.drawable.dashboard_risk,
//            R.drawable.dashboard_workplan,
//            R.drawable.dashboard_budget,
//            R.drawable.dashboard_monitoring,
//            R.drawable.dashboard_evaluating
    };

    public cLogFrameAdapter(Context context, iLogFramePresenter.View logframePresenterView,
                            List<cTreeModel> treeModels) {
        super(context, treeModels);

        this.logframePresenterView = logframePresenterView;
        this.filteredTreeModels = treeModels;
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case PARENT_LOGFRAME:
                view = inflater.inflate(R.layout.logframe_parent_cardview, parent, false);
                viewHolder = new cParentLogFrameViewHolder(view, this);
                break;
            case CHILD_LOGFRAME:
                view = inflater.inflate(R.layout.logframe_child_cardview, parent, false);
                viewHolder = new cChildLogFrameViewHolder(view, this);
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
                case PARENT_LOGFRAME:
                    cLogFrameModel parentLogFrameModel = (cLogFrameModel) obj.getModelObject();
                    cParentLogFrameViewHolder PVH = ((cParentLogFrameViewHolder) viewHolder);

                    PVH.setPaddingLeft(20 * node.getLevel());

                    //final int parentBackgroundColor = (position % 2 == 0) ? R.color.list_even :
                    //        R.color.list_odd;
                    PVH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            R.color.parent_body_colour));

                    //PVH.textViewOrganization.setText(parentLogFrameModel.getOrganizationModel().
                    //        getName());
                    PVH.textViewName.setText(parentLogFrameModel.getName());
                    PVH.textViewDescription.setText(parentLogFrameModel.getDescription());
                    PVH.textViewStartDate.setText(sdf.format(parentLogFrameModel.getStartDate()));
                    PVH.textViewEndDate.setText(sdf.format(parentLogFrameModel.getEndDate()));

                    /* the collapse and expansion of the parent logframe */
                    if (node.isLeaf()) {
                        PVH.textViewExpandIcon.setVisibility(View.GONE);
                    } else {

                        PVH.textViewExpandIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            PVH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            PVH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PVH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            PVH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            PVH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PVH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }
                    PVH.textViewExpandIcon.setOnClickListener(v -> expandOrCollapse(position));

                    /* toggling with a header */
                    PVH.linearLayoutHeader.setOnClickListener(view -> expandOrCollapse(position));


                    /* icon for accessing the boom menu */
                    PVH.bmbMenu.clearBuilders();

                    for (int i = 0; i < PVH.bmbMenu.getPiecePlaceEnum().pieceNumber(); i++) {

                        cTextOutsideCircleButton.Builder builder = new cTextOutsideCircleButton
                                .Builder()
                                .isRound(false)
                                .shadowCornerRadius(cUtil.dp2px(20))
                                .buttonCornerRadius(cUtil.dp2px(20))
                                .normalColor(Color.LTGRAY)
                                .pieceColor(context.getColor(R.color.colorPrimaryDark))
                                .normalImageRes(bmb_imageid[i])
                                .normalText(bmb_caption[i])
                                .listener(index -> {
                                    // when the boom-button is clicked.
                                    PVH.logFrameListener.onClickBMBLogFrame(
                                            index, parentLogFrameModel);
                                });
                        PVH.bmbMenu.addBuilder(builder);
                    }
                    PVH.bmbMenu.setOnClickListener(v -> PVH.bmbMenu.boom());

                    /* icon for syncing a record */
                    PVH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
//                    PVH.textViewSyncIcon.setOnClickListener(view ->
//                            PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrameModel));

                    /* icon for deleting a record */
                    PVH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    PVH.textViewDeleteIcon.setOnClickListener(view ->
                            PVH.logFrameListener.onClickDeleteLogFrame(position,null
                                    /*parentLogFrameModel.getLogFrameID()*/));

                    /* icon for saving updated record */
                    PVH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    PVH.textViewUpdateIcon.setOnClickListener(view ->
                            PVH.logFrameListener.onClickUpdateLogFrame(position,
                                    parentLogFrameModel));

                    /* icon for creating a record */
                    PVH.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewCreateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
                    PVH.textViewCreateIcon.setOnClickListener(view ->
                            PVH.logFrameListener.onClickCreateSubLogFrame(null
                            /*parentLogFrameModel.getLogFrameID()*/, new cLogFrameModel()));

                    break;

                case CHILD_LOGFRAME:
                    cLogFrameModel childLogFrameModel = (cLogFrameModel) obj.getModelObject();
                    cChildLogFrameViewHolder CVH = ((cChildLogFrameViewHolder) viewHolder);

                    CVH.setPaddingLeft(20 * node.getLevel());
                    //final int childBackgroundColor = (position % 2 == 0) ? R.color.list_even :
                    //        R.color.list_odd;
                    CVH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            R.color.child_body_colour));

                    //CVH.textViewOrganization.setText(childLogFrameModel.getOrganizationModel().
                    //        getName());
                    CVH.textViewName.setText(childLogFrameModel.getName());
                    CVH.textViewDescription.setText(childLogFrameModel.getDescription());
                    CVH.textViewStartDate.setText(sdf.format(childLogFrameModel.getStartDate()));
                    CVH.textViewEndDate.setText(sdf.format(childLogFrameModel.getEndDate()));

                    /* icon for accessing the boom menu */
                    CVH.bmbMenu.clearBuilders();
                    for (int i = 0; i < CVH.bmbMenu.getPiecePlaceEnum().pieceNumber(); i++) {
                        cTextOutsideCircleButton.Builder builder = new cTextOutsideCircleButton
                                .Builder()
                                .isRound(false)
                                .shadowCornerRadius(cUtil.dp2px(20))
                                .buttonCornerRadius(cUtil.dp2px(20))
                                .normalColor(Color.LTGRAY)
                                .pieceColor(context.getColor(R.color.colorAccent))
                                .normalImageRes(bmb_imageid[i])
                                .normalText(bmb_caption[i])
                                .listener(index -> {
                                    /* when the boom-button is clicked. */
                                    CVH.logFrameListener.onClickBMBLogFrame(
                                            index, null/*childLogFrameModel.getLogFrameID()*/);
                                });
                        CVH.bmbMenu.addBuilder(builder);
                    }
                    CVH.bmbMenu.setOnClickListener(v -> CVH.bmbMenu.boom());

                    /* icon for syncing a record */
                    CVH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorAccent));
                    CVH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
//                    CVH.textViewSyncIcon.setOnClickListener(view ->
//                            CVH.logFrameListener.onClickSyncLogFrame(position, childLogFrameModel));
                    /* icon for deleting a record */
                    CVH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorAccent));
                    CVH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    CVH.textViewDeleteIcon.setOnClickListener(view ->
                            CVH.logFrameListener.onClickDeleteLogFrame(position,null
                            /*childLogFrameModel.getLogFrameID()*/));

                    /* icon for saving updated record */
                    CVH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorAccent));
                    CVH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    CVH.textViewUpdateIcon.setOnClickListener(view ->
                            CVH.logFrameListener.onClickUpdateLogFrame(position,
                                    childLogFrameModel));

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + obj.getType());
            }
        }
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
//                    for (cTreeModel treeModel : getTreeModel()) {
//                        if (((cLogFrameModel) treeModel.getModelObject()).getName().toLowerCase().
//                                contains(charString.toLowerCase()) ||
//                                ((cLogFrameModel) treeModel.getModelObject()).getOrganizationModel().
//                                        getName().toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(treeModel);
//                        }
//                    }

                    filteredTreeModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredTreeModels.size();
                filterResults.values = filteredTreeModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredTreeModels = (ArrayList<cTreeModel>) filterResults.values;

                try {
                    notifyTreeModelChanged(filteredTreeModels);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /* these functions communicate data from the adapter to a fragment */

    @Override
    public void onClickBMBLogFrame(int index, cLogFrameModel logFrameModel) {
        logframePresenterView.onClickBMBLogFrame(index, logFrameModel);
    }

    @Override
    public void onClickCreateSubLogFrame(String logFrameID, cLogFrameModel logFrameModel) {
        logframePresenterView.onClickCreateSubLogFrame(logFrameID, logFrameModel);
    }

    @Override
    public void onClickUpdateLogFrame(int position, cLogFrameModel logFrameModel) {
        logframePresenterView.onClickUpdateLogFrame(position, logFrameModel);
    }

    @Override
    public void onClickDeleteLogFrame(int position, String logframeID) {
        logframePresenterView.onClickDeleteLogFrame(position, logframeID);
    }

    public static class cParentLogFrameViewHolder extends cTreeViewHolder {
        private final CardView cardView;
        private final AppCompatTextView textViewExpandIcon;
        private final LinearLayout linearLayoutHeader;

        private final AppCompatTextView textViewOrganization;
        private final AppCompatTextView textViewName;
        private final AppCompatTextView textViewDescription;
        private final AppCompatTextView textViewStartDate;
        private final AppCompatTextView textViewEndDate;

        private final cBoomMenuButton bmbMenu;
        private final AppCompatTextView textViewSyncIcon;
        private final AppCompatTextView textViewDeleteIcon;
        private final AppCompatTextView textViewUpdateIcon;
        private final AppCompatTextView textViewCreateIcon;

        private final View treeView;
        private final iViewLogFrameListener logFrameListener;

        private cParentLogFrameViewHolder(final View treeViewHolder, iViewLogFrameListener listener) {
            super(treeViewHolder);

            this.treeView = treeViewHolder;
            this.logFrameListener = listener;

            this.cardView = treeViewHolder.findViewById(R.id.cardView);
            this.textViewExpandIcon = treeViewHolder.findViewById(R.id.textViewExpandIcon);
            this.linearLayoutHeader = treeViewHolder.findViewById(R.id.linearLayoutHeader);

            this.bmbMenu = treeViewHolder.findViewById(R.id.bmbMenu);
            this.textViewOrganization = treeViewHolder.findViewById(R.id.textViewOrganization);
            this.textViewName = treeViewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = treeViewHolder.findViewById(R.id.textViewDescription);
            this.textViewStartDate = treeViewHolder.findViewById(R.id.textViewStartDate);
            this.textViewEndDate = treeViewHolder.findViewById(R.id.textViewEndDate);
            this.textViewSyncIcon = treeViewHolder.findViewById(R.id.textViewSyncIcon);
            this.textViewDeleteIcon = treeViewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewUpdateIcon = treeViewHolder.findViewById(R.id.textViewUpdateIcon);
            this.textViewCreateIcon = treeViewHolder.findViewById(R.id.textViewCreateIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cChildLogFrameViewHolder extends cTreeViewHolder {
        private final CardView cardView;
        private final AppCompatTextView textViewOrganization;
        private final AppCompatTextView textViewName;
        private final AppCompatTextView textViewDescription;
        private final AppCompatTextView textViewStartDate;
        private final AppCompatTextView textViewEndDate;

        private final cBoomMenuButton bmbMenu;
        private final AppCompatTextView textViewSyncIcon;
        private final AppCompatTextView textViewDeleteIcon;
        private final AppCompatTextView textViewUpdateIcon;
        //private AppCompatTextView textViewCreateIcon;

        private final View treeView;
        private final iViewLogFrameListener logFrameListener;

        private cChildLogFrameViewHolder(View treeViewHolder, iViewLogFrameListener listener) {
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.logFrameListener = listener;

            this.cardView = treeViewHolder.findViewById(R.id.cardView);
            this.bmbMenu = treeViewHolder.findViewById(R.id.bmbMenu);
            this.textViewOrganization = treeViewHolder.findViewById(R.id.textViewOrganization);
            this.textViewName = treeViewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = treeViewHolder.findViewById(R.id.textViewDescription);
            this.textViewStartDate = treeViewHolder.findViewById(R.id.textViewStartDate);
            this.textViewEndDate = treeViewHolder.findViewById(R.id.textViewEndDate);
            this.textViewSyncIcon = treeViewHolder.findViewById(R.id.textViewSyncIcon);
            this.textViewDeleteIcon = treeViewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewUpdateIcon = treeViewHolder.findViewById(R.id.textViewUpdateIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }


    static public class cQAAdapter extends BaseAdapter {

        final int[] ICONS = new int[]{
                R.string.fa_plus,
                R.string.fa_upload
        };

        LayoutInflater mLayoutInflater;
        List<cCustomActionItemText> mItems;
        cCustomActionItemText item;

        Context context;

        public cQAAdapter(Context context) {
            this.context = context;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            mItems = new ArrayList<>();

            item = new cCustomActionItemText(context, "Add", ICONS[0]);
            mItems.add(item);

            item = new cCustomActionItemText(context, "Upload", ICONS[1]);
            mItems.add(item);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int arg) {
            return mItems.get(arg);
        }

        @Override
        public long getItemId(int arg) {
            return arg;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View arg1, ViewGroup viewGroup) {
            View view;
            view = mLayoutInflater.inflate(R.layout.action_item_flexible, viewGroup,
                    false);

            cCustomActionItemText item = (cCustomActionItemText) getItem(position);

            TextView image = (TextView) view.findViewById(R.id.image);

            image.setTypeface(null, Typeface.NORMAL);
            image.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
            image.setText(context.getResources().getString(item.getImage()));
            image.setTextColor(Color.GRAY);

            return view;
        }
    }
}