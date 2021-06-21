package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.BLL.model.session.cAddressModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.UTIL.INTERFACE.iMEEntityInterface;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.cMultiSpinnerSearch;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch_old;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSpinnerListener;
import com.me.mseotsanyana.multiselectspinnerlibrary.cTableSpinner;
import com.me.mseotsanyana.multiselectspinnerlibrary.cTableSpinnerListener;
import com.me.mseotsanyana.quickactionlibrary.cCustomActionItemText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.me.mseotsanyana.mande.UTIL.cConstant.FORMAT_DATE;
import static com.me.mseotsanyana.mande.UTIL.cConstant.NUM_PERMS;

/**
 * Created by mseotsanyana on 2018/01/22.
 */

public class cUserAdapter extends RecyclerView.Adapter<cUserAdapter.cUserViewHolder> implements Filterable{
    private static String TAG = cUserAdapter.class.getSimpleName();

    private Context context;
    private Bitmap bitmap;
    private ArrayList<cUserModel> listUsers;
    private ArrayList<cUserModel> filteredUsers;

    private cStatusAdapter statusAdapter;
    private ArrayList<cStatusModel> statusDomains;

    //private cSessionManager session;

    //private cAddressHandler addressHandler;
    //private cUserHandler userHandler;
   // private cOrganizationHandler organizationHandler;

    private iMEEntityInterface userInterface;

    private String createdDate;
    private String modifiedDate;
    private String syncedDate;

    Gson gson = new Gson();

    public cUserAdapter(Context context, ArrayList<cUserModel> userDomains,
                        ArrayList<cStatusModel> statusDomains, iMEEntityInterface userInterface) {
        this.context        = context;
        //this.session        = session;
        this.listUsers      = userDomains;
        this.filteredUsers  = listUsers;

        //this.addressHandler = new cAddressHandler(context);
        //this.userHandler    = new cUserHandler(context);
        //this.organizationHandler = new cOrganizationHandler(context);

        // used to mask statuses
        this.statusDomains = statusDomains;

        this.userInterface = userInterface;
    }

    @Override
    public cUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_cardview, parent, false);

        return new cUserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final cUserViewHolder holder, final int position) {
        // set icons for menu items
        /*
        holder.textViewUserNameIcon.setTypeface(null, Typeface.NORMAL);
        holder.textViewUserNameIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        holder.textViewUserNameIcon.setText(context.getResources().getString(R.string.fa_name));
        holder.textViewUserName.setText(listUsers.get(position).getName()+" "+listUsers.get(position).getSurname());

        */
        cAddressModel addressDomain = null;//addressHandler.getAddressByID(filteredUsers.get(position).getAddressID());
        final cUserModel userDomain = filteredUsers.get(position);

        //bitmap = cBitmap.decodeByteArray(filteredUsers.get(position).getPhoto());
        bitmap = null;//userHandler.getUserPhoto(filteredUsers.get(position).getUserID());
        if (bitmap != null) {
            holder.textViewUserNameIcon.setImageBitmap(bitmap);
        }else{
            holder.textViewUserNameIcon.setImageResource(R.drawable.ic_launcher);
        }

        holder.textViewUserName.setText(userDomain.getName()+" "+userDomain.getSurname());

        holder.textViewUserAddressIcon.setTypeface(null, Typeface.NORMAL);
        holder.textViewUserAddressIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        holder.textViewUserAddressIcon.setText(context.getResources().getString(R.string.fa_address));
        holder.textViewUserAddressStreet.setText(addressDomain.getStreet());
        holder.textViewUserAddressCity.setText(addressDomain.getCity());
        holder.textViewUserAddressProvince.setText(addressDomain.getProvince());
        holder.textViewUserAddressPotalCode.setText(addressDomain.getPostalCode());
        holder.textViewUserAddressCountry.setText(addressDomain.getCountry());

        holder.textViewUserEmailIcon.setTypeface(null, Typeface.NORMAL);
        holder.textViewUserEmailIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        holder.textViewUserEmailIcon.setText(context.getResources().getString(R.string.fa_email));
        holder.textViewUserEmail.setText(userDomain.getEmail());

        holder.textViewUserWebsiteIcon.setTypeface(null, Typeface.NORMAL);
        holder.textViewUserWebsiteIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        holder.textViewUserWebsiteIcon.setText(context.getResources().getString(R.string.fa_website));
        holder.textViewUserWebsite.setText(userDomain.getWebsite());

        holder.textViewUserPhoneIcon.setTypeface(null, Typeface.NORMAL);
        holder.textViewUserPhoneIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        holder.textViewUserPhoneIcon.setText(context.getResources().getString(R.string.fa_phone));
        holder.textViewUserPhone.setText(userDomain.getPhone());

        /** quick actions on roles
        holder.textViewQuickActionIcon.setTypeface(null, Typeface.NORMAL);
        holder.textViewQuickActionIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        holder.textViewQuickActionIcon.setText(context.getResources().getString(R.string.fa_ellipsis_h));
        holder.textViewQuickActionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallback.onQuickActionCallback(view, position);
            }
        });
**/
        /** collapse and expansion of the details of the role **/
        holder.textViewUserDetailIcon.setTypeface(null, Typeface.NORMAL);
        holder.textViewUserDetailIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        holder.textViewUserDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
        holder.textViewUserDetailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(holder.expandableLayout.isExpanded())){
                    holder.textViewUserDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
                }else{
                    holder.textViewUserDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                }

                holder.expandableLayout.toggle();
            }
        });


        /** icon for saving changed entity common attributes **/
        holder.textViewEditUserIcon.setTypeface(null, Typeface.NORMAL);
        holder.textViewEditUserIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        holder.textViewEditUserIcon.setText(context.getResources().getString(R.string.fa_update));
        holder.textViewEditUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // setting icon to dialog
                TextDrawable faIcon = new TextDrawable(context);
                faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                faIcon.setText(context.getResources().getText(R.string.fa_update));
                faIcon.setTextColor(Color.BLUE);
                alertDialogBuilder.setIcon(faIcon);

                // set title
                alertDialogBuilder.setTitle("Edit User.");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to EDIT user: " +
                                holder.textViewUserName.getText() + " ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get the user details at a card position of recycleview
                                //cUserDomain userDomain = getItem(position);

                                userInterface.onUpdateEntity(position);

                                dialog.dismiss();
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
        holder.textViewDeleteUserIcon.setTypeface(null, Typeface.NORMAL);
        holder.textViewDeleteUserIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        holder.textViewDeleteUserIcon.setText(context.getResources().getString(R.string.fa_delete));
        holder.textViewDeleteUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // setting icon to dialog
                TextDrawable faIcon = new TextDrawable(context);
                faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                faIcon.setText(context.getResources().getText(R.string.fa_delete));
                faIcon.setTextColor(Color.BLUE);
                alertDialogBuilder.setIcon(faIcon);

                // set title
                alertDialogBuilder.setTitle("Remove User.");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to REMOVE user: " +
                                holder.textViewUserName.getText() + " ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get the user details at a card position of recycleview
                                //cUserModel userDomain = getItem(position);
                                // delete the user from database
                                boolean result = true;//userHandler.deleteUser(userDomain.getUserID());
                                if (result) {
                                    // remove from the user from the adapter list
                                    removeItem(position);
                                    // animate the deletion from the recycleview
                                    notifyDataSetChanged();
                                    // response message
                                    userInterface.onResponseMessage("User Entity",
                                            "User Entity Successfully Deleted.");

                                } else {
                                    Toast.makeText(context,
                                            "Unable to delete the user",
                                            Toast.LENGTH_SHORT).show();

                                }

                                dialog.dismiss();
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
        holder.textViewSyncUserIcon.setTypeface(null, Typeface.NORMAL);
        holder.textViewSyncUserIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        holder.textViewSyncUserIcon.setText(context.getResources().getString(R.string.fa_sync));
        holder.textViewSyncUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // setting icon to dialog
                TextDrawable faIcon = new TextDrawable(context);
                faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                faIcon.setText(context.getResources().getText(R.string.fa_sync));
                faIcon.setTextColor(Color.BLUE);
                alertDialogBuilder.setIcon(faIcon);

                // set title
                alertDialogBuilder.setTitle("Sync User.");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to SYNCHRONISE user: " +
                                holder.textViewUserName.getText() + " ?")
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

        /* common attributes under a hidden layer */

        // get all users from database
        final ArrayList<cUserModel> users = null;/*userHandler.getUserList(
                session.loadUserID(),        /* loggedIn user id
                session.loadOrgID(),         /* loggedIn own org.
                session.loadPrimaryRole(),   /* primary group bit
                session.loadSecondaryRoles() /* secondary group bits
        );*/

        // get all organizations from database
        final ArrayList<cOrganizationModel> orgs = null;/*
                organizationHandler.getOrganizationList(
                        session.loadUserID(),        /* loggedIn user id
                        session.loadOrgID(),         /* loggedIn own org.
                        session.loadPrimaryRole(),   /* primary group bit
                        session.loadSecondaryRoles() /* secondary group bits
                );*/

        // create a pair list of user ids and names
        final List<cKeyPairBoolData> keyPairBoolUsers = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            idNameBool.setId(users.get(i).getUserID());
            idNameBool.setName(users.get(i).getName());
            if (true/*session.loadUserID() == users.get(i).getUserID()*/) {
                idNameBool.setSelected(true);
            } else {
                idNameBool.setSelected(false);
            }
            keyPairBoolUsers.add(idNameBool);
        }
        // -1 is no by default selection, 0 to length will select corresponding values
        // called when click owner single spinner search
        holder.appCompatTextViewOwner.setText("Owner :");
        holder.singleSpinnerSearchOwner.setItems(keyPairBoolUsers, -1,
                new cSpinnerListener() {
            @Override
            public void onItemsSelected(List<cKeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        userDomain.setOwnerID((int) items.get(i).getId());
                        break;
                    }
                }
                Log.d(TAG, "OWNER : " + userDomain.getOwnerID());
            }
        });

        // create a pair list of organization ids and names
        final List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
        for (int i = 0; i < orgs.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            //-idNameBool.setId(orgs.get(i).getOrganizationID());
            idNameBool.setName(orgs.get(i).getName());
            if (userDomain.getOrganizationID() == 0/*orgs.get(i).getOrganizationID()*/) {
                idNameBool.setSelected(true);
            } else {
                idNameBool.setSelected(false);
            }
            keyPairBoolOrgs.add(idNameBool);
        }
        // -1 is no by default selection, 0 to length will select corresponding values
        // called when click organization single spinner search
        holder.singleSpinnerSearchOrg.setItems(keyPairBoolOrgs, -1, new cSpinnerListener() {
            @Override
            public void onItemsSelected(List<cKeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        userDomain.setOrganizationID((int) items.get(i).getId());
                        break;
                    }
                }
                Log.d(TAG, "ORGANIZATION ID : " + userDomain.getOrganizationID());
            }
        });

        // create a pair list of other organization ids and names
        final List<cKeyPairBoolData> keyPairBoolOtherOrgs = new ArrayList<>();
        for (int i = 0; i < orgs.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            //-idNameBool.setId(orgs.get(i).getOrganizationID());
            idNameBool.setName(orgs.get(i).getName());
            if (true){//userDomain.getGroupBITS() & orgs.get(i).getOrganizationID()) == orgs.get(i).getOrganizationID()) {
                idNameBool.setSelected(true);
            } else {
                idNameBool.setSelected(false);
            }
            keyPairBoolOtherOrgs.add(idNameBool);
        }
        // -1 is no by default selection, 0 to length will select corresponding values
        // called when click other organization multi spinner search
        holder.multiSpinnerSearchOtherOrg.setItems(keyPairBoolOtherOrgs, -1, new cSpinnerListener() {
            @Override
            public void onItemsSelected(List<cKeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    int orgID = (int) items.get(i).getId();
                    if (items.get(i).isSelected()) {
                        if ((userDomain.getGroupBITS() & orgID) != orgID) {
                            // add other organizations
                            userDomain.setGroupBITS(userDomain.getGroupBITS() | orgID);
                        }
                    }
                    if (!items.get(i).isSelected()) {
                        if ((userDomain.getGroupBITS() & orgID) == orgID) {
                            // remove other organizations
                            userDomain.setGroupBITS(userDomain.getGroupBITS() & ~orgID);
                        }
                    }
                }
                Log.d(TAG, "OTHER ORGANIZATION : " + userDomain.getGroupBITS());
            }
        });

        // create a pair list of permission ids and names
        final cKeyPairBoolData[] keyPairBoolPerms = new cKeyPairBoolData[NUM_PERMS];
        //if (permissionDomains.size() > 0) {
            int opBITS = userDomain.getPermsBITS();
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
        holder.tableSpinner.setItems(keyPairBoolPerms, -1, new cTableSpinnerListener() {
            @Override
            public void onFixedItemsSelected(cKeyPairBoolData[] items) {
                for (int i = 0; i < items.length; i++) {
                    int permID = (int) items[i].getId();
                    if (items[i].isSelected()) {
                        if ((userDomain.getPermsBITS() & permID) != permID) {
                            // add operation
                            userDomain.setPermsBITS(userDomain.getPermsBITS() | permID);
                        }
                    }
                    if (!items[i].isSelected()) {
                        if ((userDomain.getPermsBITS() & permID) == permID) {
                            // remove operation
                            userDomain.setPermsBITS(userDomain.getPermsBITS() & ~permID);
                        }
                    }
                }
                Log.d(TAG, "PERMS : " + userDomain.getPermsBITS());
            }
        });

        // create a pair list of statuses ids and names
        final List<cKeyPairBoolData> keyPairBoolStatuses = new ArrayList<>();
        for (int i = 0; i < statusDomains.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            idNameBool.setId(statusDomains.get(i).getStatusID());
            idNameBool.setName(statusDomains.get(i).getName());
            if ((userDomain.getStatusBITS() & statusDomains.get(i).getStatusID()) ==
                    statusDomains.get(i).getStatusID()) {
                idNameBool.setSelected(true);
            } else {
                idNameBool.setSelected(false);
            }
            keyPairBoolStatuses.add(idNameBool);
        }
        // -1 is no by default selection, 0 to length will select corresponding values
        // called when click statuses multi spinner search
        holder.multiSpinnerSearchStatuses.setItems(keyPairBoolStatuses, -1, new cSpinnerListener() {
            @Override
            public void onItemsSelected(List<cKeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    int statusID = (int) items.get(i).getId();
                    if (items.get(i).isSelected()) {
                        if ((userDomain.getStatusBITS() & statusID) != statusID) {
                            // add status
                            userDomain.setStatusBITS(userDomain.getStatusBITS() | statusID);
                        }
                    }
                    if (!items.get(i).isSelected()) {
                        if ((userDomain.getStatusBITS() & statusID) == statusID) {
                            // remove status
                            userDomain.setStatusBITS(userDomain.getStatusBITS() & ~statusID);
                        }
                    }
                }
                Log.d(TAG, "STATUSES : " + userDomain.getStatusBITS());
            }
        });

        createdDate = FORMAT_DATE.format(userDomain.getCreatedDate());
        modifiedDate = FORMAT_DATE.format(userDomain.getModifiedDate());
        syncedDate = FORMAT_DATE.format(userDomain.getSyncedDate());

        holder.textViewCreatedDate.setText(createdDate);
        holder.textViewModifiedDate.setText(modifiedDate);
        holder.textViewSyncedDate.setText(syncedDate);

        /** change perms **/
        holder.textViewChangeUserIcon.setTypeface(null, Typeface.NORMAL);
        holder.textViewChangeUserIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        holder.textViewChangeUserIcon.setText(context.getResources().getString(R.string.fa_perms));
        holder.textViewChangeUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // setting icon to dialog
                TextDrawable faIcon = new TextDrawable(context);
                faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                faIcon.setText(context.getResources().getText(R.string.fa_perms));
                faIcon.setTextColor(Color.BLUE);
                alertDialogBuilder.setIcon(faIcon);

                // set title
                alertDialogBuilder.setTitle("Change Permissions.");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to CHANGE permissions for " +
                                holder.textViewUserName.getText() + "'s Record ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // update user record permissions in the database
                                userDomain.setModifiedDate(new Date());
                                if(true/*userHandler.updateUser(userDomain)*/){
                                    userInterface.onResponseMessage("Record Permissions",
                                            "Record Permissions Successfully Changed.");
                                }else{
                                    userInterface.onResponseMessage("Record Permissions",
                                            "Error Changing Record Permissions.");
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


    }

    public cPermissionModel getPermissionDomain(ArrayList<cPermissionModel> permissionDomains,
                                                int privilegeID, int entityID, int typeID,
                                                int operationID, int statusID) {
        cPermissionModel permissionDomain = null;
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

//                permissionDomain = new cPermissionModel(permissionDomains.get(i));

                return permissionDomain;
            }
        }

        return null;
    }

    // call this method when required to show popup
    public void onShowCommonAttributes(View view, final cStatusModel statusDomain) {
        // get all users from database
        final ArrayList<cUserModel> users = null;/*userHandler.getUserList(
                session.loadUserID(),        /* loggedIn user id
                session.loadOrgID(),         /* loggedIn own org.
                session.loadPrimaryRole(),   /* primary group bit
                session.loadSecondaryRoles() /* secondary group bits
        );*/

        // get all organizations from database
        final ArrayList<cOrganizationModel> orgs = null; /*
                organizationHandler.getOrganizationList(
                        session.loadUserID(),        /* loggedIn user id
                        session.loadOrgID(),         /* loggedIn own org.
                        session.loadPrimaryRole(),   /* primary group bit
                        session.loadSecondaryRoles() /* secondary group bits
                );*/

        /* get a deep copy of permission domain to modify
        final cPermissionDomain mPermissionDomain = getPermissionDomain(permissionDomains,
                privilegeID, entityDomain.getEntityID(), entityDomain.getTypeID(),
                operationDomain.getOperationID(), statusDomain.getStatusID());*/

        /** make a deepcopy of the original permission domain
        final cPermissionDomain originalDomain = new cPermissionDomain(mPermissionDomain);**/


    }

    /*
    @Override
    public int getItemCount() {
        Log.v(cUserAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }
    */

    @Override
    public int getItemCount() {
        return filteredUsers.size();
    }

    public cUserModel getItem(int position) {
        return filteredUsers.get(position);
    }

    public void removeItem(int position) {
        filteredUsers.remove(position);
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
                    filteredUsers = listUsers;
                } else {

                    ArrayList<cUserModel> filteredList = new ArrayList<>();

                    for (cUserModel userDomain : listUsers) {

                        if (userDomain.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                userDomain.getSurname().toLowerCase().contains(charString)) {
                            filteredList.add(userDomain);
                        }
                    }

                    filteredUsers = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count  = filteredUsers.size();
                filterResults.values = filteredUsers;

                Log.d(TAG,gson.toJson(filterResults));


                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUsers = (ArrayList<cUserModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    /**
     * cViewHolder class
     */
    public class cUserViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView textViewUserNameIcon;
        private AppCompatTextView textViewUserName;

        private AppCompatTextView textViewUserAddressIcon;
        private AppCompatTextView textViewUserAddressStreet;
        private AppCompatTextView textViewUserAddressCity;
        private AppCompatTextView textViewUserAddressProvince;
        private AppCompatTextView textViewUserAddressPotalCode;
        private AppCompatTextView textViewUserAddressCountry;

        private AppCompatTextView textViewUserEmailIcon;
        private AppCompatTextView textViewUserEmail;

        private AppCompatTextView textViewUserWebsiteIcon;
        private AppCompatTextView textViewUserWebsite;

        private AppCompatTextView textViewUserPhoneIcon;
        private AppCompatTextView textViewUserPhone;

        //private TextView textViewQuickActionIcon;

        private cExpandableLayout expandableLayout;
        private TextView textViewUserDetailIcon;

        private TextView textViewSyncUserIcon;
        private TextView textViewDeleteUserIcon;
        private TextView textViewEditUserIcon;

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

        public cUserViewHolder(View view) {
            super(view);
            /*
            textViewUserNameIcon    = (AppCompatTextView) view.findViewById(R.id.textViewUserNameIcon);
            */
            this.textViewUserNameIcon    = (CircleImageView) view.findViewById(R.id.textViewUserNameIcon);
            this.textViewUserName        = (AppCompatTextView) view.findViewById(R.id.textViewUserName);

            this.textViewUserAddressIcon = (AppCompatTextView) view.findViewById(R.id.textViewUserAddressIcon);
            this.textViewUserAddressStreet = (AppCompatTextView) view.findViewById(R.id.textViewUserAddressStreet);
            this.textViewUserAddressCity = (AppCompatTextView) view.findViewById(R.id.textViewUserAddressCity);
            this.textViewUserAddressProvince = (AppCompatTextView) view.findViewById(R.id.textViewUserAddressProvince);
            this.textViewUserAddressPotalCode = (AppCompatTextView) view.findViewById(R.id.textViewUserAddressPotalCode);
            this.textViewUserAddressCountry = (AppCompatTextView) view.findViewById(R.id.textViewUserAddressCountry);

            this.textViewUserEmailIcon   = (AppCompatTextView) view.findViewById(R.id.textViewUserEmailIcon);
            this.textViewUserEmail       = (AppCompatTextView) view.findViewById(R.id.textViewUserEmail);

            this.textViewUserWebsiteIcon = (AppCompatTextView) view.findViewById(R.id.textViewUserWebsiteIcon);
            this.textViewUserWebsite     = (AppCompatTextView) view.findViewById(R.id.textViewUserWebsite);

            this.textViewUserPhoneIcon   = (AppCompatTextView) view.findViewById(R.id.textViewUserPhoneIcon);
            this.textViewUserPhone       = (AppCompatTextView) view.findViewById(R.id.textViewUserPhone);

            //this.textViewQuickActionIcon = (TextView) view.findViewById(R.id.textViewQuickActionIcon);

            this.expandableLayout = (cExpandableLayout) view.findViewById(R.id.expandableLayout);
            this.textViewUserDetailIcon = (TextView) view.findViewById(R.id.textViewUserDetailIcon);

            /* common attributes */
            this.textViewSyncUserIcon = (TextView) view.findViewById(R.id.textViewSyncUserIcon);
            this.textViewDeleteUserIcon = (TextView) view.findViewById(R.id.textViewDeleteUserIcon);
            this.textViewEditUserIcon = (TextView) view.findViewById(R.id.textViewEditUserIcon);

            this.singleSpinnerSearchOwner =
                    (cSingleSpinnerSearch_old) view.findViewById(R.id.appCompatSpinnerOwner);
            this.appCompatTextViewOwner = (AppCompatTextView)view.findViewById(R.id.appCompatTextViewOwner);
            this.singleSpinnerSearchOrg =
                    (cSingleSpinnerSearch_old) view.findViewById(R.id.appCompatSpinnerOrg);
            this.multiSpinnerSearchOtherOrg =
                    (cMultiSpinnerSearch) view.findViewById(R.id.appCompatSpinnerOtherOrg);
            this.tableSpinner =
                    (cTableSpinner) view.findViewById(R.id.appCompatSpinnerPerms);
            this.multiSpinnerSearchStatuses =
                    (cMultiSpinnerSearch) view.findViewById(R.id.appCompatSpinnerStatuses);

            this.textViewCreatedDate = (TextView) view.findViewById(R.id.textViewCreatedDate);
            this.textViewModifiedDate = (TextView) view.findViewById(R.id.textViewModifiedDate);
            this.textViewSyncedDate = (TextView) view.findViewById(R.id.textViewSyncedDate);

            this.textViewChangeUserIcon = (AppCompatTextView) view.findViewById(R.id.textViewChangeUserIcon);

        }
    }

    static public class cQAAdapter extends BaseAdapter {

        final int[] ICONS = new int[]{
                R.string.fa_delete,
                R.string.fa_update
        };

        LayoutInflater mLayoutInflater;
        List<cCustomActionItemText> mItems;
        cCustomActionItemText item;

        Context context;

        public cQAAdapter(Context context) {
            this.context = context;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            mItems = new ArrayList<cCustomActionItemText>();

            item = new cCustomActionItemText(context, "Delete", ICONS[0]);
            mItems.add(item);

            item = new cCustomActionItemText(context, "Edit", ICONS[1]);
            mItems.add(item);

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