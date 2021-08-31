package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cMenuAdapter extends cTreeAdapter implements Filterable {
    private static final String TAG = cMenuAdapter.class.getSimpleName();
//    private static final SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PERM_MENU = 0;
    private static final int MAIN_MENU = 1;
    private static final int SUB_MENU  = 2;

    private final Context context;
    private List<cTreeModel> menuTree;
    private List<cTreeModel> filteredMenuTree;

    public cMenuAdapter(Context context, List<cTreeModel> menuTree) {
        super(context, menuTree);
        this.context = context;
        this.menuTree = menuTree;
        this.filteredMenuTree = menuTree;
    }

    public void setMenuWithSubMenu(List<cTreeModel> menuTree) {
        this.menuTree = menuTree;
        this.filteredMenuTree = menuTree;
    }

    @Override
    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {
            case PERM_MENU:
                view = inflater.inflate(R.layout.session_permission_cardview, parent, false);
                viewHolder = new cPermMenuViewHolder(view);
                break;
            case MAIN_MENU:
                view = inflater.inflate(R.layout.session_menu_entity_cardview, parent, false);
                viewHolder = new cMainMenuViewHolder(view);
                break;
            case SUB_MENU:
                view = inflater.inflate(R.layout.session_menu_entity_cardview, parent, false);
                viewHolder = new cSubMenuViewHolder(view);
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
                case PERM_MENU:
                    cPermissionModel permissionModel = (cPermissionModel) obj.getModelObject();
                    cPermMenuViewHolder PH = ((cPermMenuViewHolder) viewHolder);

                    PH.setPaddingLeft(20 * node.getLevel());

                    /* permission menu */
                    PH.textViewName.setText(permissionModel.getName());
                    PH.textViewDescription.setText(permissionModel.getDescription());

                    /* the collapse and expansion of the main menu */
                    if (node.isLeaf()) {
                        PH.textViewDetailIcon.setVisibility(View.GONE);
                    } else {

                        PH.textViewDetailIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            PH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            PH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        } else {
                            PH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            PH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        }
                    }
                    PH.textViewDetailIcon.setOnClickListener(v -> expandOrCollapse(position));

                    /* icon for saving updated record */
                    PH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    PH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    PH.textViewUpdateIcon.setOnClickListener(view -> {
                        //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
                    });

                    /* icon for deleting a record */
                    PH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    PH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    PH.textViewDeleteIcon.setOnClickListener(view -> {
                        //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
                    });

                    break;

                case MAIN_MENU:
                    cMenuModel menuModel = (cMenuModel) obj.getModelObject();
                    cMainMenuViewHolder MH = ((cMainMenuViewHolder) viewHolder);

                    //Gson gson = new Gson();
                    //Log.d(TAG, "==========================> "+gson.toJson(menuModel.getName()));

                    MH.setPaddingLeft(20 * node.getLevel());

                    /* check box to select the menu item */
                    MH.checkBoxMenu.setChecked(menuModel.isChecked());
                    MH.checkBoxMenu.setOnClickListener(view -> {
                        boolean checked = ((CheckBox) view).isChecked();
                        // Check which checkbox was clicked
                        if (checked){
                            Log.d(TAG, "Checked =====> "+checked);
                        }
                        else{
                            Log.d(TAG, "Unchecked =====> "+checked);
                        }
                        //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
                    });

                    /* menu details */
                    MH.textViewName.setText(menuModel.getName());
                    MH.textViewDescription.setText(menuModel.getDescription());

                    /* the collapse and expansion of the main menu */
                    if (node.isLeaf()) {
                        MH.textViewDetailIcon.setVisibility(View.GONE);
                    } else {

                        MH.textViewDetailIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            MH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            MH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            MH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            MH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            MH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            MH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }
                    MH.textViewDetailIcon.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case SUB_MENU:
                    cMenuModel subMenuModel = (cMenuModel) obj.getModelObject();
                    cSubMenuViewHolder SH = ((cSubMenuViewHolder) viewHolder);

                    SH.setPaddingLeft(20 * node.getLevel());

                    SH.textViewDetailIcon.setVisibility(View.GONE);

                    /* check box to select the menu item */
                    SH.checkBoxMenu.setChecked(subMenuModel.isChecked());
                    SH.checkBoxMenu.setOnClickListener(view -> {
                        //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
                    });

                    /* menu details */
                    SH.textViewName.setText(subMenuModel.getName());
                    SH.textViewDescription.setText(subMenuModel.getDescription());

                    break;
            }
        }
    }

//    @Override
//    public int getItemCount() {
//        return filteredMenuTree.size();
//    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    filteredTeamsRolesTree = teamsRolesTree;
//                } else {
//
//                    List<cTeamModel> filteredList = new ArrayList<>();
//                    for (cTreeModel teamModel : teamsRolesTree) {
//                        if (teamModel.getName().toLowerCase().
//                                contains(charString.toLowerCase())) {
//                            filteredList.add(teamModel);
//                        }
//                    }
//
//                    filteredTeamsRolesTree = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
                //filterResults.count = filteredTeamModels.size();
                //filterResults.values = filteredTeamModels;

                return null;//filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //filteredTeamModels = (ArrayList<cTeamModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public static class cPermMenuViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewName;
        private final TextView textViewDescription;
        private final TextView textViewDetailIcon;
        private final TextView textViewUpdateIcon;
        private final TextView textViewDeleteIcon;

        private final View viewHolder;

        private cPermMenuViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
            this.textViewUpdateIcon = viewHolder.findViewById(R.id.textViewUpdateIcon);
            this.textViewDeleteIcon = viewHolder.findViewById(R.id.textViewDeleteIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cMainMenuViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkBoxMenu;
        private final TextView textViewName;
        private final TextView textViewDescription;
        private final TextView textViewDetailIcon;

        private final View viewHolder;

        private cMainMenuViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.checkBoxMenu = viewHolder.findViewById(R.id.checkBoxMenu);
            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cSubMenuViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatCheckBox checkBoxMenu;
        private final TextView textViewName;
        private final TextView textViewDescription;
        private final TextView textViewDetailIcon;

        private final View viewHolder;

        private cSubMenuViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.checkBoxMenu = viewHolder.findViewById(R.id.checkBoxMenu);
            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}