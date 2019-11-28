package com.me.mseotsanyana.mande.BRBAC.PL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
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

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BRBAC.BLL.cEntityDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOperationDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOrganizationDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOrganizationHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cPermissionDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionManager;
import com.me.mseotsanyana.mande.BRBAC.BLL.cStatusDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cUserDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cUserHandler;
import com.me.mseotsanyana.mande.INTERFACE.iTreeAdapterCallback;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTILITY.cFontManager;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.cMultiSpinnerSearch;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSpinnerListener;
import com.me.mseotsanyana.multiselectspinnerlibrary.cTableSpinner;
import com.me.mseotsanyana.multiselectspinnerlibrary.cTableSpinnerListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.me.mseotsanyana.mande.UTILITY.cConstant.NUM_PERMS;

/**
 * Created by mseotsanyana on 2018/01/22.
 */

public class cStatusTreeAdapter extends RecyclerView.Adapter<cStatusTreeAdapter.cStatusViewHolder>
        implements Filterable {
    private static final String TAG = cStatusTreeAdapter.class.getSimpleName();
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "dd MMMM, yyyy hh:mm:ss a", Locale.US);

    private Context context;

    private ArrayList<cStatusDomain> listStatus;
    private ArrayList<cStatusDomain> modifiedStatuses;
    private ArrayList<cStatusDomain> filteredStatus;
    private ArrayList<cPermissionDomain> permissionDomains;

    private cOperationDomain origOperationDomain;
    private cStatusDomain[] origStatusDomains;

    private int privilegeID;
    private cEntityDomain entityDomain;
    private cOperationDomain operationDomain;
    private cStatusDomain statusDomain;

    private cUserHandler userHandler;
    private cOrganizationHandler organizationHandler;

    private AppCompatCheckBox appCompatCheckBoxOperation;
    private AppCompatCheckBox appCompatCheckBoxAllStatuses;

    private TextView textViewCreatedDate;
    private TextView textViewModifiedDate;
    private TextView textViewSyncedDate;
    private AppCompatButton appCompatButtonComSave;
    private AppCompatTextView appCompatTextViewCancelIcon;

    private String createdDate;
    private String modifiedDate;
    private String syncedDate;

    private PopupWindow popWindow;

    private cSessionManager session;

    private iTreeAdapterCallback callback;

    final Gson gson = new Gson();

    public cStatusTreeAdapter(Context context, cSessionManager session,
                              int privilegeID, cEntityDomain entityDomain,
                              cOperationDomain operationDomain,
                              ArrayList<cStatusDomain> listStatus,
                              ArrayList<cPermissionDomain> permissionDomains,
                              cOperationDomain origOperationDomain,
                              cStatusDomain[] origStatusDomains,
                              AppCompatCheckBox appCompatCheckBoxOperation,
                              AppCompatCheckBox appCompatCheckBoxAllStatuses,
                              iTreeAdapterCallback callback) {
        this.context = context;
        this.session = session;

        this.listStatus = listStatus;
        this.filteredStatus = listStatus;
        this.modifiedStatuses = new ArrayList<>();

        this.privilegeID = privilegeID;
        this.entityDomain = entityDomain;
        this.permissionDomains = permissionDomains;

        this.operationDomain = operationDomain;
        this.origOperationDomain = origOperationDomain;
        this.origStatusDomains = origStatusDomains;

        this.userHandler = new cUserHandler(context, session);
        this.organizationHandler = new cOrganizationHandler(context, session);

        this.appCompatCheckBoxOperation = appCompatCheckBoxOperation;
        this.appCompatCheckBoxAllStatuses = appCompatCheckBoxAllStatuses;

        this.callback = callback;
    }

    @Override
    public cStatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.status_tree_cardview, parent, false);

        return new cStatusViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cStatusViewHolder SH, final int position) {
        SH.textViewStatusName.setText(listStatus.get(position).getName());
        SH.textViewStatusDescription.setText(listStatus.get(position).getDescription());
        SH.switchStatus.setChecked(listStatus.get(position).isState());
        SH.switchStatus.setTag(listStatus.get(position));

        SH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        SH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        SH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_chevron_right));

        //Log.d(TAG, "Position: "+position);
        SH.switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Switch sc = (Switch) buttonView;
                statusDomain = (cStatusDomain) sc.getTag();

                if (isChecked) {
                    if(origStatusDomains[position].isState() && !origStatusDomains[position].isDirty()) {
                        statusDomain.setDirty(false);
                    }else{
                        statusDomain.setDirty(true);
                    }
                    statusDomain.setState(true);

                    Log.d(TAG, "Name: "+statusDomain.getName()+
                            ", State: "+statusDomain.isState()+
                            ", Dirt: "+statusDomain.isDirty());
                }else{
                    if(!origStatusDomains[position].isState() && !origStatusDomains[position].isDirty()) {
                        statusDomain.setDirty(false);
                    }else{
                        statusDomain.setDirty(true);
                    }
                    statusDomain.setState(false);

                    Log.d(TAG, "Name: "+statusDomain.getName()+
                            ", State: "+statusDomain.isState()+
                            ", Dirt: "+statusDomain.isDirty());
                }

                /** check 'all statuses' checkbox if all radio buttons are checked **/
                if (appCompatCheckBoxAllStatuses != null) {
                    /** all statuses are selected, then change all status checkbox **/
                    if (isAllValuesChecked(listStatus)) {
                        appCompatCheckBoxAllStatuses.setChecked(true);
                    } else {
                        appCompatCheckBoxAllStatuses.setChecked(false);
                    }

                    /** all statuses are not selected, then uncheck operation checkbox **/
                    if (isAllValuesUnChecked(listStatus)) {
                        appCompatCheckBoxOperation.setChecked(false);
                    }

                    /** all or some statuses are selected, then check operation checkbox **/
                    if (appCompatCheckBoxAllStatuses.isChecked() ||
                            isSomeValuesChecked(listStatus)) {
                        appCompatCheckBoxOperation.setChecked(true);
                    }
                }
                //notifyItemChanged(position);

                //if (callback != null) {//fixme: to remove and put under sava button
                    // get a deep copy of permission domain to modify
                    //callback.onRefreshTreeAdapter();
                    //callback.onRefreshPermissions();
                //}
            }
        });

        SH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listStatus.get(position).isState()) {
                    onShowCommonAttributes(view, listStatus.get(position));
                    //Log.d(TAG, "Permission detail test...");
                }
            }
        });
    }

    /**
     * ViewHolder class
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
            this.setIsRecyclable(false);//FIXME: set due to switch's unpredictable behaviour.
        }
    }

    @Override
    public int getItemCount() {
        return listStatus.size();
    }

    public ArrayList<cStatusDomain> getItems() {
        return listStatus;
    }

    /*
     * find if all values are checked.
     */
    public boolean isAllValuesChecked(ArrayList<cStatusDomain> statuses) {
        for (int i = 0; i < statuses.size(); i++) {
            if (!statuses.get(i).isState()) {
                return false;
            }
        }
        return true;
    }

    /*
     * find if all values are unchecked.
     */
    public boolean isAllValuesUnChecked(ArrayList<cStatusDomain> statuses) {
        for (int i = 0; i < statuses.size(); i++) {
            if (statuses.get(i).isState()) {
                return false;
            }
        }
        return true;
    }


    /*
     * find if all values are checked.
     */
    public boolean isSomeValuesChecked(ArrayList<cStatusDomain> statuses) {
        for (int i = 0; i < statuses.size(); i++) {
            if (statuses.get(i).isState()) {
                return true;
            }
        }
        return false;
    }


/*
    public void onOwnCheckBox(cStatusViewHolder SH, boolean state) {
        SH.switchStatus.setChecked(state);
    }
*/

    public cPermissionDomain getPermissionDomain(ArrayList<cPermissionDomain> permissionDomains,
                                                 int privilegeID, int entityID, int typeID,
                                                 int operationID, int statusID) {
        cPermissionDomain permissionDomain = null;
/*
        Log.d(TAG, "PRIVILEGE ID = "+privilegeID+", ENTITY ID = "+entityID+", ENTITY TYPE ID = "+
                typeID+", OPERATION ID = "+operationID+", STATUS ID = "+statusID);
*/
        for (int i = 0; i < permissionDomains.size(); i++) {

            /*if ((permissionDomains.get(i).getPrivilegeDomain().getPrivilegeID() == privilegeID) &&
                    (permissionDomains.get(i).getEntityDomain().getEntityID() == entityID) &&
                    (permissionDomains.get(i).getEntityDomain().getTypeID() == typeID) &&
                    (permissionDomains.get(i).getOperationDomain().getOperationID() == operationID) &&
                    (permissionDomains.get(i).getStatusDomain().getStatusID() == statusID))*/ {

                /*
                Log.d(TAG, "PRIVILEGE ID = "+permissionDomains.get(i).getPrivilegeDomain().getPrivilegeID() +
                        ", ENTITY ID = "+permissionDomains.get(i).getEntityDomain().getEntityID() +
                        ", ENTITY TYPE ID = "+ permissionDomains.get(i).getEntityDomain().getTypeID() +
                        ", OPERATION ID = "+ permissionDomains.get(i).getOperationDomain().getOperationID() +
                        ", STATUS ID = "+permissionDomains.get(i).getStatusDomain().getStatusID());
                */

                permissionDomain = new cPermissionDomain(permissionDomains.get(i));

                return permissionDomain;
            }
        }

        return null;
    }

    // call this method when required to show popup
    public void onShowCommonAttributes(View view, final cStatusDomain statusDomain) {
        // get all users from database
        final ArrayList<cUserDomain> users = userHandler.getUserList(
                session.loadUserID(),        /* loggedIn user id  */
                session.loadOrgID(),         /* loggedIn own org. */
                session.loadPrimaryRole(),   /* primary group bit */
                session.loadSecondaryRoles() /* secondary group bits */
        );

        // get all organizations from database
        final ArrayList<cOrganizationDomain> orgs =
                organizationHandler.getOrganizationList(
                        session.loadUserID(),        /* loggedIn user id  */
                        session.loadOrgID(),         /* loggedIn own org. */
                        session.loadPrimaryRole(),   /* primary group bit */
                        session.loadSecondaryRoles() /* secondary group bits */
                );

        // get a deep copy of permission domain to modify
        final cPermissionDomain mPermissionDomain = getPermissionDomain(permissionDomains,
                privilegeID, entityDomain.getEntityID(), entityDomain.getTypeID(),
                operationDomain.getOperationID(), statusDomain.getStatusID());

        /** make a deepcopy of the original permission domain **/
        final cPermissionDomain originalDomain = new cPermissionDomain(mPermissionDomain);

        //Log.d(TAG, "PERMISSION "+gson.toJson(modifiedDomain));

        // create inflator for a popup layout with the views below
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate the custom popup layout
        final View inflatedView = layoutInflater.inflate(R.layout.me_popup_common_attributes,
                null, false);

        // create a pair list of user ids and names
        final List<cKeyPairBoolData> keyPairBoolUsers = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            idNameBool.setId(users.get(i).getUserID());
            idNameBool.setName(users.get(i).getName());
            if ((mPermissionDomain != null) &&
                    (mPermissionDomain.getOwnerID() == users.get(i).getUserID())) {
                idNameBool.setSelected(true);
            } else {
                idNameBool.setSelected(false);
            }
            keyPairBoolUsers.add(idNameBool);
        }
        // -1 is no by default selection, 0 to length will select corresponding values
        cSingleSpinnerSearch singleSpinnerSearchOwner =
                (cSingleSpinnerSearch) inflatedView.findViewById(R.id.appCompatSpinnerOwner);
        // called when click owner single spinner search
        singleSpinnerSearchOwner.setItems(keyPairBoolUsers, -1, new cSpinnerListener() {
            @Override
            public void onItemsSelected(List<cKeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        mPermissionDomain.setOwnerID((int) items.get(i).getId());
                        break;
                    }
                }
                Log.d(TAG, "OWNER : " + mPermissionDomain.getOwnerID());
            }
        });

        // create a pair list of organization ids and names
        final List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
        for (int i = 0; i < orgs.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            idNameBool.setId(orgs.get(i).getOrganizationID());
            idNameBool.setName(orgs.get(i).getName());
            if ((mPermissionDomain != null) &&
                    (mPermissionDomain.getOrgID() == orgs.get(i).getOrganizationID())) {
                idNameBool.setSelected(true);
            } else {
                idNameBool.setSelected(false);
            }
            keyPairBoolOrgs.add(idNameBool);
        }
        // -1 is no by default selection, 0 to length will select corresponding values
        cSingleSpinnerSearch singleSpinnerSearchOrg =
                (cSingleSpinnerSearch) inflatedView.findViewById(R.id.appCompatSpinnerOrg);
        // called when click organization single spinner search
        singleSpinnerSearchOrg.setItems(keyPairBoolOrgs, -1, new cSpinnerListener() {
            @Override
            public void onItemsSelected(List<cKeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        mPermissionDomain.setOrgID((int) items.get(i).getId());
                        break;
                    }
                }
                Log.d(TAG, "ORGANIZATION OWNER : " + mPermissionDomain.getOrgID());
            }
        });

        // create a pair list of other organization ids and names
        final List<cKeyPairBoolData> keyPairBoolOtherOrgs = new ArrayList<>();
        for (int i = 0; i < orgs.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            idNameBool.setId(orgs.get(i).getOrganizationID());
            idNameBool.setName(orgs.get(i).getName());
            if ((mPermissionDomain != null) &&
                    ((mPermissionDomain.getGroupBITS() & orgs.get(i).getOrganizationID()) ==
                            orgs.get(i).getOrganizationID())) {
                idNameBool.setSelected(true);
            } else {
                idNameBool.setSelected(false);
            }
            keyPairBoolOtherOrgs.add(idNameBool);
        }
        // -1 is no by default selection, 0 to length will select corresponding values
        cMultiSpinnerSearch multiSpinnerSearchOtherOrg =
                (cMultiSpinnerSearch) inflatedView.findViewById(R.id.appCompatSpinnerOtherOrg);
        // called when click other organization multi spinner search
        multiSpinnerSearchOtherOrg.setItems(keyPairBoolOrgs, -1, new cSpinnerListener() {
            @Override
            public void onItemsSelected(List<cKeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    int orgID = (int) items.get(i).getId();
                    if (items.get(i).isSelected()) {
                        if ((mPermissionDomain.getGroupBITS() & orgID) != orgID) {
                            // add other organizations
                            mPermissionDomain.setGroupBITS(mPermissionDomain.getGroupBITS() | orgID);
                        }
                    }
                    if (!items.get(i).isSelected()) {
                        if ((mPermissionDomain.getGroupBITS() & orgID) == orgID) {
                            // remove other organizations
                            mPermissionDomain.setGroupBITS(mPermissionDomain.getGroupBITS() & ~orgID);
                        }
                    }
                }
                Log.d(TAG, "OTHER ORGANIZATION : " + mPermissionDomain.getGroupBITS());
            }
        });

        // create a pair list of permission ids and names
        final cKeyPairBoolData[] keyPairBoolPerms = new cKeyPairBoolData[NUM_PERMS];
        if (permissionDomains.size() > 0) {
            int opBITS = permissionDomains.get(0).getPermsBITS();
            //keyPairBoolPerms[0].setId();keyPairBoolPerms[0].setName();
            for (int i = 0; i < session.permissions.length; i++) {
                //Log.d(TAG, " "+(opBITS & session.permissions[i]));
                cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                idNameBool.setId(session.permissions[i]);
                idNameBool.setName(session.perm_names[i]);
                idNameBool.setSelected((opBITS & session.permissions[i]) == session.permissions[i]);
                keyPairBoolPerms[i] = idNameBool;
            }
        }
        // -1 is no by default selection, 0 to length will select corresponding values
        cTableSpinner tableSpinner =
                (cTableSpinner) inflatedView.findViewById(R.id.appCompatSpinnerPerms);
        // called when click permissions multi spinner search
        tableSpinner.setItems(keyPairBoolPerms, -1, new cTableSpinnerListener() {
            @Override
            public void onFixedItemsSelected(cKeyPairBoolData[] items) {
                for (int i = 0; i < items.length; i++) {
                    int permID = (int) items[i].getId();
                    if (items[i].isSelected()) {
                        if ((mPermissionDomain.getPermsBITS() & permID) != permID) {
                            // add operation
                            mPermissionDomain.setPermsBITS(mPermissionDomain.getPermsBITS() | permID);
                        }
                    }
                    if (!items[i].isSelected()) {
                        if ((mPermissionDomain.getPermsBITS() & permID) == permID) {
                            // remove operation
                            mPermissionDomain.setPermsBITS(mPermissionDomain.getPermsBITS() & ~permID);
                        }
                    }
                }
                Log.d(TAG, "PERMS : " + mPermissionDomain.getPermsBITS());
            }
        });

        // create a pair list of statuses ids and names
        final List<cKeyPairBoolData> keyPairBoolStatuses = new ArrayList<>();
        for (int i = 0; i < listStatus.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            idNameBool.setId(listStatus.get(i).getStatusID());
            idNameBool.setName(listStatus.get(i).getName());
            if ((mPermissionDomain != null) &&
                    ((mPermissionDomain.getStatusBITS() & listStatus.get(i).getStatusID()) ==
                            listStatus.get(i).getStatusID())) {
                idNameBool.setSelected(true);
            } else {
                idNameBool.setSelected(false);
            }
            keyPairBoolStatuses.add(idNameBool);
        }
        // -1 is no by default selection, 0 to length will select corresponding values
        cMultiSpinnerSearch multiSpinnerSearchStatuses =
                (cMultiSpinnerSearch) inflatedView.findViewById(R.id.appCompatSpinnerStatuses);
        // called when click statuses multi spinner search
        multiSpinnerSearchStatuses.setItems(keyPairBoolStatuses, -1, new cSpinnerListener() {
            @Override
            public void onItemsSelected(List<cKeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    int statusID = (int) items.get(i).getId();
                    if (items.get(i).isSelected()) {
                        if ((mPermissionDomain.getStatusBITS() & statusID) != statusID) {
                            // add status
                            mPermissionDomain.setStatusBITS(mPermissionDomain.getStatusBITS() | statusID);
                        }
                    }
                    if (!items.get(i).isSelected()) {
                        if ((mPermissionDomain.getStatusBITS() & statusID) == statusID) {
                            // remove status
                            mPermissionDomain.setStatusBITS(mPermissionDomain.getStatusBITS() & ~statusID);
                        }
                    }
                }
                Log.d(TAG, "STATUSES : " + mPermissionDomain.getStatusBITS());
            }
        });

        textViewCreatedDate = (TextView) inflatedView.findViewById(R.id.textViewCreatedDate);
        textViewModifiedDate = (TextView) inflatedView.findViewById(R.id.textViewModifiedDate);
        textViewSyncedDate = (TextView) inflatedView.findViewById(R.id.textViewSyncedDate);

        appCompatButtonComSave = (AppCompatButton) inflatedView.findViewById(R.id.appCompatButtonComSave);
        appCompatTextViewCancelIcon = (AppCompatTextView) inflatedView.findViewById(R.id.appCompatTextViewCancelIcon);

        //textViewStatusIconOther.setOnClickListener(new View.OnClickListener()

        /** assign icons to title of popup window **/
        appCompatTextViewCancelIcon.setTypeface(null, Typeface.NORMAL);
        appCompatTextViewCancelIcon.setTypeface(cFontManager.getTypeface(context,
                cFontManager.FONTAWESOME));
        appCompatTextViewCancelIcon.setText(context.getResources().getString(R.string.fa_com_attr));

        for (int i = 0; i < permissionDomains.size(); i++) {
            /*if ((privilegeID == permissionDomains.get(i).getPrivilegeDomain().getPrivilegeID()) &&
                    (entityDomain.getEntityID() == permissionDomains.get(i).getEntityDomain().getEntityID()) &&
                    (entityDomain.getTypeID() == permissionDomains.get(i).getEntityDomain().getTypeID()) &&
                    (operationDomain.getOperationID() == permissionDomains.get(i).getOperationDomain().getOperationID()) &&
                    (statusDomain.getStatusID() == permissionDomains.get(i).getStatusDomain().getStatusID())) */{

                createdDate = formatter.format(permissionDomains.get(i).getCreatedDate());
                modifiedDate = formatter.format(permissionDomains.get(i).getModifiedDate());
                syncedDate = formatter.format(permissionDomains.get(i).getSyncedDate());

                textViewCreatedDate.setText(createdDate);
                textViewModifiedDate.setText(modifiedDate);
                textViewSyncedDate.setText(syncedDate);

                //Log.d(TAG, "PERMISSION = "+gson.toJson(permissionDomains.get(i)));
            }
        }

        // get device size
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        //mDeviceHeight = size.y;

        // set height depends on the device size
        popWindow = new PopupWindow(inflatedView, size.x - 120, size.y - 1150, true);
        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.fb_popup_bg,
                context.getTheme()));

        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(false);

        // make it outside touchable to dismiss the popup window
        //popWindow.setOutsideTouchable(true);

        // show the popup at bottom of the screen and set some margin at bottom ie,
        popWindow.showAtLocation(view, Gravity.CENTER, 0, 100);

        appCompatButtonComSave.setText("Change");

        appCompatButtonComSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPermissionDomain != null) {
                    // keep a list of modified status domains'
                    if (statusDomain.isDirty()) {
                        modifiedStatuses.add(statusDomain);
                    }

                    /* update part of permission domain */
                    Date timestamp = new Date();
                    textViewModifiedDate.setText(formatter.format(timestamp));
                    mPermissionDomain.setModifiedDate(timestamp);

                    if (callback != null) {
                        callback.onUpdatePermissions(originalDomain, mPermissionDomain);
                    }

                    //Log.d(TAG, "UPDATE "+gson.toJson(statusDomain));
                    //Log.d(TAG, "PERMISSION "+gson.toJson(mPermissionDomain));
                }else{

                    if (callback != null) {//FIXME
                        callback.onCreatePermissions(statusDomain);
                    }
                    Log.d(TAG, "CREATE "+gson.toJson(statusDomain));
                    Log.d(TAG, "PERMISSION "+gson.toJson(mPermissionDomain));
                }
                popWindow.dismiss();
            }
        });

        appCompatTextViewCancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
            }
        });
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
}
