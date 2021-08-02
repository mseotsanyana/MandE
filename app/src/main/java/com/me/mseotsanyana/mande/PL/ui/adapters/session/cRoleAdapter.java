package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.UTIL.INTERFACE.iPermissionInterface;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.cMultiSpinnerSearch;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch_old;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSpinnerListener;
import com.me.mseotsanyana.multiselectspinnerlibrary.cTableSpinner;
import com.me.mseotsanyana.multiselectspinnerlibrary.cTableSpinnerListener;
import com.me.mseotsanyana.quickactionlibrary.cCustomActionItemText;
import com.me.mseotsanyana.quickactionlibrary.cCustomQuickAction;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.me.mseotsanyana.mande.UTIL.cConstant.FORMAT_DATE;
import static com.me.mseotsanyana.mande.UTIL.cConstant.NUM_PERMS;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cRoleAdapter extends cTreeAdapter implements Filterable {
    private static String TAG = cRoleAdapter.class.getSimpleName();

    private Context context;
    //private cSessionManager session;

    public static final int ROLE = 0;
    public static final int USER = 1;

    private ArrayList<cRoleModel> listRoles;
    private ArrayList<cRoleModel> filteredRoles;

    //private cUserHandler userHandler;
//    private cOrganizationHandler organizationHandler;
    //private cRoleHandler roleHandler;

    private cStatusAdapter statusAdapter;
    private ArrayList<cStatusModel> statusDomains;

    private String createdDate;
    private String modifiedDate;
    private String syncedDate;

    private iPermissionInterface roleInterface;

    Gson gson = new Gson();

    public cRoleAdapter(Context context,
                        List<cTreeModel> treeModels,
                        ArrayList<cStatusModel> statusDomains,
                        int expLevel, iPermissionInterface roleInterface) {
        super(context, treeModels, expLevel);

        this.context = context;
        //this.session = session;

        //this.listRoles = new ArrayList<>();
        //this.listRoles = roleDomains;
        this.filteredRoles = listRoles;

        // used to mask statuses
        this.statusDomains = statusDomains;

        //this.userHandler = null;//new cUserHandler(context, session);
        //this.roleHandler = null;//new cRoleHandler(context, session);
        //this.organizationHandler = null;//new cOrganizationHandler(context, session);

        this.roleInterface = roleInterface;

    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {

        //Gson gson = new Gson();
        //Log.d("View Type = ", "" + viewType);

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view, viewStatus;
        switch (viewType) {
            case ROLE:
                view = inflater.inflate(R.layout.session_role_cardview, parent, false);
                //viewStatus = inflater.inflate(R.layout.status_cardview, parent, false);
                viewHolder = new cRoleTreeViewHolder(view);
                break;
            case USER:
                view = inflater.inflate(R.layout.role_user_cardview, parent, false);
                viewHolder = new cUserTreeViewHolder(view);
                break;
            default:
                viewHolder = null;
                break;
        }

        return viewHolder;
    }

    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        cNode node = visibleNodes.get(position);

        cTreeModel obj = (cTreeModel) node.getObj();

        if (obj != null) {
            switch (obj.getType()) {
                case ROLE:
                    final cRoleModel roleDomain = (cRoleModel) obj.getModelObject();
                    final cRoleTreeViewHolder RVH = ((cRoleTreeViewHolder) viewHolder);

                    Log.d(TAG, gson.toJson(roleDomain));

                    // the name and description of the role
                    RVH.textViewRoleName.setText(roleDomain.getName());
                    RVH.textViewDescription.setText(roleDomain.getDescription());

                    RVH.setPaddingLeft(40 * node.getLevel());

                    // the collapse and expansion of the roles
                    if (node.isLeaf()) {
                        RVH.textViewExpandRoleIcon.setVisibility(View.INVISIBLE);
                    } else {
                        RVH.textViewExpandRoleIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            RVH.textViewExpandRoleIcon.setTypeface(null, Typeface.NORMAL);
                            RVH.textViewExpandRoleIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            RVH.textViewExpandRoleIcon.setText(context.getResources().getString(R.string.fa_minus));

                        } else {
                            RVH.textViewExpandRoleIcon.setTypeface(null, Typeface.NORMAL);
                            RVH.textViewExpandRoleIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            RVH.textViewExpandRoleIcon.setText(context.getResources().getString(R.string.fa_plus));
                        }
                    }
                    RVH.textViewExpandRoleIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    // collapse and expansion of the details of the role
                    RVH.textViewRoleDetailIcon.setTypeface(null, Typeface.NORMAL);
                    RVH.textViewRoleDetailIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    RVH.textViewRoleDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));                    RVH.textViewRoleDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                    RVH.textViewRoleDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!(RVH.expandableLayout.isExpanded())){
                                RVH.textViewRoleDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
                            }else{
                                RVH.textViewRoleDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                            }

                            RVH.expandableLayout.toggle();
                        }
                    });

                    // number of users under a role
                    RVH.textViewCountUser.setText("USERS: " + node.numberOfChildren());

                    // synchronise with the remote database
                    RVH.textViewSyncRoleIcon.setTypeface(null, Typeface.NORMAL);
                    RVH.textViewSyncRoleIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    RVH.textViewSyncRoleIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    RVH.textViewSyncRoleIcon.setText(context.getResources().getString(R.string.fa_sync));
                    RVH.textViewSyncRoleIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                    // quick actions on roles
                    RVH.textViewQuickActionIcon.setTypeface(null, Typeface.NORMAL);
                    RVH.textViewQuickActionIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    RVH.textViewQuickActionIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    RVH.textViewQuickActionIcon.setText(context.getResources().getString(R.string.fa_actions));
                    RVH.textViewQuickActionIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // create the quick action view, passing the view anchor
                            cCustomQuickAction quickAction = cCustomQuickAction.Builder(view);

                            // set the adapter
                            quickAction.setAdapter(new cQAAdapter(context));

                            // set the number of columns ( setting -1 for auto )
                            quickAction.setNumColumns(-1);
                            quickAction.setOnClickListener(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int position) {
                                    switch (position) {
                                        case 0:
                                            Toast.makeText(context, "Selected item: 0 " + position, Toast.LENGTH_SHORT).show();
                                            break;
                                        case 1:
                                            Toast.makeText(context, "Selected item: 1 " + position, Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            Toast.makeText(context, "Error! ", Toast.LENGTH_SHORT).show();
                                            break;
                                    }

                                    dialog.dismiss();

                                }
                            });

                            // finally show the view
                            quickAction.show();
                        }
                    });


                    /** icon for saving changed entity common attributes **/
                    RVH.textViewEditRoleIcon.setTypeface(null, Typeface.NORMAL);
                    RVH.textViewEditRoleIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    RVH.textViewEditRoleIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    RVH.textViewEditRoleIcon.setText(context.getResources().getString(R.string.fa_update));
                    RVH.textViewEditRoleIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_save));
                            faIcon.setTextColor(context.getColor(R.color.colorAccent));
                            alertDialogBuilder.setIcon(faIcon);

                            // set title
                            alertDialogBuilder.setTitle("Save Role.");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Do you want to SAVE role: " +
                                            RVH.textViewRoleName.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // update the permissions in the database

                                            //EVH.createPermissions();
                                            dialog.dismiss();
                                            //notifyItemChanged(position);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                    });

                    /** icon for deleting an entity related permissions **/
                    RVH.textViewDeleteRoleIcon.setTypeface(null, Typeface.NORMAL);
                    RVH.textViewDeleteRoleIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    RVH.textViewDeleteRoleIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    RVH.textViewDeleteRoleIcon.setText(context.getResources().getString(R.string.fa_delete));
                    RVH.textViewDeleteRoleIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_delete));
                            faIcon.setTextColor(context.getColor(R.color.colorAccent));
                            alertDialogBuilder.setIcon(faIcon);

                            // set title
                            alertDialogBuilder.setTitle("Remove Role.");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Do you want to REMOVE role: " +
                                            RVH.textViewRoleName.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // update the permissions in the database
                                            //EVH.createPermissions();
                                            //permissionHandler.deletePermission()
                                            dialog.dismiss();
                                            //notifyItemChanged(position);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                    });

                    /** icon for syncing an entity related permissions **/
                    RVH.textViewSyncRoleIcon.setTypeface(null, Typeface.NORMAL);
                    RVH.textViewSyncRoleIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    RVH.textViewSyncRoleIcon.setText(context.getResources().getString(R.string.fa_sync));
                    RVH.textViewSyncRoleIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_sync));
                            faIcon.setTextColor(context.getColor(R.color.colorAccent));
                            alertDialogBuilder.setIcon(faIcon);

                            // set title
                            alertDialogBuilder.setTitle("Sync Role.");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Do you want to SYNCHRONISE role: " +
                                            RVH.textViewRoleName.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // update the permissions in the database
                                            //EVH.createPermissions();
                                            dialog.dismiss();
                                            //notifyItemChanged(position);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                    });

                    /** common attributes **/

                    // get all users from database
                    final ArrayList<cUserModel> users = null;/*userHandler.getUserList(
                            session.loadUserID(),         loggedIn user id
                            session.loadOrgID(),          loggedIn own org.
                            session.loadPrimaryRole(),    primary group bit
                            session.loadSecondaryRoles()  secondary group bits
                    );*/

                    // get all organizations from database
                    final ArrayList<cOrganizationModel> orgs =null;/*
                            organizationHandler.getOrganizationList(
                                    session.loadUserID(),        /* loggedIn user id
                                    session.loadOrgID(),         /* loggedIn own org.
                                    session.loadPrimaryRole(),   /* primary group bit
                                    session.loadSecondaryRoles() /* secondary group bits
                            );*/

                    /* get the detailed of the loggedin user */
                    //final cUserDomain loggedInUser = session.loadCurrentUser();

                    // create a pair list of user ids and names
                    final List<cKeyPairBoolData> keyPairBoolUsers = new ArrayList<>();
                    for (int i = 0; i < users.size(); i++) {
                        cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                        idNameBool.setId(users.get(i).getUserID());
                        idNameBool.setName(users.get(i).getName());
                        if (true/*roleDomain.getOwnerID() == users.get(i).getUserID()*/) {
                            idNameBool.setSelected(true);
                        } else {
                            idNameBool.setSelected(false);
                        }
                        keyPairBoolUsers.add(idNameBool);
                    }
                    // -1 is no by default selection, 0 to length will select corresponding values
                    // called when click owner single spinner search
                    RVH.appCompatTextViewOwner.setText("Owner :");
                    RVH.singleSpinnerSearchOwner.setItems(keyPairBoolUsers, -1,
                            new cSpinnerListener() {
                                @Override
                                public void onItemsSelected(List<cKeyPairBoolData> items) {
                                    for (int i = 0; i < items.size(); i++) {
                                        if (items.get(i).isSelected()) {
                                            roleDomain.setRoleServerID(""/*(int) items.get(i).getId()*/);
                                            break;
                                        }
                                    }
                                    Log.d(TAG, "OWNER : " + roleDomain.getRoleServerID());
                                }
                            });

                    // create a pair list of organization ids and names
                    final List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
                    for (int i = 0; i < orgs.size(); i++) {
                        cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                        //-idNameBool.setId(orgs.get(i).getOrganizationID());
                        idNameBool.setName(orgs.get(i).getName());
                        if (true/*roleDomain.getOrganizationID() == orgs.get(i).getOrganizationID()*/) {
                            idNameBool.setSelected(true);
                        } else {
                            idNameBool.setSelected(false);
                        }
                        keyPairBoolOrgs.add(idNameBool);
                    }
                    // -1 is no by default selection, 0 to length will select corresponding values
                    // called when click organization single spinner search
                    RVH.singleSpinnerSearchOrg.setItems(keyPairBoolOrgs, -1, new cSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<cKeyPairBoolData> items) {
                            for (int i = 0; i < items.size(); i++) {
                                if (items.get(i).isSelected()) {
                                    //roleDomain.setOrganizationID((int) items.get(i).getId());
                                    break;
                                }
                            }
                            //Log.d(TAG, "ORGANIZATION ID : " + roleDomain.getOrganizationID());
                        }
                    });

                    // create a pair list of other organization ids and names
                    final List<cKeyPairBoolData> keyPairBoolOtherOrgs = new ArrayList<>();
                    for (int i = 0; i < orgs.size(); i++) {
                        cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                        //-idNameBool.setId(orgs.get(i).getOrganizationID());
                        idNameBool.setName(orgs.get(i).getName());
                        if (true){//(session.loadSecondaryRoles() & orgs.get(i).getOrganizationID()) == orgs.get(i).getOrganizationID()*/) {
                            idNameBool.setSelected(true);
                        } else {
                            idNameBool.setSelected(false);
                        }
                        keyPairBoolOtherOrgs.add(idNameBool);
                    }
                    // -1 is no by default selection, 0 to length will select corresponding values
                    // called when click other organization multi spinner search
                    RVH.multiSpinnerSearchOtherOrg.setItems(keyPairBoolOtherOrgs, -1, new cSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<cKeyPairBoolData> items) {
                            for (int i = 0; i < items.size(); i++) {
                                int orgID = (int) items.get(i).getId();
                                if (items.get(i).isSelected()) {
                                    /*if ((roleDomain.getGroupBITS() & orgID) != orgID) {
                                        // add other organizations
                                        roleDomain.setGroupBITS(roleDomain.getGroupBITS() | orgID);
                                    }*/
                                }
                                if (!items.get(i).isSelected()) {
                                    /*if ((roleDomain.getGroupBITS() & orgID) == orgID) {
                                        // remove other organizations
                                        roleDomain.setGroupBITS(roleDomain.getGroupBITS() & ~orgID);
                                    }*/
                                }
                            }
                            //Log.d(TAG, "OTHER ORGANIZATION : " + roleDomain.getGroupBITS());
                        }
                    });

                    // create a pair list of permission ids and names
                    final cKeyPairBoolData[] keyPairBoolPerms = new cKeyPairBoolData[NUM_PERMS];
                    //if (permissionDomains.size() > 0) {
                    //int opBITS = roleDomain.getPermsBITS();
                    //keyPairBoolPerms[0].setId();keyPairBoolPerms[0].setName();
                    /*for (int i = 0; i < session.permissions.length; i++) {
                        //Log.d(TAG, " "+(opBITS & session.permissions[i]));
                        cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                        idNameBool.setId(session.permissions[i]);
                        idNameBool.setName(session.perm_names[i]);
                        idNameBool.setSelected((opBITS & session.permissions[i]) == session.permissions[i]);
                        keyPairBoolPerms[i] = idNameBool;
                    }*/
                    //}
                    // -1 is no by default selection, 0 to length will select corresponding values
                    // called when click permissions multi spinner search
                    RVH.tableSpinner.setItems(keyPairBoolPerms, -1, new cTableSpinnerListener() {
                        @Override
                        public void onFixedItemsSelected(cKeyPairBoolData[] items) {
                            for (int i = 0; i < items.length; i++) {
                                int permID = (int) items[i].getId();
                                /*if (items[i].isSelected()) {
                                    if ((roleDomain.getPermsBITS() & permID) != permID) {
                                        // add operation
                                        roleDomain.setPermsBITS(roleDomain.getPermsBITS() | permID);
                                    }
                                }
                                if (!items[i].isSelected()) {
                                    if ((roleDomain.getPermsBITS() & permID) == permID) {
                                        // remove operation
                                        roleDomain.setPermsBITS(roleDomain.getPermsBITS() & ~permID);
                                    }
                                }*/
                            }
                            //Log.d(TAG, "PERMS : " + roleDomain.getPermsBITS());
                        }
                    });

                    // create a pair list of statuses ids and names
                    final List<cKeyPairBoolData> keyPairBoolStatuses = new ArrayList<>();
                    for (int i = 0; i < statusDomains.size(); i++) {
                        cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                        idNameBool.setId(statusDomains.get(i).getStatusServerID());
                        idNameBool.setName(statusDomains.get(i).getName());
                        if (true/*(roleDomain.getStatusBITS() & statusDomains.get(i).getStatusID()) ==
                                statusDomains.get(i).getStatusID()*/) {
                            idNameBool.setSelected(true);
                        } else {
                            idNameBool.setSelected(false);
                        }
                        keyPairBoolStatuses.add(idNameBool);
                    }
                    // -1 is no by default selection, 0 to length will select corresponding values
                    // called when click statuses multi spinner search
                    RVH.multiSpinnerSearchStatuses.setItems(keyPairBoolStatuses, -1, new cSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<cKeyPairBoolData> items) {
                            for (int i = 0; i < items.size(); i++) {
                                int statusID = (int) items.get(i).getId();
                                /*if (items.get(i).isSelected()) {
                                    if ((roleDomain.getStatusBITS() & statusID) != statusID) {
                                        // add status
                                        roleDomain.setStatusBITS(roleDomain.getStatusBITS() | statusID);
                                    }
                                }
                                if (!items.get(i).isSelected()) {
                                    if ((roleDomain.getStatusBITS() & statusID) == statusID) {
                                        // remove status
                                        roleDomain.setStatusBITS(roleDomain.getStatusBITS() & ~statusID);
                                    }
                                }*/
                            }
                           // Log.d(TAG, "STATUSES : " + roleDomain.getStatusBITS());
                        }
                    });

                    createdDate = FORMAT_DATE.format(roleDomain.getCreatedDate());
                    modifiedDate = FORMAT_DATE.format(roleDomain.getModifiedDate());
                    syncedDate = FORMAT_DATE.format(roleDomain.getSyncedDate());

                    RVH.textViewCreatedDate.setText(createdDate);
                    RVH.textViewModifiedDate.setText(modifiedDate);
                    RVH.textViewSyncedDate.setText(syncedDate);

                    /** change perms **/
                    RVH.textViewChangeUserIcon.setTypeface(null, Typeface.NORMAL);
                    RVH.textViewChangeUserIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    RVH.textViewChangeUserIcon.setText(context.getResources().getString(R.string.fa_perms));
                    RVH.textViewChangeUserIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_perms));
                            faIcon.setTextColor(context.getColor(R.color.colorAccent));
                            alertDialogBuilder.setIcon(faIcon);

                            // set title
                            alertDialogBuilder.setTitle("Change Permissions.");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Do you want to CHANGE permissions for " +
                                            RVH.textViewRoleName.getText() + "'s Record ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // update user record permissions in the database
                                            roleDomain.setModifiedDate(new Date());
                                            if(true/*roleHandler.updateRole(roleDomain)*/){
                                                roleInterface.onResponseMessage(1,
                                                        2);
                                            }else{
                                                roleInterface.onResponseMessage(1,
                                                        2);
                                            }
                                            dialog.dismiss();
                                            notifyItemChanged(position);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                    });

                    break;

                case USER:
                    cUserModel userDomain = (cUserModel) obj.getModelObject();
                    cUserTreeViewHolder UVH = ((cUserTreeViewHolder) viewHolder);

                    UVH.setPaddingLeft(40 * node.getLevel());
                    //Bitmap bitmap = cBitmap.decodeByteArray(userDomain.getPhoto());
                    //UVH.imageViewUser.setImageBitmap(bitmap);
                    UVH.textViewNameSurname.setText(userDomain.getName() + " " + userDomain.getSurname());
                    UVH.textViewDescription.setText(userDomain.getDescription());

                    UVH.textViewRemoveUserIcon.setTypeface(null, Typeface.NORMAL);
                    UVH.textViewRemoveUserIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    UVH.textViewRemoveUserIcon.setText(context.getResources().getString(R.string.fa_delete));

                    break;
            }
        }
    }
/*
    @Override
    public int getItemCount() {
        return filteredRoles.size();
    }
*/
    public cRoleModel getItem(int position) {
        return filteredRoles.get(position);
    }

    public void removeItem(int position) {
        filteredRoles.remove(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredRoles = listRoles;
                } else {

                    ArrayList<cRoleModel> filteredList = new ArrayList<>();

                    for (cRoleModel roleDomain : listRoles) {

                        if (roleDomain.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                roleDomain.getDescription().toLowerCase().contains(charString)) {
                            filteredList.add(roleDomain);
                        }
                    }

                    filteredRoles = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count  = filteredRoles.size();
                filterResults.values = filteredRoles;

                Log.d(TAG,gson.toJson(filterResults));

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredRoles = (ArrayList<cRoleModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class cRoleTreeViewHolder extends cTreeViewHolder {
        private TextView textViewRoleName;
        private TextView textViewDescription;
        private TextView textViewCountUser;

        private TextView textViewQuickActionIcon;
        private TextView textViewRoleDetailIcon;

        private TextView textViewSyncRoleIcon;
        private TextView textViewDeleteRoleIcon;
        private TextView textViewEditRoleIcon;

        private cExpandableLayout expandableLayout;
        private TextView textViewExpandRoleIcon;

        private cSingleSpinnerSearch_old singleSpinnerSearchOwner;
        private AppCompatTextView appCompatTextViewOwner;

        private cSingleSpinnerSearch_old singleSpinnerSearchOrg;
        private cMultiSpinnerSearch multiSpinnerSearchOtherOrg;
        private cTableSpinner tableSpinner;
        private cMultiSpinnerSearch multiSpinnerSearchStatuses;

        private TextView textViewCreatedDate;
        private TextView textViewModifiedDate;
        private TextView textViewSyncedDate;

        private AppCompatTextView textViewChangeUserIcon;

        private View treeView;

        public cRoleTreeViewHolder(final View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;
//            this.textViewRoleName = (TextView) treeViewHolder.findViewById(R.id.textViewRoleName);
            this.textViewDescription = (TextView) treeViewHolder.findViewById(R.id.textViewDescription);
//            this.textViewCountUser = (TextView) treeViewHolder.findViewById(R.id.textViewCountUser);
//            this.textViewExpandRoleIcon = (TextView) treeViewHolder.findViewById(R.id.textViewExpandRoleIcon);
            this.textViewQuickActionIcon = (TextView) treeViewHolder.findViewById(R.id.textViewQuickActionIcon);
//            this.textViewRoleDetailIcon = (TextView) treeViewHolder.findViewById(R.id.textViewRoleDetailIcon);
//            this.textViewSyncRoleIcon = (TextView) treeViewHolder.findViewById(R.id.textViewSyncRoleIcon);
//            this.textViewDeleteRoleIcon = (TextView) treeViewHolder.findViewById(R.id.textViewDeleteRoleIcon);
//            this.textViewEditRoleIcon = (TextView) treeViewHolder.findViewById(R.id.textViewEditRoleIcon);

            this.expandableLayout = (cExpandableLayout) treeViewHolder.findViewById(R.id.expandableLayout);

            /* common attributes */
            this.singleSpinnerSearchOwner =
                    (cSingleSpinnerSearch_old) treeViewHolder.findViewById(R.id.appCompatSpinnerOwner);
            this.appCompatTextViewOwner = (AppCompatTextView)treeViewHolder.findViewById(R.id.appCompatTextViewOwner);
            this.singleSpinnerSearchOrg =
                    (cSingleSpinnerSearch_old) treeViewHolder.findViewById(R.id.appCompatSpinnerOrg);
            this.multiSpinnerSearchOtherOrg =
                    (cMultiSpinnerSearch) treeViewHolder.findViewById(R.id.appCompatSpinnerOtherOrg);
            this.tableSpinner =
                    (cTableSpinner) treeViewHolder.findViewById(R.id.appCompatSpinnerPerms);
            this.multiSpinnerSearchStatuses =
                    (cMultiSpinnerSearch) treeViewHolder.findViewById(R.id.appCompatSpinnerStatuses);

            this.textViewCreatedDate = (TextView) treeViewHolder.findViewById(R.id.textViewCreatedDate);
            this.textViewModifiedDate = (TextView) treeViewHolder.findViewById(R.id.textViewModifiedDate);
            this.textViewSyncedDate = (TextView) treeViewHolder.findViewById(R.id.textViewSyncedDate);

            this.textViewChangeUserIcon = (AppCompatTextView) treeViewHolder.findViewById(R.id.textViewChangeUserIcon);

        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cUserTreeViewHolder extends cTreeViewHolder {
        CircleImageView imageViewUser;
        TextView textViewNameSurname;
        TextView textViewDescription;
        TextView textViewRemoveUserIcon;

        private View treeView;

        public cUserTreeViewHolder(View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.imageViewUser = (CircleImageView) treeViewHolder.findViewById(R.id.imageViewUser);
            this.textViewNameSurname = (TextView) treeViewHolder.findViewById(R.id.textViewNameSurname);
            this.textViewDescription = (TextView) treeViewHolder.findViewById(R.id.textViewDescription);
            this.textViewRemoveUserIcon = (TextView) treeViewHolder.findViewById(R.id.textViewRemoveUserIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    static public class cQAAdapter extends BaseAdapter {

        final int[] ICONS = new int[]{
                R.string.fa_plus,
                R.string.fa_delete
        };

        LayoutInflater mLayoutInflater;
        List<cCustomActionItemText> mItems;
        cCustomActionItemText item;

        Context context;

        public cQAAdapter(Context context) {
            this.context = context;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            mItems = new ArrayList<cCustomActionItemText>();

            item = new cCustomActionItemText(context, "Add", ICONS[0]);
            mItems.add(item);

            item = new cCustomActionItemText(context, "Del", ICONS[1]);
            mItems.add(item);

            //item = new cCustomActionItemText(context, "Remove", ICONS[2]);
            //mItems.add(item);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int arg) {
            return mItems.get(arg);
        }

        @Override
        public long getItemId(int arg) {
            return arg;
        }

        @Override
        public View getView(int position, View arg1, ViewGroup viewGroup) {
            View view = mLayoutInflater.inflate(R.layout.action_item_flexible, viewGroup, false);

            cCustomActionItemText item = (cCustomActionItemText) getItem(position);

            TextView image = (TextView) view.findViewById(R.id.image);
            TextView text = (TextView) view.findViewById(R.id.title);

            //image.setImageDrawable(item.getIcon());
            text.setText(item.getTitle());

            image.setTypeface(null, Typeface.NORMAL);
            image.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
            image.setText(context.getResources().getString(item.getImage()));

            return view;
        }
    }
}

