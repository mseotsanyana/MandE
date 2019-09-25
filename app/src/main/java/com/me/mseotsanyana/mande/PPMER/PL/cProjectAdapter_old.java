package com.me.mseotsanyana.mande.PPMER.PL;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.SparseBooleanArray;

import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.PPMER.BLL.cProjectDomain;
import com.me.mseotsanyana.mande.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;


/**
 * Created by mseotsanyana on 2016/08/02.
 */

public class cProjectAdapter_old extends RecyclerView.Adapter<cProjectAdapter_old.cProjectViewHolder> {

    Context context;
    private List<cProjectDomain> projectList = new ArrayList<>();
    LayoutInflater inflater;

    private int selectedItem = 0;

    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    private cProjectFragment projectFragment;
    private cAddProjectFragment addProjectFragment;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    private HashSet<Integer> mExpandedPositionSet = new HashSet<>();

    public cProjectAdapter_old(Context context, List<cProjectDomain> projectList,
                               cProjectFragment projectFragment) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.projectList = projectList;
        this.projectFragment = projectFragment;
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public cProjectDomain getItem(int position) {
        return projectList.get(position);
    }

    public void removeItem(int position) {
        projectList.remove(position);
    }

    public void addItem(cProjectDomain projectDomain) {
        projectList.add(projectDomain);
    }

    public void updateItem(cProjectDomain projectDomain, int position) {
        removeItem(position);
        projectList.add(position, projectDomain);
    }

    @Override
    public cProjectViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.project_cardview, viewGroup, false);

        return new cProjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cProjectViewHolder projectViewHolder, int position) {
        cProjectDomain ci = projectList.get(position);

        projectViewHolder.txtName.setText("Project Name : "+ci.getProjectName());

        //projectViewHolder.txtDescription.setHeight(100);
        projectViewHolder.vCountry.setText("      : "+ci.getCountry());
        projectViewHolder.vRegion.setText("      : "+ci.getRegion());
        projectViewHolder.vStatus.setText("      : "+ci.getProjectStatus());
        projectViewHolder.vStartDate.setText("      : "+formatter.format(ci.getStartDate()));
        projectViewHolder.vCloseDate.setText("      : "+formatter.format(ci.getCloseDate()));
        projectViewHolder.vManager.setText("      : "+ci.getProjectManagerID());
        projectViewHolder.vDescription.setText(ci.getProjectDescription());

        projectViewHolder.itemView.setSelected(selectedItems.get(position, false));

        projectViewHolder.updateItem(position);
    }

    public class cProjectViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtName;
        protected TextView vDescription;
        protected TextView txtDescription;
        protected TextView vCountry;
        protected TextView vRegion;
        protected TextView vStatus;
        protected TextView vStartDate;
        protected TextView vCloseDate;
        protected TextView vManager;

        private cExpandableLayout expandableLayout ;

        public cProjectViewHolder(final View view) {
            super(view);

            txtName =  (TextView) view.findViewById(R.id.txtName);
            //txtDescription = (TextView)  view.findViewById(R.id.txtDescription);
            vCountry = (TextView)  view.findViewById(R.id.valCountry);
            vRegion = (TextView) view.findViewById(R.id.valRegion);
            vStatus = (TextView) view.findViewById(R.id.valStatus);
            vStartDate = (TextView) view.findViewById(R.id.valStartDate);
            vCloseDate = (TextView) view.findViewById(R.id.valCloseDate);
            vManager = (TextView) view.findViewById(R.id.valManager);
            vDescription = (TextView)  view.findViewById(R.id.valDescription);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    // Save the selected positions to the SparseBooleanArray
                    if (selectedItems.get(getAdapterPosition(), false)) {
                        selectedItems.delete(getAdapterPosition());
                        view.setSelected(false);

                    }
                    else {
                        selectedItems.put(getAdapterPosition(), true);
                        view.setSelected(true);
                    }
                    //notifyDataSetChanged();
                    //Toast.makeText(projectFragment.getActivity(), "DETAILS selected "+selectedItem,
                    //        Toast.LENGTH_SHORT).show();

                }
            });

            ImageView quickActionImage = (ImageView)view.findViewById(R.id.quick_action);

            quickActionImage.setOnClickListener(new RecyclerView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectFragment.onclickImageView(view, getAdapterPosition());
                    //Toast.makeText(projectFragment.getActivity(), "DETAILS selected "+getAdapterPosition(),
                    //        Toast.LENGTH_SHORT).show();
                }
            });

            expandableLayout = (cExpandableLayout) itemView.findViewById(R.id.project_expandablelayout_id);
        }

        private void updateItem(final int position) {
            expandableLayout.setOnExpandListener(new cExpandableLayout.OnExpandListener() {
                @Override
                public void onExpand(boolean expanded) {
                    registerExpand(position);
                }
            });
            expandableLayout.setExpand(mExpandedPositionSet.contains(position));

        }
    }

    private void registerExpand(int position) {
        if (mExpandedPositionSet.contains(position)) {
            removeExpand(position);
        }else {
            addExpand(position);
        }
    }

    private void removeExpand(int position) {
        mExpandedPositionSet.remove(position);
    }

    private void addExpand(int position) {
        mExpandedPositionSet.add(position);
    }
  }

/*
public class cProjectAdapter_old extends CursorAdapter
{
    public cProjectAdapter_old(Context context, Cursor cursor, int flags)
    {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup)
    {
        return LayoutInflater.from(context).inflate(R.layout.project_info, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        // find fields to populate in inflated template
        TextView tvName = (TextView)view.findViewById(R.id.name);
        TextView tvDescription = (TextView)view.findViewById(R.id.description);

        // extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow("projectName"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("projectDescription"));

        // populate fields with extracted properties
        tvName.setText(name);
        tvDescription.setText(description);
    }
}
*/


                    //((MainActivity)context).mQuickAction.show(view);
                    // sent a view to the activity
                    /*
                    cEvent.cAdapterActivityMessage adapterActivityMessageEvent =
                            new cEvent.cAdapterActivityMessage(view);

                    cGlobalBus.getBus().post(adapterActivityMessageEvent);
                    */
