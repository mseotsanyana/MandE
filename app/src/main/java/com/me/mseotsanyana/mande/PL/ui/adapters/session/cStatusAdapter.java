package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.me.mseotsanyana.mande.BLL.domain.session.cStatusDomain;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;

import java.util.ArrayList;

/**
 * Created by mseotsanyana on 2018/01/22.
 */

public class cStatusAdapter extends RecyclerView.Adapter<cStatusAdapter.cStatusViewHolder> implements Filterable {
    private static final String TAG = cStatusAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<cStatusDomain> listStatus;
    private ArrayList<cStatusDomain> filteredStatus;
    //private int permissionStatus;
    private AppCompatCheckBox appCompatCheckBoxAll;

    private PopupWindow popWindow;

    //private cSessionManager sessionManager;

    public cStatusAdapter(Context context, ArrayList<cStatusDomain> listStatus,
                          int permissionStatus, AppCompatCheckBox appCompatCheckBoxAll) {
        this.context = context;
        this.listStatus = listStatus;
        this.filteredStatus = listStatus;
        //this.permissionStatus = permissionStatus;// the variable to UPDATE
        this.appCompatCheckBoxAll = appCompatCheckBoxAll;

        //sessionManager = new cSessionManager(context);
    }

    @Override
    public cStatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.status_cardview, parent, false);

        return new cStatusViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cStatusViewHolder SH, final int position) {
        //if (SH != null && filteredStatus.size() > 0) {
        SH.textViewStatusName.setText(filteredStatus.get(position).getName());
        SH.textViewStatusDescription.setText(filteredStatus.get(position).getDescription());
        SH.switchStatus.setChecked(filteredStatus.get(position).isState());
        SH.switchStatus.setTag(filteredStatus.get(position));

        SH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        SH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        SH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_chevron_right));

        //}

        SH.switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Switch sc = (Switch) buttonView;
                cStatusDomain statusDomain = (cStatusDomain) sc.getTag();

                statusDomain.setDirty(true);
                filteredStatus.get(position).setDirty(true);

                Log.d(TAG,"Detail Test...");

                if (((Switch) buttonView).isChecked()) {
                    filteredStatus.get(position).setState(true);
                    statusDomain.setState(true);
                } else {
                    filteredStatus.get(position).setState(false);
                    statusDomain.setState(false);
                }

                if (appCompatCheckBoxAll != null) {
                    if (isAllValuesChecked()) {
                        appCompatCheckBoxAll.setChecked(true);
                    } else {
                        appCompatCheckBoxAll.setChecked(false);
                    }
                }
            }
        });

        SH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onShowPopup(view);
                Log.d(TAG,"Detail Test...");
            }
        });
    }


    // call this method when required to show popup
    /*public void onShowPopup(View v){

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate the custom popup layout
        final View inflatedView = layoutInflater.inflate(R.layout.me_popup_common_attributes, null,false);

        // get device size
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        //mDeviceHeight = size.y;

        // set height depends on the device size
        popWindow = new PopupWindow(inflatedView, size.x - 50,size.y - 1300, true );
        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.fb_popup_bg,
                context.getTheme()));

        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(true);

        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);

        // show the popup at bottom of the screen and set some margin at bottom ie,
        popWindow.showAtLocation(v, Gravity.CENTER, 0,100);
    }
*/
    /*
     * find if all values are checked.
     */
    public boolean isAllValuesChecked() {
        for (int i = 0; i < filteredStatus.size(); i++) {
            if (!filteredStatus.get(i).isState()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return filteredStatus.size();
    }

    public ArrayList<cStatusDomain> getItems() {
        return filteredStatus;
    }

    /**
     * <p>Returns a filter that can be used to constrain data with a filtering
     * pattern.</p>
     * <p>
     * <p>This method is usually implemented by {@link Adapter}
     * classes.</p>
     *
     * @return a filter used to constrain data
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredStatus = listStatus;
                } else {

                    ArrayList<cStatusDomain> filteredList = new ArrayList<>();

                    for (cStatusDomain statusDomain : listStatus) {

                        if (statusDomain.getName().toLowerCase().contains(charString.toLowerCase())) {

                            filteredList.add(statusDomain);
                        }
                    }

                    filteredStatus = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredStatus;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredStatus = (ArrayList<cStatusDomain>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    /**
     * cViewHolder class
     */
    public class cStatusViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewStatusName;
        public TextView textViewStatusDescription;
        public Switch switchStatus;
        public TextView textViewDetailIcon;

        public cStatusViewHolder(View view) {
            super(view);
            textViewStatusName = (TextView) view.findViewById(R.id.textViewStatusName);
            textViewStatusDescription = (TextView) view.findViewById(R.id.textViewStatusDescription);
            switchStatus = (Switch) view.findViewById(R.id.switchStatus);
            textViewDetailIcon = (TextView) view.findViewById(R.id.textViewDetailIcon);
        }
    }
}
