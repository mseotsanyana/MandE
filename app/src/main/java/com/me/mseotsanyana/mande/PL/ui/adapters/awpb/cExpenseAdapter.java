package com.me.mseotsanyana.mande.PL.ui.adapters.awpb;

import android.content.Context;
import android.graphics.Typeface;
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

import com.me.mseotsanyana.mande.BLL.model.wpb.cExpenseModel;
import com.me.mseotsanyana.mande.BLL.model.wpb.cIncomeModel;
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

public class cExpenseAdapter extends RecyclerView.Adapter<cExpenseAdapter.cExpenseParentViewHolder>
        implements Filterable {
    private static String TAG = cExpenseAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    //private final iActivityPresenter.View activityPresenterView;
    //private final iInputPresenter.View inputPresenterView;

    private Context context;
    private ArrayList<cExpenseModel> expenseModels;
    private List<cExpenseModel> filteredExpenseModels;

    public cExpenseAdapter(Context context, iInputPresenter.View inputPresenterView,
                          ArrayList<cExpenseModel> expenseModels) {
        this.context = context;
        this.expenseModels = expenseModels;
        this.filteredExpenseModels = expenseModels;

        //this.inputPresenterView = inputPresenterView;
        //this.inputPresenterView = inputPresenterView;
    }

    @NonNull
    @Override
    public cExpenseParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.resources_parent_cardview, parent, false);
        return new cExpenseParentViewHolder(view);
    }

    public void onBindViewHolder(@NonNull cExpenseParentViewHolder EH, int position) {
        cExpenseModel expenseModel = this.expenseModels.get(position);

        EH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                R.color.child_body_colour));

        EH.textViewParentCaption.setText(R.string.activity_caption);
        EH.textViewParent.setText(expenseModel.getActivityModel().getName());
        EH.textViewNameCaption.setText(R.string.input_caption);
        //--EH.textViewName.setText(expenseModel.getResourceModel().getName());
        EH.textViewNumberCaption.setText(R.string.input_expense_caption);
        EH.textViewNumber.setText(String.valueOf(expenseModel.getExpense()));
        //--EH.textViewDescription.setText(expenseModel.getResourceModel().getDescription());
        EH.textViewStartDate.setText(sdf.format(expenseModel.getStartDate()));
        EH.textViewEndDate.setText(sdf.format(expenseModel.getEndDate()));

        /* collapse and expansion of the details */
        EH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        EH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                cFontManager.FONTAWESOME));
        EH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        EH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_details));
        EH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /* icon for syncing a record */
        EH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
        EH.textViewSyncIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        EH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        EH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
        EH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
            }
        });

        /* icon for deleting a record */
        EH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        EH.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        EH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        EH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
        EH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
            }
        });

        /* icon for saving updated record */
        EH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        EH.textViewUpdateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        EH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        EH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
        EH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseModels.size();
    }

    public void setExpenseModels(ArrayList<cExpenseModel> expenseModels){
        this.expenseModels = expenseModels;
        this.filteredExpenseModels = expenseModels;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredExpenseModels = expenseModels;
                } else {

                    ArrayList<cExpenseModel> filteredList = new ArrayList<>();
                    for (cExpenseModel expenseModel : expenseModels) {
//--                        if (expenseModel.getResourceModel().getName().toLowerCase().
//                                contains(charString.toLowerCase())) {
//                            filteredList.add(expenseModel);
//                        }
                    }

                    filteredExpenseModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredExpenseModels.size();
                filterResults.values = filteredExpenseModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //assert (ArrayList<cTreeModel>) filterResults.values != null;
                filteredExpenseModels = (ArrayList<cExpenseModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class cExpenseParentViewHolder extends RecyclerView.ViewHolder {
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

        private cExpenseParentViewHolder(final View viewHolder) {
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
//    cExpenseChildViewHolder HCH = ((cExpenseChildViewHolder) viewHolder);
//                    HCH.setPaddingLeft(20 * node.getLevel());
//
//                            HCH.resourcesPlaceholderView.removeAllViews();
//
//                            for (int i = 0; i < objects.size(); i++) {
//        /* list of users under human group */
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