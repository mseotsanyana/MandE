package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cOrganizationAdapter extends RecyclerView.Adapter<cOrganizationAdapter.cOrganizationViewHolder>
        implements Filterable {
    private static String TAG = cOrganizationAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private final Context context;
    private List<cOrganizationModel> organizationModels;
    private List<cOrganizationModel> filteredOrganizationModels;

    public cOrganizationAdapter(Context context, List<cOrganizationModel> organizationModels) {
        this.context = context;
        this.organizationModels = organizationModels;
        this.filteredOrganizationModels = organizationModels;
    }

    public void setOrganizationModels(List<cOrganizationModel> organizationModels) {
        this.organizationModels = organizationModels;
        this.filteredOrganizationModels = organizationModels;
    }

    @Override
    public int getItemCount() {
        return filteredOrganizationModels.size();
    }

    @NonNull
    @Override
    public cOrganizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.session_organization_cardview, parent, false);
        return new cOrganizationViewHolder(view);
    }

    public void onBindViewHolder(@NonNull cOrganizationViewHolder OH, int position) {
        cOrganizationModel organizationModel = this.filteredOrganizationModels.get(position);

        //HH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
        //        R.color.child_body_colour));

        /* icon for name of an organization */
        OH.textViewOrganizationIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewOrganizationIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));

        if (organizationModel.getOrganizationType() == 0) {
            OH.textViewOrganizationIcon.setTextColor(Color.RED);
        } else if (organizationModel.getOrganizationType() == 1) {
            OH.textViewOrganizationIcon.setTextColor(Color.GREEN);
        } else if (organizationModel.getOrganizationType() == 2) {
            OH.textViewOrganizationIcon.setTextColor(Color.BLUE);
        } else {
            OH.textViewOrganizationIcon.setTextColor(Color.MAGENTA);
        }

        OH.textViewOrganizationIcon.setText(context.getResources().getString(R.string.fa_organization));
        OH.textViewName.setText(organizationModel.getName());

        OH.textViewEmailIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewEmailIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.textViewEmailIcon.setTextColor(context.getColor(R.color.black));
        OH.textViewEmailIcon.setText(context.getResources().getString(R.string.fa_email));
        OH.textViewEmail.setText(organizationModel.getEmail());

        OH.textViewWebsiteIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewWebsiteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.textViewWebsiteIcon.setTextColor(context.getColor(R.color.black));
        OH.textViewWebsiteIcon.setText(context.getResources().getString(R.string.fa_website));
        OH.textViewWebsite.setText(organizationModel.getWebsite());

        /* icon for deleting a record */
        OH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
        OH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
            }
        });

        /* icon for saving updated record */
        OH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewUpdateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
        OH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
            }
        });

        /* icon for joining a record */
        OH.textViewJoinIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewJoinIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.textViewJoinIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.textViewJoinIcon.setText(context.getResources().getString(R.string.fa_join));
        OH.textViewJoinIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
            }
        });

        /* icon for creating a record */
        OH.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewCreateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
        OH.textViewCreateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
            }
        });

        // collapse and expansion of the details of the role
        OH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        OH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
        OH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(OH.expandableLayout.isExpanded())) {
                    OH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
                } else {
                    OH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                }

                OH.expandableLayout.toggle();
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredOrganizationModels = organizationModels;
                } else {

                    ArrayList<cOrganizationModel> filteredList = new ArrayList<>();
                    for (cOrganizationModel organizationModel : organizationModels) {
                        if (organizationModel.getName().toLowerCase().
                                contains(charString.toLowerCase())) {
                            filteredList.add(organizationModel);
                        }
                    }

                    filteredOrganizationModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredOrganizationModels.size();
                filterResults.values = filteredOrganizationModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredOrganizationModels = (ArrayList<cOrganizationModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class cOrganizationViewHolder extends RecyclerView.ViewHolder {
        private final cExpandableLayout expandableLayout;

        private final AppCompatTextView textViewName;
        private final AppCompatTextView textViewEmail;
        private final AppCompatTextView textViewWebsite;

        private final AppCompatTextView textViewOrganizationIcon;
        private final AppCompatTextView textViewEmailIcon;
        private final AppCompatTextView textViewWebsiteIcon;

        private final AppCompatTextView textViewDeleteIcon;
        private final AppCompatTextView textViewUpdateIcon;
        private final AppCompatTextView textViewJoinIcon;
        private final AppCompatTextView textViewCreateIcon;
        private final AppCompatTextView textViewDetailIcon;

        private final View viewHolder;

        private cOrganizationViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            CardView cardView = viewHolder.findViewById(R.id.cardView);
            this.expandableLayout = viewHolder.findViewById(R.id.expandableLayout);

            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewEmail = viewHolder.findViewById(R.id.textViewEmail);
            this.textViewWebsite = viewHolder.findViewById(R.id.textViewWebsite);

            this.textViewOrganizationIcon = viewHolder.findViewById(R.id.textViewOrganizationIcon);
            this.textViewEmailIcon = viewHolder.findViewById(R.id.textViewEmailIcon);
            this.textViewWebsiteIcon = viewHolder.findViewById(R.id.textViewWebsiteIcon);

            this.textViewDeleteIcon = viewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewUpdateIcon = viewHolder.findViewById(R.id.textViewUpdateIcon);
            this.textViewJoinIcon = viewHolder.findViewById(R.id.textViewJoinIcon);
            this.textViewCreateIcon = viewHolder.findViewById(R.id.textViewCreateIcon);
            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}