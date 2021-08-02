package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.me.mseotsanyana.mande.R.color.gray;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cTeamMemberAdapter extends cTreeAdapter implements Filterable {
    private static String TAG = cTeamMemberAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int TEAM = 0;
    private static final int USER = 1;

    private final Context context;
    private List<cTreeModel> teamsMembersTree;
    private List<cTreeModel> filteredTeamsMembersTree;

    public cTeamMemberAdapter(Context context, List<cTreeModel> teamsMembersTree) {
        super(context, teamsMembersTree);
        this.context = context;
        this.teamsMembersTree = teamsMembersTree;
        this.filteredTeamsMembersTree = teamsMembersTree;
    }

    public void setTeamsWithMembers(List<cTreeModel> teamsMembersTree) {
        this.teamsMembersTree = teamsMembersTree;
        this.filteredTeamsMembersTree = teamsMembersTree;
    }

    @Override
    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case TEAM:
                view = inflater.inflate(R.layout.session_team_cardview, parent,
                        false);
                viewHolder = new cTeamViewHolder(view);
                break;
            case USER:
                view = inflater.inflate(R.layout.user_less_detail_list, parent,
                        false);
                viewHolder = new cUserViewHolder(view);
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        cNode node = visibleNodes.get(position);
        cTreeModel obj = (cTreeModel) node.getObj();

        if (obj != null) {
            switch (obj.getType()) {
                case TEAM:
                    cTeamModel teamModel = (cTeamModel) obj.getModelObject();
                    cTeamViewHolder TH = ((cTeamViewHolder) viewHolder);

                    TH.setPaddingLeft(20 * node.getLevel());

                    /* team details */
                    TH.textViewName.setText(teamModel.getName());
                    TH.textViewDescription.setText(teamModel.getDescription());

                    /* the collapse and expansion of the team */
                    if (node.isLeaf()) {
                        TH.textViewDetailIcon.setVisibility(View.GONE);
                    } else {

                        TH.textViewDetailIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            TH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            TH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            TH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        } else {
                            TH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            TH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            TH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        }
                    }
                    TH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    /* icon for joining a record */
                    TH.textViewJoinIcon.setTypeface(null, Typeface.NORMAL);
                    TH.textViewJoinIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    TH.textViewJoinIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    TH.textViewJoinIcon.setText(context.getResources().getString(R.string.fa_join));
                    TH.textViewJoinIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
                        }
                    });

                    /* icon for creating a record */
                    TH.textViewAddIcon.setTypeface(null, Typeface.NORMAL);
                    TH.textViewAddIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    TH.textViewAddIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    TH.textViewAddIcon.setText(context.getResources().getString(R.string.fa_create));
                    TH.textViewAddIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
                        }
                    });

                    /* icon for saving updated record */
                    TH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    TH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    TH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    TH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    TH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
                        }
                    });

                    /* icon for deleting a record */
                    TH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    TH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    TH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    TH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    TH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
                        }
                    });

                    break;

                case USER:
                    cUserProfileModel userModel = (cUserProfileModel) obj.getModelObject();
                    cUserViewHolder UH = ((cUserViewHolder) viewHolder);

                    UH.setPaddingLeft(20 * node.getLevel());

                    UH.circleImageViewUser.setImageResource(gray);
                    UH.textViewName.setText(userModel.getName() +" "+userModel.getSurname());
                    UH.textViewPhone.setText(userModel.getPhone());
                    UH.textViewEmail.setText(userModel.getEmail());

                    /* icon for deleting a record */
                    UH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    UH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    UH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorAccent));
                    UH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));

                    break;
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
                    filteredTeamsMembersTree = teamsMembersTree;
                } else {

                    /*ArrayList<cTeamModel> filteredList = new ArrayList<>();
                   for (cTreeModel teamModel : teamsMembersTree) {
                        if (teamModel.getName().toLowerCase().
                                contains(charString.toLowerCase())) {
                            filteredList.add(teamModel);
                        }
                    }

                    filteredTeamsMembersTree = filteredList;*/
                }

                FilterResults filterResults = new FilterResults();
                //filterResults.count = filteredTeamModels.size();
                //filterResults.values = filteredTeamModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //filteredTeamModels = (ArrayList<cTeamModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class cTeamViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatTextView textViewName;
        private final AppCompatTextView textViewDescription;

        private final AppCompatTextView textViewDetailIcon;
        private final AppCompatTextView textViewJoinIcon;
        private final AppCompatTextView textViewAddIcon;
        private final AppCompatTextView textViewUpdateIcon;
        private final AppCompatTextView textViewDeleteIcon;

        private final View viewHolder;

        private cTeamViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);

            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
            this.textViewJoinIcon = viewHolder.findViewById(R.id.textViewJoinIcon);
            this.textViewAddIcon = viewHolder.findViewById(R.id.textViewAddIcon);
            this.textViewUpdateIcon = viewHolder.findViewById(R.id.textViewUpdateIcon);
            this.textViewDeleteIcon = viewHolder.findViewById(R.id.textViewDeleteIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cUserViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView circleImageViewUser;

        private final AppCompatTextView textViewName;
        private final AppCompatTextView textViewPhone;
        private final AppCompatTextView textViewEmail;
        private final AppCompatTextView textViewDeleteIcon;

        private final View viewHolder;

        private cUserViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.circleImageViewUser = viewHolder.findViewById(R.id.circleImageViewUser);

            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewPhone = viewHolder.findViewById(R.id.textViewPhone);
            this.textViewEmail = viewHolder.findViewById(R.id.textViewEmail);
            this.textViewDeleteIcon = viewHolder.findViewById(R.id.textViewDeleteIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}