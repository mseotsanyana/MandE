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

import com.me.mseotsanyana.mande.BLL.model.wpb.cIncomeModel;
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

public class cIncomeAdapter extends RecyclerView.Adapter<cIncomeAdapter.cIncomeParentViewHolder>
        implements Filterable {
    private static String TAG = cIncomeAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    //private final iActivityPresenter.View activityPresenterView;
    //private final iInputPresenter.View inputPresenterView;

    private Context context;
    private ArrayList<cIncomeModel> incomeModels;
    private List<cIncomeModel> filteredIncomeModels;

    public cIncomeAdapter(Context context, iInputPresenter.View inputPresenterView,
                            ArrayList<cIncomeModel> incomeModels) {
        this.context = context;
        this.incomeModels = incomeModels;
        this.filteredIncomeModels = incomeModels;

        //this.inputPresenterView = inputPresenterView;
        //this.inputPresenterView = inputPresenterView;
    }

    @NonNull
    @Override
    public cIncomeParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.resources_parent_cardview, parent, false);
        return new cIncomeParentViewHolder(view);
    }

    public void onBindViewHolder(@NonNull cIncomeParentViewHolder IH, int position) {
        cIncomeModel incomeModel = this.incomeModels.get(position);

        IH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                R.color.child_body_colour));

        IH.textViewParentCaption.setText(R.string.activity_caption);
        IH.textViewParent.setText(incomeModel.getActivityModel().getName());
        IH.textViewNameCaption.setText(R.string.input_caption);
        //--IH.textViewName.setText(incomeModel.getResourceModel().getName());
        IH.textViewNumberCaption.setText(R.string.input_income_caption);
        //IH.textViewNumber.setText(String.valueOf(incomeModel.);
        //--IH.textViewDescription.setText(incomeModel.getResourceModel().getDescription());
        IH.textViewStartDate.setText(sdf.format(incomeModel.getStartDate()));
        IH.textViewEndDate.setText(sdf.format(incomeModel.getEndDate()));

        /* collapse and expansion of the details */
        IH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        IH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                cFontManager.FONTAWESOME));
        IH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        IH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_details));
        IH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /* icon for syncing a record */
        IH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
        IH.textViewSyncIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        IH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        IH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
        IH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
            }
        });

        /* icon for deleting a record */
        IH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        IH.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        IH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        IH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
        IH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
            }
        });

        /* icon for saving updated record */
        IH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        IH.textViewUpdateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        IH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        IH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
        IH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
            }
        });
    }

    @Override
    public int getItemCount() {
        return incomeModels.size();
    }

    public void setIncomeModels(ArrayList<cIncomeModel> incomeModels){
        this.incomeModels = incomeModels;
        this.filteredIncomeModels = incomeModels;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredIncomeModels = incomeModels;
                } else {

                    ArrayList<cIncomeModel> filteredList = new ArrayList<>();
                    for (cIncomeModel incomeModel : incomeModels) {
//--                        if (incomeModel.getResourceModel().getName().toLowerCase().
//                                contains(charString.toLowerCase())) {
//                            filteredList.add(incomeModel);
//                        }
                    }

                    filteredIncomeModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredIncomeModels.size();
                filterResults.values = filteredIncomeModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //assert (ArrayList<cTreeModel>) filterResults.values != null;
                filteredIncomeModels = (ArrayList<cIncomeModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class cIncomeParentViewHolder extends RecyclerView.ViewHolder {
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

        private cIncomeParentViewHolder(final View viewHolder) {
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
//    cIncomeChildViewHolder HCH = ((cIncomeChildViewHolder) viewHolder);
//                    HCH.setPaddingLeft(20 * node.getLevel());
//
//                            HCH.resourcesPlaceholderView.removeAllViews();
//
//                            for (int i = 0; i < objects.size(); i++) {
//
//        /* list of journal entries under this input */
//        if (objects.get(i) instanceof cJournalModel) {
//        if (i == 0) {
//        HCH.resourcesPlaceholderView.addView(new cJournalHeaderView(
//        context, "Journal Entries"));
//
//        HCH.resourcesPlaceholderView.addView(new cJournalBodyView(
//        context, (cJournalModel) objects.get(i)));
//
//        Log.d(TAG, "1. JOURNAL ENTRIES: " + objects.get(i));
//        } else {
//        HCH.resourcesPlaceholderView.addView(new cJournalBodyView(
//        context, (cJournalModel) objects.get(i)));
//
//        Log.d(TAG, "2. JOURNAL ENTRIES: " + objects.get(i));
//        }
//        }
//
//        /* list of users under human group */
//        if (objects.get(i) instanceof cIncomeModel) {
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