package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.me.mseotsanyana.mande.R.color.gray;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cOrganizationMemberAdapter extends RecyclerView.Adapter<cOrganizationMemberAdapter.
        cOrganizationMemberViewHolder>
        implements Filterable {
    //private static final String TAG = cOrganizationMemberAdapter.class.getSimpleName();
    //private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private final Context context;
    private List<cUserProfileModel> userProfileModels;
    private List<cUserProfileModel> filteredUserProfileModels;


    public cOrganizationMemberAdapter(Context context, List<cUserProfileModel> userProfileModels) {
        this.context = context;
        this.userProfileModels = userProfileModels;
        this.filteredUserProfileModels = userProfileModels;
    }

    public void setOrganizationMemberModels(List<cUserProfileModel> userProfileModels) {
        this.userProfileModels = userProfileModels;
        this.filteredUserProfileModels = userProfileModels;
    }

    @Override
    public int getItemCount() {
        return filteredUserProfileModels.size();
    }

    @NonNull
    @Override
    public cOrganizationMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.session_org_member_cardview, parent, false);
        return new cOrganizationMemberViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull cOrganizationMemberViewHolder OH, int position) {
        cUserProfileModel userProfileModel = this.filteredUserProfileModels.get(position);

        OH.circleImageViewUser.setImageResource(gray);
        OH.textViewName.setText(userProfileModel.getName() +" "+userProfileModel.getSurname());
        OH.textViewEmail.setText(userProfileModel.getEmail());
        OH.textViewPhone.setText(userProfileModel.getPhone());

        /* icon for deleting a record */
        OH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
        OH.textViewDeleteIcon.setOnClickListener(view -> {
            //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredUserProfileModels= userProfileModels;
                } else {

                    List<cUserProfileModel> filteredList = new ArrayList<>();
                    for (cUserProfileModel userProfileModel : userProfileModels) {
                        if (userProfileModel.getName().toLowerCase().
                                contains(charString.toLowerCase())) {
                            filteredList.add(userProfileModel);
                        }
                    }

                    filteredUserProfileModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredUserProfileModels.size();
                filterResults.values = filteredUserProfileModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserProfileModels = (List<cUserProfileModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class cOrganizationMemberViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView circleImageViewUser;
        private final AppCompatTextView textViewName;
        private final AppCompatTextView textViewEmail;
        private final AppCompatTextView textViewPhone;

        private final AppCompatTextView textViewDeleteIcon;

        private final View viewHolder;

        private cOrganizationMemberViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.circleImageViewUser = (CircleImageView)
                    viewHolder.findViewById(R.id.circleImageViewUser);

            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewEmail = viewHolder.findViewById(R.id.textViewEmail);
            this.textViewPhone = viewHolder.findViewById(R.id.textViewPhone);

            this.textViewDeleteIcon = viewHolder.findViewById(R.id.textViewDeleteIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}