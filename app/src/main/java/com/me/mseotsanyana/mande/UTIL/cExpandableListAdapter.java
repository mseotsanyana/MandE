package com.me.mseotsanyana.mande.UTIL;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.me.mseotsanyana.mande.R;

public class cExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    private List<String> expandableListTitle;
    private Map<String, List<String>> expandableMenuItems;

    public cExpandableListAdapter(Context context, List<String> expandableListTitle,
                                  Map<String, List<String>> expandableMenuItems) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableMenuItems = expandableMenuItems;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return expandableMenuItems.get(expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.dashboard_drawer_sub_menu, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return expandableMenuItems.get(expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String listTitle = (String) getGroup(listPosition);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.dashboard_drawer_main_menu, null);
        }
        // set main menu title
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        // set arrows for the main menu title
        TextView listTitleTextArrowView = (TextView) convertView
                .findViewById(R.id.listTitleArrow);
        listTitleTextArrowView.setTypeface(null, Typeface.NORMAL);
        listTitleTextArrowView.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));

        // set icons for menu items
        TextView listTitleTextIconView = (TextView) convertView
                .findViewById(R.id.listTitleIcon);
        listTitleTextIconView.setTypeface(null, Typeface.NORMAL);
        listTitleTextIconView.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));

        if (listPosition == 0)
            listTitleTextIconView.setText(context.getResources().getString(R.string.fa_admin));
        else if (listPosition == 1)
            listTitleTextIconView.setText(context.getResources().getString(R.string.fa_profile));
        else if (listPosition == 2)
            listTitleTextIconView.setText(context.getResources().getString(R.string.fa_notification));
        else if (listPosition == 3)
            listTitleTextIconView.setText(context.getResources().getString(R.string.fa_setting));
        else if (listPosition == 4)
            listTitleTextIconView.setText(context.getResources().getString(R.string.fa_upload));
        else if (listPosition == 5)
            listTitleTextIconView.setText(context.getResources().getString(R.string.fa_download));
        else if (listPosition == 6)
            listTitleTextIconView.setText(context.getResources().getString(R.string.fa_logout));


        // set arrow icons for relevant items
        if (listPosition == 0 || listPosition == 4 || listPosition == 5) {
            if (!isExpanded)
                listTitleTextArrowView.setText(context.getResources().getString(R.string.fa_chevron_right));
            else
                listTitleTextArrowView.setText(context.getResources().getString(R.string.fa_chevron_down));
        } else {
            listTitleTextArrowView.setText("");

        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    public List<String> getExpandableListTitle() {
        return expandableListTitle;
    }

    public void setExpandableListTitle(List<String> expandableListTitle) {
        this.expandableListTitle = expandableListTitle;
    }

    public Map<String, List<String>> getExpandableMenuItems() {
        return expandableMenuItems;
    }

    public void setExpandableMenuItems(Map<String, List<String>> expandableMenuItems) {
        this.expandableMenuItems = expandableMenuItems;
    }
}
