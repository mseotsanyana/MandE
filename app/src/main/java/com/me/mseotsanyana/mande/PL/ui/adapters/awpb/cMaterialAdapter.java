package com.me.mseotsanyana.mande.PL.ui.adapters.awpb;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.wpb.cHumanModel;
import com.me.mseotsanyana.mande.BLL.model.wpb.cMaterialModel;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iInputPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cMaterialAdapter extends RecyclerView.Adapter<cMaterialAdapter.cMaterialParentViewHolder>
        implements Filterable {
    private static String TAG = cMaterialAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    //private final iActivityPresenter.View activityPresenterView;
    //private final iInputPresenter.View inputPresenterView;

    private Context context;
    private ArrayList<cMaterialModel> materialModels;
    private List<cMaterialModel> filteredMaterialModels;

    public cMaterialAdapter(Context context, iInputPresenter.View inputPresenterView,
                         ArrayList<cMaterialModel> materialModels) {
        this.context = context;
        this.materialModels = materialModels;
        this.filteredMaterialModels = materialModels;

        //this.inputPresenterView = inputPresenterView;
        //this.inputPresenterView = inputPresenterView;
    }

    @NonNull
    @Override
    public cMaterialParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.resources_parent_cardview, parent, false);
        return new cMaterialParentViewHolder(view);
    }

    public void onBindViewHolder(@NonNull cMaterialParentViewHolder MH, int position) {
        cMaterialModel materialModel = this.materialModels.get(position);

        MH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                R.color.child_body_colour));

        MH.textViewParentCaption.setText(R.string.activity_caption);
        MH.textViewParent.setText(materialModel.getActivityModel().getName());
        MH.textViewNameCaption.setText(R.string.input_caption);
        MH.textViewName.setText(materialModel.getResourceModel().getName());
        MH.textViewNumberCaption.setText(R.string.input_number_caption);
        MH.textViewNumber.setText(String.valueOf(materialModel.getQuantity()));
        MH.textViewDescription.setText(materialModel.getResourceModel().getDescription());
        MH.textViewStartDate.setText(sdf.format(materialModel.getStartDate()));
        MH.textViewEndDate.setText(sdf.format(materialModel.getEndDate()));

        /* collapse and expansion of the details */
        MH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        MH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                cFontManager.FONTAWESOME));
        MH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        MH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_details));
        MH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /* icon for syncing a record */
        MH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
        MH.textViewSyncIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        MH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        MH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
        MH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
            }
        });

        /* icon for deleting a record */
        MH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        MH.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        MH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        MH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
        MH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
            }
        });

        /* icon for saving updated record */
        MH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        MH.textViewUpdateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        MH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        MH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
        MH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
            }
        });
    }

    @Override
    public int getItemCount() {
        return materialModels.size();
    }

    public void setMaterialModels(ArrayList<cMaterialModel> materialModels){
        this.materialModels = materialModels;
        this.filteredMaterialModels = materialModels;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredMaterialModels = materialModels;
                } else {

                    ArrayList<cMaterialModel> filteredList = new ArrayList<>();
                    for (cMaterialModel materialModel : materialModels) {
                        if (materialModel.getResourceModel().getName().toLowerCase().
                                contains(charString.toLowerCase())) {
                            filteredList.add(materialModel);
                        }
                    }

                    filteredMaterialModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredMaterialModels.size();
                filterResults.values = filteredMaterialModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //assert (ArrayList<cTreeModel>) filterResults.values != null;
                filteredMaterialModels = (ArrayList<cMaterialModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class cMaterialParentViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        private AppCompatTextView textViewParentCaption;
        private AppCompatTextView textViewParent;
        private AppCompatTextView textViewName;
        private AppCompatTextView textViewNameCaption;
        private AppCompatTextView textViewNumberCaption;
        private AppCompatTextView textViewNumber;
        private AppCompatTextView textViewDescription;
        private AppCompatTextView textViewStartDate;
        private AppCompatTextView textViewEndDate;

        private AppCompatTextView textViewSyncIcon;
        private AppCompatTextView textViewDeleteIcon;
        private AppCompatTextView textViewUpdateIcon;
        private AppCompatTextView textViewDetailIcon;

        private View viewHolder;

        private cMaterialParentViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.cardView = viewHolder.findViewById(R.id.cardView);

            this.textViewParentCaption = viewHolder.findViewById(R.id.textViewParentCaption);
            this.textViewParent = viewHolder.findViewById(R.id.textViewParent);
            this.textViewNameCaption = viewHolder.findViewById(R.id.textViewNameCaption);
            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewNumberCaption = viewHolder.findViewById(R.id.textViewNumberCaption);
            this.textViewNumber = viewHolder.findViewById(R.id.textViewNumber);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewStartDate = viewHolder.findViewById(R.id.textViewStartDate);
            this.textViewEndDate = viewHolder.findViewById(R.id.textViewEndDate);

            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
            this.textViewUpdateIcon = viewHolder.findViewById(R.id.textViewUpdateIcon);
            this.textViewDeleteIcon = viewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewSyncIcon = viewHolder.findViewById(R.id.textViewSyncIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}

//    ArrayList<Object> objects = (ArrayList<Object>) obj.getModelObject();
//    cMaterialAdapter.cHumanChildViewHolder HCH = ((cHumanChildViewHolder) viewHolder);
//                    HCH.setPaddingLeft(20 * node.getLevel());
//
//                            HCH.resourcesPlaceholderView.removeAllViews();
//
//                            for (int i = 0; i < objects.size(); i++) {
//        /* list of users under material group */
//        if (objects.get(i) instanceof cUserModel) {
//        if (i == 0) {
//        HCH.resourcesPlaceholderView.addView(new cLogFrameHeaderView(
//        context, "List of Users"));
//        HCH.resourcesPlaceholderView.addView(new cUserBodyView(
//        context, null, (cUserModel) objects.get(i)));
//        Log.d(TAG, "1. CHILD USER: " + objects.get(i));
//        } else {
//        HCH.resourcesPlaceholderView.addView(new cUserBodyView(
//        context, null, (cUserModel) objects.get(i)));
//        Log.d(TAG, "2. CHILD USER: " + objects.get(i));
//        }
//        }
//
//        /* list of sub-logframe activities under this input */
//        if (objects.get(i) instanceof cActivityModel) {
//        if (i == 0) {
//        HCH.resourcesPlaceholderView.addView(new cLogFrameHeaderView(
//        context, "List of Activities"));
//        HCH.resourcesPlaceholderView.addView(new cActivityBodyView(
//        context, (cActivityModel) objects.get(i)));
//        Log.d(TAG, "1. CHILD ACTIVITY: " + objects.get(i));
//        } else {
//        HCH.resourcesPlaceholderView.addView(new cActivityBodyView(
//        context, (cActivityModel) objects.get(i)));
//        Log.d(TAG, "2. CHILD ACTIVITY: " + objects.get(i));
//        }
//        }
//
//        /* list of questions under this input */
//        if (objects.get(i) instanceof cQuestionModel) {
//        if (i == 0) {
//        HCH.resourcesPlaceholderView.addView(new cLogFrameHeaderView(
//        context, "List of Questions"));
//        HCH.resourcesPlaceholderView.addView(new cQuestionBodyView(
//        context, (cQuestionModel) objects.get(i)));
//
//        Log.d(TAG, "1. QUESTION: " + objects.get(i));
//        } else {
//        HCH.resourcesPlaceholderView.addView(new cQuestionBodyView(context,
//        (cQuestionModel) objects.get(i)));
//        Log.d(TAG, "2. QUESTION: " + objects.get(i));
//        }
//        }
//
//        /* list of journal entries under this input */
//        if (objects.get(i) instanceof cQuestionModel) {
//        if (i == 0) {
//        HCH.resourcesPlaceholderView.addView(new cLogFrameHeaderView(
//        context, "List of Journal Entries"));
//        HCH.resourcesPlaceholderView.addView(new cQuestionBodyView(
//        context, (cQuestionModel) objects.get(i)));
//
//        Log.d(TAG, "1. QUESTION: " + objects.get(i));
//        } else {
//        HCH.resourcesPlaceholderView.addView(new cQuestionBodyView(context,
//        (cQuestionModel) objects.get(i)));
//        Log.d(TAG, "2. QUESTION: " + objects.get(i));
//        }
//        }
//        }