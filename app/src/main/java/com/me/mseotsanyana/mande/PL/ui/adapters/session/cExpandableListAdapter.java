package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;

import java.util.List;
import java.util.Objects;

public class cExpandableListAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private List<cMenuModel> menuModels;

    public cExpandableListAdapter(Context context, List<cMenuModel> menuModels) {
        this.context = context;
        this.menuModels = menuModels;
    }

    @Override
    public cMenuModel getChild(int groupPosition, int childPosititon) {
        return this.menuModels.get(groupPosition).getSubMenuModels().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (this.menuModels.get(groupPosition).getSubMenuModels() == null)
            return 0;
        else
            return Objects.requireNonNull(this.menuModels.get(groupPosition).getSubMenuModels()).size();
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).getName();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.me_list_group_child, null);
        }

        TextView txtListChild = convertView.findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public cMenuModel getGroup(int groupPosition) {
        return this.menuModels.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return this.menuModels.size();

    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String menuItemTitle = getGroup(groupPosition).getName();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.me_list_group_header, null);
        }

        // set icons for menu items
        TextView menuItemIconView = (TextView) convertView.findViewById(R.id.menuItemIcon);
        menuItemIconView.setTypeface(null, Typeface.NORMAL);
        menuItemIconView.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));

        if (menuModels.get(groupPosition).getMenuServerID() == 0)
            menuItemIconView.setText(context.getResources().getString(R.string.fa_profile));
        else if (menuModels.get(groupPosition).getMenuServerID() == 8)
            menuItemIconView.setText(context.getResources().getString(R.string.fa_admin));
        else if (menuModels.get(groupPosition).getMenuServerID() == 256)
            menuItemIconView.setText(context.getResources().getString(R.string.fa_notification));
        else if (menuModels.get(groupPosition).getMenuServerID() == 512)
            menuItemIconView.setText(context.getResources().getString(R.string.fa_setting));
        else if (menuModels.get(groupPosition).getMenuServerID() == 1024)
            menuItemIconView.setText(context.getResources().getString(R.string.fa_logout));

        // set main menu title
        TextView menuItemTitleView = convertView.findViewById(R.id.menuItemTitle);
        menuItemTitleView.setTypeface(null, Typeface.BOLD);
        menuItemTitleView.setText(menuItemTitle);

        // set arrow icons for relevant items
        TextView menuItemArrowView = (TextView) convertView.findViewById(R.id.menuItemArrow);
        menuItemArrowView.setTypeface(null, Typeface.NORMAL);
        menuItemArrowView.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));

        if (menuModels.get(groupPosition).getMenuServerID() == 0 || menuModels.get(groupPosition).getMenuServerID() == 8) {
            if (!isExpanded)
                menuItemArrowView.setText(context.getResources().getString(R.string.fa_chevron_right));
            else
                menuItemArrowView.setText(context.getResources().getString(R.string.fa_chevron_down));
        } else {
            menuItemArrowView.setText("");
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public List<cMenuModel> getMenuModels() {
        return menuModels;
    }

    public void setMenuModels(List<cMenuModel> menuModels) {
        this.menuModels = menuModels;
    }
}
