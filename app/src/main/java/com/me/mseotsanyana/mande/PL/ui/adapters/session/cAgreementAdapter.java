package com.me.mseotsanyana.mande.PL.ui.adapters.session;

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

import com.me.mseotsanyana.mande.BLL.model.session.cAgreementModel;
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

public class cAgreementAdapter extends RecyclerView.Adapter<cAgreementAdapter.cAgreementViewHolder>
        implements Filterable {
    private static String TAG = cAgreementAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    //private final iActivityPresenter.View activityPresenterView;
    //private final iInputPresenter.View inputPresenterView;

    private Context context;
    private ArrayList<cAgreementModel> agreementModels;
    private List<cAgreementModel> filteredAgreementModels;

    public cAgreementAdapter(Context context, iInputPresenter.View inputPresenterView,
                             ArrayList<cAgreementModel> agreementModels) {
        this.context = context;
        this.agreementModels = agreementModels;
        this.filteredAgreementModels = agreementModels;

        //this.inputPresenterView = inputPresenterView;
        //this.inputPresenterView = inputPresenterView;
    }

    @NonNull
    @Override
    public cAgreementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.resources_parent_cardview, parent, false);
        return new cAgreementViewHolder(view);
    }

    public void onBindViewHolder(@NonNull cAgreementViewHolder HH, int position) {
        cAgreementModel agreementModel = this.filteredAgreementModels.get(position);

        HH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                R.color.child_body_colour));

        //HH.textViewParentCaption.setText(R.string.activity_caption);
        //HH.textViewParent.setText(agreementModel.getActivityModel().getName());
        //HH.textViewNameCaption.setText(R.string.input_caption);
        //--HH.textViewName.setText(humanModel.getResourceModel().getName());
        //HH.textViewNumberCaption.setText(R.string.input_number_caption);
        //HH.textViewNumber.setText(String.valueOf(humanModel.getQuantity()));
        //--HH.textViewDescription.setText(humanModel.getResourceModel().getDescription());
        //HH.textViewStartDate.setText(sdf.format(humanModel.getStartDate()));
        //HH.textViewEndDate.setText(sdf.format(humanModel.getEndDate()));

        /* collapse and expansion of the details */
        HH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        HH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                cFontManager.FONTAWESOME));
        HH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        HH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_details));
        HH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /* icon for syncing a record */
        HH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
        HH.textViewSyncIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        HH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        HH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
        HH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
            }
        });

        /* icon for deleting a record */
        HH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        HH.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        HH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        HH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
        HH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
            }
        });

        /* icon for saving updated record */
        HH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        HH.textViewUpdateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        HH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        HH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
        HH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;//filteredAgreementModels.size();
    }

    public void setAgreementModels(ArrayList<cAgreementModel> agreementModels) {
        this.agreementModels = agreementModels;
        this.filteredAgreementModels = agreementModels;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredAgreementModels = agreementModels;
                } else {

                    ArrayList<cAgreementModel> filteredList = new ArrayList<>();
                    for (cAgreementModel agreementModel : agreementModels) {
//--                        if (humanModel.getResourceModel().getName().toLowerCase().
//                                contains(charString.toLowerCase())) {
//                            filteredList.add(humanModel);
//                        }
                    }

                    filteredAgreementModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredAgreementModels.size();
                filterResults.values = filteredAgreementModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //assert (ArrayList<cTreeModel>) filterResults.values != null;
                filteredAgreementModels = (ArrayList<cAgreementModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class cAgreementViewHolder extends RecyclerView.ViewHolder {
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

        private cAgreementViewHolder(final View viewHolder) {
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
//    cHumanAdapter.cHumanChildViewHolder HCH = ((cHumanChildViewHolder) viewHolder);
//                    HCH.setPaddingLeft(20 * node.getLevel());
//
//                            HCH.resourcesPlaceholderView.removeAllViews();
//
//                            for (int i = 0; i < objects.size(); i++) {
//        /* list of users under human group */
//        if (objects.get(i) instanceof cUserModel) {
//        if (i == 0) {
//        HCH.resourcesPlaceholderView.addView(new cLogFrameHeaderView(
//        context, "Set of Human Resources"));
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
//        context, "Key Performance Questions"));
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
