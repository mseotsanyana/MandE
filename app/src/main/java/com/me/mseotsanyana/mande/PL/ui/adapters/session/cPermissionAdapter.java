package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Layout;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.BLL.domain.session.cEntityDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.entity.Impl.cEntityHandler;
import com.me.mseotsanyana.mande.BLL.domain.session.cOperationDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.operation.Impl.cOperationHandler;
import com.me.mseotsanyana.mande.BLL.domain.session.cOrganizationDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cPermissionDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.permission.Impl.cPermissionHandler;
import com.me.mseotsanyana.mande.BLL.domain.session.cStatusDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.status.Impl.cStatusInteractorImpl;
import com.me.mseotsanyana.mande.BLL.domain.session.cUserDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl.cUserHandler;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cOperationsFragment;
import com.me.mseotsanyana.mande.UTIL.INTERFACE.iPermissionInterface;
import com.me.mseotsanyana.mande.UTIL.INTERFACE.iEntityTVHInterface;
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
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.me.mseotsanyana.mande.UTIL.cConstant.COLLAPSE;
import static com.me.mseotsanyana.mande.UTIL.cConstant.EXPAND;
import static com.me.mseotsanyana.mande.UTIL.cConstant.FORMAT_DATE;
import static com.me.mseotsanyana.mande.UTIL.cConstant.NUM_PERMS;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cPermissionAdapter extends cTreeAdapter {
    private static final String TAG = cPermissionAdapter.class.getSimpleName();
    private static final int NUM_OPS = 3;
    private static final int NUM_STS = 5;

    public static final int PRIVILEGE = 0;
    public static final int ENTITY = 1;

    private HashSet<Integer> expandedPositionSet;

    // sparse boolean array for checking the state of the check box
    private SparseBooleanArray checkBoxStateArray = new SparseBooleanArray();

    private int privilegeDomainID;
    private cEntityDomain entityDomain;
    private cOperationDomain[] createOperations, readOperations,
            updateOperations, deleteOperations, syncOperations;
    private cStatusDomain[][] createStatuses, readStatuses,
            updateStatuses, deleteStatuses, syncStatuses;

    private ArrayList<cPermissionDomain> permissionDomains;

    private ArrayList<cOperationDomain> operationDomains;
    private ArrayList<cStatusDomain> statusDomains;

    private Context context;
    private FragmentManager fragmentManager;

    //private cSessionManager session;

    private cUserHandler userHandler;
    //private cOrganizationHandler organizationHandler;
    private cPermissionHandler privilegeHandler;
    private cEntityHandler entityHandler;
    private cOperationHandler operationHandler;
    private cStatusInteractorImpl statusHandler;
    private cPermissionHandler permissionHandler;

    //private cStatusAdapter statusAdapter;

    private int operationMask;
    private int statusMask;

    // contains all selected organisations
    private ArrayList<cEntityDomain> selectedEntities;


    //private cQuickAction quickAction;
    //private List<cTreeModel> Roles;
    private List<cTreeModel> treeModels;
    private int expLevel;

    private String createdDate;
    private String modifiedDate;
    private String syncedDate;

    private iPermissionInterface permissionInterface;

    private List<cKeyPairBoolData> keyPairBoolUsers;

    final Gson gson = new Gson();

    public cPermissionAdapter(Context context,
                              List<cTreeModel> treeModels, int expLevel,
                              ArrayList<cOperationDomain> operationDomains,
                              ArrayList<cStatusDomain> statusDomains,
                              FragmentManager fragmentManager,
                              iPermissionInterface permissionInterface) {

        super(context, treeModels, expLevel);
        this.treeModels = treeModels;
        this.expLevel = expLevel;

        this.context = context;
        //this.session = session;

        this.fragmentManager = fragmentManager;

        this.expandedPositionSet = new HashSet<>();
        this.selectedEntities = new ArrayList<cEntityDomain>();

        // used to mask statuses
        this.permissionDomains = new ArrayList<>();
        this.operationDomains = operationDomains;
        this.statusDomains = statusDomains;

        this.createOperations = new cOperationDomain[NUM_OPS];
        this.readOperations = new cOperationDomain[NUM_OPS];
        this.updateOperations = new cOperationDomain[NUM_OPS];
        this.deleteOperations = new cOperationDomain[NUM_OPS];
        this.syncOperations = new cOperationDomain[NUM_OPS];

        this.createStatuses = new cStatusDomain[NUM_OPS][NUM_STS];
        this.readStatuses = new cStatusDomain[NUM_OPS][NUM_STS];
        this.updateStatuses = new cStatusDomain[NUM_OPS][NUM_STS];
        this.deleteStatuses = new cStatusDomain[NUM_OPS][NUM_STS];
        this.syncStatuses = new cStatusDomain[NUM_OPS][NUM_STS];

        this.userHandler = new cUserHandler(context);
        //this.organizationHandler = new cOrganizationHandler(context);
        this.privilegeHandler = new cPermissionHandler(context);
        this.entityHandler = new cEntityHandler(context);
        this.operationHandler = new cOperationHandler(context);
        this.statusHandler = null;//new cStatusInteractorImpl(context);

        this.permissionHandler = new cPermissionHandler(context);


        this.keyPairBoolUsers = new ArrayList<>();


        this.permissionInterface = permissionInterface;
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case PRIVILEGE:
                view = inflater.inflate(R.layout.privilege_cardview, parent, false);
                viewHolder = new cPrivilegeTreeViewHolder(view);
                break;
            case ENTITY:
                view = inflater.inflate(R.layout.privilege_entity_cardview, parent, false);
                viewHolder = new cEntityTreeViewHolder(view);
                break;
            default:
                viewHolder = null;
                break;
        }

        return viewHolder;
    }

    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final cNode node = visibleNodes.get(position);

        final cTreeModel treeModel = (cTreeModel) node.getObj();

        if (treeModel != null) {
            switch (treeModel.getType()) {
                case PRIVILEGE:
                    final cPermissionDomain privilegeDomain = (cPermissionDomain) treeModel.getModelObject();
                    final cPrivilegeTreeViewHolder PVH = ((cPrivilegeTreeViewHolder) viewHolder);

                    PVH.updateItem(position);
                    PVH.setPaddingLeft(40 * node.getLevel());

                    // the name and description of the privilege
                    PVH.textViewName.setText(privilegeDomain.getName());
                    PVH.textViewDescription.setText(privilegeDomain.getDescription());

                    // the collapse and expansion of the roles
                    if (node.isLeaf()) {
                        PVH.textViewExpandPrivilegeIcon.setVisibility(View.INVISIBLE);
                    } else {
                        PVH.textViewExpandPrivilegeIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            PVH.textViewExpandPrivilegeIcon.setTypeface(null, Typeface.NORMAL);
                            PVH.textViewExpandPrivilegeIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PVH.textViewExpandPrivilegeIcon.setText(context.getResources().getString(R.string.fa_minus));

                            //PVH.textViewExapandPrivilegeIcon.setSelected(true);
                        } else {
                            PVH.textViewExpandPrivilegeIcon.setTypeface(null, Typeface.NORMAL);
                            PVH.textViewExpandPrivilegeIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PVH.textViewExpandPrivilegeIcon.setText(context.getResources().getString(R.string.fa_plus));

                            //PVH.textViewPrivilegeDetailIcon.setSelected(false);
                        }
                    }
                    PVH.textViewExpandPrivilegeIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    // collapse and expansion of the details of the role
                    PVH.textViewPrivilegeDetailIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewPrivilegeDetailIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewPrivilegeDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                    PVH.textViewPrivilegeDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!(PVH.expandableLayout.isExpanded())) {
                                PVH.textViewPrivilegeDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
                            } else {
                                PVH.textViewPrivilegeDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                            }

                            PVH.expandableLayout.toggle();
                        }
                    });

                    // number of users under a role
                    PVH.textViewCountRole.setText("ENTITIES: " + node.numberOfChildren());

                    /** edit privilege **/
                    PVH.textViewEditPrivilegeIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewEditPrivilegeIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewEditPrivilegeIcon.setText(context.getResources().getString(R.string.fa_update));
                    PVH.textViewEditPrivilegeIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewEditPrivilegeIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            permissionInterface.onUpdatePrivilege(treeModel);

                        }
                    });

                    /** delete privilege **/
                    PVH.textViewDeletePrivilegeIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewDeletePrivilegeIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewDeletePrivilegeIcon.setText(context.getResources().getString(R.string.fa_delete));
                    PVH.textViewDeletePrivilegeIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewDeletePrivilegeIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 8);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_delete));
                            faIcon.setTextColor(context.getColor(R.color.colorAccent));
                            alertDialogBuilder.setIcon(faIcon);

                            // set title
                            alertDialogBuilder.setTitle(context.getResources().getString(R.string.privilege_del_title));
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage(context.getResources().getString(R.string.privilege_del_question) +
                                            ": " + PVH.textViewName.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // delete the privilege in the database
                                            permissionInterface.onDeletePrivilege(treeModel, position);
                                            dialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // just close the dialog box
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                    });

                    /** synchronise privilege **/
                    PVH.textViewSyncPrivilegeIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewSyncPrivilegeIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewSyncPrivilegeIcon.setText(context.getResources().getString(R.string.fa_sync));
                    PVH.textViewSyncPrivilegeIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewSyncPrivilegeIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 8);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_sync));
                            faIcon.setTextColor(context.getColor(R.color.colorAccent));
                            alertDialogBuilder.setIcon(faIcon);

                            // set title
                            alertDialogBuilder.setTitle(
                                    context.getResources().getString(R.string.privilege_sync_title));
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage(
                                            context.getResources().getString(R.string.privilege_sync_question) +
                                                    ": " + PVH.textViewName.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // FIXME:sync the permissions in the database
                                            //EVH.syncPermissions();
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


                    //final Gson gson = new Gson();

                    // quick actions on roles
                    PVH.textViewQuickActionIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewQuickActionIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewQuickActionIcon.setText(context.getResources().getString(R.string.fa_ellipsis_h));
                    PVH.textViewQuickActionIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewQuickActionIcon.setOnClickListener(new View.OnClickListener() {
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
                                            permissionInterface.onAddPermissionEntities(node);
                                            break;
                                        case 1:
                                            permissionInterface.onRemovePermissionEntities(node);
                                            break;
                                        default:
                                            permissionInterface.onResponseMessage(R.string.privilege_filter_error,
                                                    R.string.privilege_filter_error_msg);
                                            break;
                                    }

                                    dialog.dismiss();
                                }
                            });

                            // finally show the view
                            quickAction.show();
                        }
                    });

                    /** common attributes **/

                    /** populate users from database **/
                    onPopulateCommonAttributes(PVH, treeModel);

                    break;

                case ENTITY:
                    //final cPermissionTreeDomain permTreeDomain = (cPermissionTreeDomain)
                    //        treeModel.getModelObject();

                    operationMask = 0;
                    statusMask = 0;

                    for (int i = 0; i < NUM_OPS; i++) {
                        /** initialise operations
                        createOperations[i] = new cOperationDomain(
                                getOperationByID(operationDomains,ession.permissions[i]));
                        readOperations[i] = new cOperationDomain(
                                getOperationByID(operationDomains, session.permissions[i + 3]));
                        updateOperations[i] = new cOperationDomain(
                                getOperationByID(operationDomains, session.permissions[i + 6]));
                        deleteOperations[i] = new cOperationDomain(
                                getOperationByID(operationDomains, session.permissions[i + 9]));
                        syncOperations[i] = new cOperationDomain(
                                getOperationByID(operationDomains, session.permissions[i + 12]));
**/
                        /** initialise statuses **/
                        for (int j = 0; j < NUM_STS; j++) {
                            createStatuses[i][j] = new cStatusDomain(getStatusByID(statusDomains,
                                    statusDomains.get(j).getStatusID()));
                            readStatuses[i][j] = new cStatusDomain(getStatusByID(statusDomains,
                                    statusDomains.get(j).getStatusID()));
                            updateStatuses[i][j] = new cStatusDomain(getStatusByID(statusDomains,
                                    statusDomains.get(j).getStatusID()));
                            deleteStatuses[i][j] = new cStatusDomain(getStatusByID(statusDomains,
                                    statusDomains.get(j).getStatusID()));
                            syncStatuses[i][j] = new cStatusDomain(getStatusByID(statusDomains,
                                    statusDomains.get(j).getStatusID()));
                        }
                    }

                    /** id of the privilege **/
                    //privilegeDomainID = permTreeDomain.getPrivilegeID();

                    /** entity domain under the privileges **/
                    //entityDomain = permTreeDomain.getEntityDomain();

                    /** populate operation, status and permission domains from database **/
                    Iterable<? extends Map.Entry<cOperationDomain, HashMap<cStatusDomain, cPermissionDomain>>> l=null;
                    for (Map.Entry<cOperationDomain, HashMap<cStatusDomain, cPermissionDomain>> opsEntry : l)
                            //permTreeDomain.getPermDomainDetails().entrySet())
                    {

                        int opID = opsEntry.getKey().getOperationID();

                        for (int i = 0; i < NUM_OPS; i++) {
                            /** own create operations in the database
                            if (opID == session.permissions[i]) {
                                createOperations[i].setState(true);
                            }
                            /** own read operations in the database
                            if (opID == session.permissions[i + 3]) {
                                readOperations[i].setState(true);
                            }
                            /** own update operations in the database
                            if (opID == session.permissions[i + 6]) {
                                updateOperations[i].setState(true);
                            }
                            /** own delete operations in the database
                            if (opID == session.permissions[i + 9]) {
                                deleteOperations[i].setState(true);
                            }
                            /** own sync operations in the database
                            if (opID == session.permissions[i + 12]) {
                                syncOperations[i].setState(true);
                            }*/
                        }

                        /** masking the operations for the entity **/
                        operationMask |= opsEntry.getKey().getOperationID();

                        for (Map.Entry<cStatusDomain, cPermissionDomain> statusEntry :
                                opsEntry.getValue().entrySet()) {

                            int stsID = statusEntry.getKey().getStatusID();

                            /** create operations **/
                            for (int i = 0; i < NUM_OPS; i++) {
                                for (int j = 0; j < NUM_STS; j++) {
                                    /** create statuses in the database
                                    if ((opID == session.permissions[i]) && (stsID == session.statuses[j])) {
                                        //createStatuses[i][j].setState(true);
                                    }

                                    /** read statuses in the database
                                    if ((opID == session.permissions[i + 3]) && (stsID == session.statuses[j])) {
                                        //readStatuses[i][j].setState(true);
                                    }

                                    /** update statuses in the database
                                    if ((opID == session.permissions[i + 6]) && (stsID == session.statuses[j])) {
                                        //updateStatuses[i][j].setState(true);
                                    }

                                    /** delete statuses in the database
                                    if ((opID == session.permissions[i + 9]) && (stsID == session.statuses[j])) {
                                        //deleteStatuses[i][j].setState(true);
                                    }

                                     sync statuses in the database
                                    if ((opID == session.permissions[i + 12]) && (stsID == session.statuses[j])) {
                                        //syncStatuses[i][j].setState(true);
                                    }*/
                                }
                            }

                            /** masking status bits for the entity **/
                            statusMask |= statusEntry.getKey().getStatusID();

                            permissionDomains.add(statusEntry.getValue());
                        }
                    }

                    //Log.d(TAG, "==="+entityDomain.getName()+" ("+entityDomain.getEntityID()+")===");
                    //Log.d(TAG, gson.toJson(createOperations));
                    //Log.d(TAG, "---------------------------------");
                    //Log.d(TAG, gson.toJson(readOperations));
                    //Log.d(TAG, gson.toJson(updateOperations));
                    //Log.d(TAG, gson.toJson(deleteOperations));
                    //Log.d(TAG, gson.toJson(syncOperations));

                    //Log.d(TAG, gson.toJson(createStatuses));
                    //Log.d(TAG, gson.toJson(readStatuses));
                    //Log.d(TAG, gson.toJson(updateStatuses));
                    //Log.d(TAG, gson.toJson(deleteStatuses));
                    //Log.d(TAG, gson.toJson(syncStatuses));

                    final cEntityTreeViewHolder EVH = ((cEntityTreeViewHolder) viewHolder);

                    EVH.updateItem(position);
                    EVH.setPaddingLeft(40 * node.getLevel());

                    EVH.textViewName.setText(entityDomain.getName());
                    EVH.textViewDescription.setText(entityDomain.getDescription());

                    /** collapse and expansion of the details of the role
                    EVH.textViewEntityDetailIcon.setTypeface(null, Typeface.NORMAL);
                    EVH.textViewEntityDetailIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    EVH.textViewEntityDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));

                    EVH.textViewEntityDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (EVH.expandableLayout.isExpanded()) {
                                EVH.textViewEntityDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                            } else {
                                EVH.textViewEntityDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
                            }
                            EVH.expandableLayout.toggle();

                        }
                    });
                     **/

                    /** icon for deleting an entity related permissions **/
                    EVH.textViewDeleteEntityIcon.setTypeface(null, Typeface.NORMAL);
                    EVH.textViewDeleteEntityIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    EVH.textViewDeleteEntityIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    EVH.textViewDeleteEntityIcon.setText(context.getResources().getString(R.string.fa_delete));
                    EVH.textViewDeleteEntityIcon.setOnClickListener(new View.OnClickListener() {
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
                            alertDialogBuilder.setTitle("Remove Entity.");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Do you want to REMOVE entity: " +
                                            EVH.textViewName.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // update the permissions in the database
                                            //EVH.createPermissions();
                                            //permissionHandler.deletePermission();
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
                    EVH.textViewSyncEntityIcon.setTypeface(null, Typeface.NORMAL);
                    EVH.textViewSyncEntityIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    EVH.textViewSyncEntityIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    EVH.textViewSyncEntityIcon.setText(context.getResources().getString(R.string.fa_sync));
                    EVH.textViewSyncEntityIcon.setOnClickListener(new View.OnClickListener() {
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
                            alertDialogBuilder.setTitle("Sync Entity.");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Do you want to SYNCHRONISE entity: " +
                                            EVH.textViewName.getText() + " ?")
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

                    EVH.appCompatButtonUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_exclamation_triangle));
                            faIcon.setTextColor(Color.BLUE);
                            alertDialogBuilder.setIcon(faIcon);


                            if (EVH.isPermissionCreated()) {
                                // set title
                                alertDialogBuilder.setTitle("Add Permissions");
                                // set dialog message
                                alertDialogBuilder
                                        .setMessage("Do you want to ADD permissions to entity: " +
                                                EVH.textViewName.getText() + " ?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // update the permissions in the database
                                                EVH.createPermissions();
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

                            if (EVH.isPermissionUpdated()) {
                                // set title
                                alertDialogBuilder.setTitle("Update Permissions");
                                // set dialog message
                                alertDialogBuilder
                                        .setMessage("Do you want to UPDATE permissions on entity: " +
                                                EVH.textViewName.getText() + " ?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // update the permissions in the database
                                                EVH.updatePermissions();
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

                            if (EVH.isPermissionDeleted()) {
                                // set title
                                alertDialogBuilder.setTitle("Delete Permissions");
                                // set dialog message
                                alertDialogBuilder
                                        .setMessage("Do you want to DELETE permissions on entity: " +
                                                EVH.textViewName.getText() + " ?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // update the permissions in the database
                                                EVH.deletePermissions();
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

                            if (!EVH.isPermissionCreated() && !EVH.isPermissionUpdated() && !EVH.isPermissionDeleted()) {
                                // set title
                                alertDialogBuilder.setTitle("Permissions");

                                // set dialog message
                                alertDialogBuilder
                                        .setMessage("No changes detected on entity: " +
                                                EVH.textViewName.getText() + " !")
                                        .setCancelable(false)
                                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // if this button is clicked, just close
                                                // the dialog box and do nothing
                                                dialog.cancel();
                                            }
                                        });

                                // create alert dialog
                                AlertDialog alertDialog = alertDialogBuilder.create();

                                // show it
                                alertDialog.show();
                            }
                        }
                    });

                    EVH.appCompatButtonReset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(context, "Button Reset", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // assign a unique id so that ViewPager is shown.
                    EVH.viewPager.setId(position);

                    //cOperationDomain[] tmpOperations;

                    // attach fragments with a viewpager and data
                    EVH.populateEntities(privilegeDomainID, entityDomain,
                            operationMask, statusMask, permissionDomains);

                    // attach the viewpager to the tablayout
                    EVH.tabLayout.setupWithViewPager(EVH.viewPager);

                    // attache icons to the tabs
                    EVH.setupTabIcons();

                    break;
            }
        }
    }

    private void onPopulateCommonAttributes(cPrivilegeTreeViewHolder PVH,
                                            cTreeModel treeModel) {
        new cCommonAttributesTask().execute(PVH, treeModel);
    }

    class cCommonAttributesTask extends AsyncTask<Object, Void, Object[]> {
        private cPrivilegeTreeViewHolder PVH;
        private cTreeModel treeModel;
        private cPermissionDomain privilegeDomain;


        Object[] objectArrayList = new Object[5];

        @Override
        protected Object[] doInBackground(Object... objects) {
            PVH = (cPrivilegeTreeViewHolder) objects[0];
            treeModel = (cTreeModel) objects[1];
            privilegeDomain = (cPermissionDomain) treeModel.getModelObject();

            // get all users from database
            final ArrayList<cUserDomain> users = null;/*userHandler.getUserList(
                    session.loadUserID(),        /* loggedIn user id
                    session.loadOrgID(),         /* loggedIn own org.
                    session.loadPrimaryRole(),   /* primary group bit
                    session.loadSecondaryRoles() /* secondary group bits
            );
*/
            final ArrayList<cOrganizationDomain> orgs =null;
                    /*organizationHandler.getOrganizationList(
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
                if (privilegeDomain.getOwnerID() == users.get(i).getUserID()) {
                    idNameBool.setSelected(true);
                } else {
                    idNameBool.setSelected(false);
                }
                keyPairBoolUsers.add(idNameBool);
            }

            // create a pair list of organization ids and names
            final List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
            for (int i = 0; i < orgs.size(); i++) {
                cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                idNameBool.setId(orgs.get(i).getOrganizationID());
                idNameBool.setName(orgs.get(i).getName());
                if (privilegeDomain.getOrgID() == orgs.get(i).getOrganizationID()) {
                    idNameBool.setSelected(true);
                } else {
                    idNameBool.setSelected(false);
                }
                keyPairBoolOrgs.add(idNameBool);
            }

            // create a pair list of other organization ids and names
            final List<cKeyPairBoolData> keyPairBoolOtherOrgs = new ArrayList<>();
            int gpBITS = privilegeDomain.getGroupBITS();
            for (int i = 0; i < orgs.size(); i++) {
                cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                idNameBool.setId(orgs.get(i).getOrganizationID());
                idNameBool.setName(orgs.get(i).getName());
                if ((gpBITS & orgs.get(i).getOrganizationID()) == orgs.get(i).getOrganizationID()) {
                    idNameBool.setSelected(true);
                } else {
                    idNameBool.setSelected(false);
                }
                keyPairBoolOtherOrgs.add(idNameBool);
            }

            //Log.d(TAG, gpBITS+" <=> "+gson.toJson(keyPairBoolOtherOrgs));

            // create a pair list of permission ids and names
            final cKeyPairBoolData[] keyPairBoolPerms = new cKeyPairBoolData[NUM_PERMS];
            int opBITS = privilegeDomain.getPermsBITS();
            /*for (int i = 0; i < session.permissions.length; i++) {
                cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                idNameBool.setId(session.permissions[i]);
                idNameBool.setName(session.perm_names[i]);
                idNameBool.setSelected((opBITS & session.permissions[i]) == session.permissions[i]);
                keyPairBoolPerms[i] = idNameBool;
            }*/

            // create a pair list of statuses ids and names
            final List<cKeyPairBoolData> keyPairBoolStatuses = new ArrayList<>();
            for (int i = 0; i < statusDomains.size(); i++) {
                cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                idNameBool.setId(statusDomains.get(i).getStatusID());
                idNameBool.setName(statusDomains.get(i).getName());
                if ((privilegeDomain.getStatusBITS() & statusDomains.get(i).getStatusID()) ==
                        statusDomains.get(i).getStatusID()) {
                    idNameBool.setSelected(true);
                } else {
                    idNameBool.setSelected(false);
                }
                keyPairBoolStatuses.add(idNameBool);
            }

            objectArrayList[0] = keyPairBoolUsers;
            objectArrayList[1] = keyPairBoolOrgs;
            objectArrayList[2] = keyPairBoolOtherOrgs;
            objectArrayList[3] = keyPairBoolPerms;
            objectArrayList[4] = keyPairBoolStatuses;

            return objectArrayList;
        }

        @Override
        protected void onPostExecute(Object[] keyPairBoolData) {
            super.onPostExecute(keyPairBoolData);

            PVH.appCompatTextViewOwner.setText("Owner ");
            PVH.singleSpinnerSearchOwner.setItems((List<cKeyPairBoolData>) keyPairBoolData[0],
                    -1,
                    new cSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<cKeyPairBoolData> items) {
                            for (int i = 0; i < items.size(); i++) {
                                if (items.get(i).isSelected()) {
                                    privilegeDomain.setOwnerID((int) items.get(i).getId());
                                    break;
                                }
                            }
                            Log.d(TAG, "OWNER : " + privilegeDomain.getOwnerID());
                        }
                    });


            // -1 is no by default selection, 0 to length will select corresponding values
            // called when click organization single spinner search
            PVH.singleSpinnerSearchOrg.setItems((List<cKeyPairBoolData>) keyPairBoolData[1],
                    -1, new cSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<cKeyPairBoolData> items) {
                            for (int i = 0; i < items.size(); i++) {
                                if (items.get(i).isSelected()) {
                                    privilegeDomain.setOrgID((int) items.get(i).getId());
                                    break;
                                }
                            }
                            Log.d(TAG, "ORGANIZATION ID : " + privilegeDomain.getOrgID());
                        }
                    });

            // -1 is no by default selection, 0 to length will select corresponding values
            // called when click other organization multi spinner search
            PVH.multiSpinnerSearchOtherOrg.setItems((List<cKeyPairBoolData>) keyPairBoolData[2],
                    -1, new cSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<cKeyPairBoolData> items) {
                            for (int i = 0; i < items.size(); i++) {
                                int orgID = (int) items.get(i).getId();
                                if (items.get(i).isSelected()) {
                                    if ((privilegeDomain.getGroupBITS() & orgID) != orgID) {
                                        // add other organizations
                                        privilegeDomain.setGroupBITS(privilegeDomain.getGroupBITS() | orgID);
                                    }
                                }
                                if (!items.get(i).isSelected()) {
                                    if ((privilegeDomain.getGroupBITS() & orgID) == orgID) {
                                        // remove other organizations
                                        privilegeDomain.setGroupBITS(privilegeDomain.getGroupBITS() & ~orgID);
                                    }
                                }
                            }
                            Log.d(TAG, "OTHER ORGANIZATION : " + privilegeDomain.getGroupBITS());
                        }
                    });

            //}
            // -1 is no by default selection, 0 to length will select corresponding values
            // called when click permissions multi spinner search
            PVH.tableSpinner.setItems((cKeyPairBoolData[]) keyPairBoolData[3],
                    -1, new cTableSpinnerListener() {
                        @Override
                        public void onFixedItemsSelected(cKeyPairBoolData[] items) {
                            for (int i = 0; i < items.length; i++) {
                                int permID = (int) items[i].getId();
                                if (items[i].isSelected()) {
                                    if ((privilegeDomain.getPermsBITS() & permID) != permID) {
                                        // add operation
                                        privilegeDomain.setPermsBITS(privilegeDomain.getPermsBITS() | permID);
                                    }
                                }
                                if (!items[i].isSelected()) {
                                    if ((privilegeDomain.getPermsBITS() & permID) == permID) {
                                        // remove operation
                                        privilegeDomain.setPermsBITS(privilegeDomain.getPermsBITS() & ~permID);
                                    }
                                }
                            }
                            Log.d(TAG, "PERMS : " + privilegeDomain.getPermsBITS());
                        }
                    });

            // -1 is no by default selection, 0 to length will select corresponding values
            // called when click statuses multi spinner search
            PVH.multiSpinnerSearchStatuses.setItems((List<cKeyPairBoolData>) keyPairBoolData[4],
                    -1, new cSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<cKeyPairBoolData> items) {
                            for (int i = 0; i < items.size(); i++) {
                                int statusID = (int) items.get(i).getId();
                                if (items.get(i).isSelected()) {
                                    if ((privilegeDomain.getStatusBITS() & statusID) != statusID) {
                                        // add status
                                        privilegeDomain.setStatusBITS(privilegeDomain.getStatusBITS() | statusID);
                                    }
                                }
                                if (!items.get(i).isSelected()) {
                                    if ((privilegeDomain.getStatusBITS() & statusID) == statusID) {
                                        // remove status
                                        privilegeDomain.setStatusBITS(privilegeDomain.getStatusBITS() & ~statusID);
                                    }
                                }
                            }
                            Log.d(TAG, "STATUSES : " + privilegeDomain.getStatusBITS());
                        }
                    });

            createdDate = FORMAT_DATE.format(privilegeDomain.getCreatedDate());
            modifiedDate = FORMAT_DATE.format(new Date());
            syncedDate = FORMAT_DATE.format(privilegeDomain.getSyncedDate());

            PVH.textViewCreatedDate.setText(createdDate);
            PVH.textViewModifiedDate.setText(modifiedDate);
            PVH.textViewSyncedDate.setText(syncedDate);

            /** change perms **/
            PVH.textViewChangeUserIcon.setTypeface(null, Typeface.NORMAL);
            PVH.textViewChangeUserIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
            PVH.textViewChangeUserIcon.setText(context.getResources().getString(R.string.fa_perms));
            PVH.textViewChangeUserIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    LayoutInflater inflater = LayoutInflater.from(context);
                    View titleView = inflater.inflate(R.layout.me_custom_title, null);

                    // setting icon to dialog
                    TextDrawable faIcon = new TextDrawable(context);
                    faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                    faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    faIcon.setText(context.getResources().getText(R.string.fa_perms));
                    faIcon.setTextColor(context.getColor(R.color.colorAccent));


                    // set title
                    //alertDialogBuilder.setTitle(context.getResources().getString(R.string.privilege_change_title));
                    TextView title = titleView.findViewById(R.id.exemptionSubHeading4);
                    title.setText(context.getResources().getString(R.string.privilege_change_title));
                    alertDialogBuilder.setCustomTitle(titleView);
                    alertDialogBuilder.setIcon(faIcon);

                    // set dialog message
                    alertDialogBuilder
                            .setMessage(context.getResources().getString(R.string.privilege_change_question) +
                                    "'s Record ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // update user record permissions in the database
                                    new cUpdatePrivilegeTask().execute(privilegeDomain, treeModel);

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
        }
    }

    /**
     * update privilege task for updating privilege in the database
     **/
    class cUpdatePrivilegeTask extends AsyncTask<Object, Void, Boolean> {

        private cPermissionDomain privilegeDomain;
        private cTreeModel treeModel;
        boolean result;

        @Override
        protected Boolean doInBackground(Object... objects) {
            privilegeDomain = (cPermissionDomain) objects[0];
            treeModel = (cTreeModel) objects[1];
            treeModel.setModelObject(privilegeDomain);// update the modified privilege.
            result = privilegeHandler.updatePrivilege(privilegeDomain);

            return result;
        }

        @Override
        protected void onPostExecute(Boolean results) {
            super.onPostExecute(results);
            if (results) {
                notifyDataSetChanged();
                permissionInterface.onResponseMessage(R.string.privilege_edit_title, R.string.privilege_edit_success);
            } else {
                permissionInterface.onResponseMessage(R.string.privilege_edit_title, R.string.privilege_edit_error);
            }
        }
    }


/*
    private void filterDialog(int animationSource, String title, View filterLayout, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(filterLayout);
        builder.setPositiveButton(
                "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            // update a relavent adapter (getSelectedTreeModel used for all trees)
                            //((OnGridViewItemSelectedListener) getActivity()).getSelectedTreeModel(getSelectedModel());

                            // select a relavent fragment through a position
                            //((OnGridViewItemSelectedListener) getActivity()).getGridPosition(position);
                        } catch (ClassCastException cce) {

                        }
                    }
                }
        );

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dismiss
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = animationSource;
        builder.show();
    }
*/
    public class cPrivilegeTreeViewHolder extends cTreeViewHolder{
        private TextView textViewName;
        private TextView textViewDescription;
        private TextView textViewCountRole;
        private TextView textViewExpandPrivilegeIcon;
        private TextView textViewQuickActionIcon;
        private TextView textViewPrivilegeDetailIcon;
        private TextView textViewSyncPrivilegeIcon;
        private TextView textViewDeletePrivilegeIcon;
        private TextView textViewEditPrivilegeIcon;

        private cExpandableLayout expandableLayout;

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

        //private RecyclerView recyclerViewStatus;

        private View treeView;

        public cPrivilegeTreeViewHolder(final View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.textViewName = (TextView) treeViewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = (TextView) treeViewHolder.findViewById(R.id.textViewDescription);
            this.textViewCountRole = (TextView) treeViewHolder.findViewById(R.id.textViewCountRole);
            this.textViewExpandPrivilegeIcon = (TextView) treeViewHolder.findViewById(R.id.textViewExpandPrivilegeIcon);
            this.textViewQuickActionIcon = (TextView) treeViewHolder.findViewById(R.id.textViewQuickActionIcon);
            this.textViewPrivilegeDetailIcon = (TextView) treeViewHolder.findViewById(R.id.textViewPrivilegeDetailIcon);
            this.textViewSyncPrivilegeIcon = (TextView) treeViewHolder.findViewById(R.id.textViewSyncPrivilegeIcon);
            this.textViewDeletePrivilegeIcon = (TextView) treeViewHolder.findViewById(R.id.textViewDeletePrivilegeIcon);
            this.textViewEditPrivilegeIcon = (TextView) treeViewHolder.findViewById(R.id.textViewEditPrivilegeIcon);

            this.expandableLayout = (cExpandableLayout) treeViewHolder.findViewById(R.id.expandableLayout);

            /* common attributes */
            this.singleSpinnerSearchOwner =
                    (cSingleSpinnerSearch_old) treeViewHolder.findViewById(R.id.appCompatSpinnerOwner);
            this.appCompatTextViewOwner = (AppCompatTextView) treeViewHolder.findViewById(R.id.appCompatTextViewOwner);
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

        private void updateItem(final int position) {
            this.expandableLayout.setOnExpandListener(new cExpandableLayout.OnExpandListener() {
                @Override
                public void onExpand(boolean expanded) {
                    registerExpand(position);
                }
            });

            this.expandableLayout.setExpand(expandedPositionSet.contains(position));
        }
    }


    public class cEntityTreeViewHolder extends cTreeViewHolder implements
            TabLayout.OnTabSelectedListener, iEntityTVHInterface {
        private TextView textViewName;
        private TextView textViewDescription;

        private TextView textViewEntityDetailIcon;
        private TextView textViewExpandEntityIcon;
        //private TextView textViewSaveEntityIcon;
        private TextView textViewDeleteEntityIcon;
        private TextView textViewSyncEntityIcon;

        private cExpandableLayout expandableLayout;
        private RelativeLayout relativeLayoutHiddenLayer;

        private AppCompatButton appCompatButtonUpdate;
        private AppCompatButton appCompatButtonReset;

        private TabLayout tabLayout;
        private ViewPager viewPager;
        private cFragmentPagerAdapter fragmentPagerAdapter;

        private View treeView;

        public cEntityTreeViewHolder(View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;
            textViewName = (TextView) treeViewHolder.findViewById(R.id.textViewName);
            textViewDescription = (TextView) treeViewHolder.findViewById(R.id.textViewDescription);

            textViewEntityDetailIcon = (TextView) treeViewHolder.findViewById(R.id.textViewEntityDetailIcon);
            textViewExpandEntityIcon = (TextView) treeViewHolder.findViewById(R.id.textViewExapandEntityIcon);
            //textViewSaveEntityIcon = (TextView) treeViewHolder.findViewById(R.id.textViewSaveEntityIcon);
            textViewDeleteEntityIcon = (TextView) treeViewHolder.findViewById(R.id.textViewDeleteEntityIcon);
            textViewSyncEntityIcon = (TextView) treeViewHolder.findViewById(R.id.textViewSyncEntityIcon);

            expandableLayout = (cExpandableLayout) treeViewHolder.findViewById(R.id.expandableLayout);
            relativeLayoutHiddenLayer = (RelativeLayout) treeViewHolder.findViewById(R.id.relativeLayoutHiddenLayer);

            /** initialize buttons to update or reset permission domain **/
            appCompatButtonUpdate = (AppCompatButton) treeViewHolder.findViewById(R.id.appCompactButtonUpdate);

            appCompatButtonReset = (AppCompatButton) treeViewHolder.findViewById(R.id.appCompactButtonReset);

            // initializing tabLayout
            tabLayout = (TabLayout) treeViewHolder.findViewById(R.id.tabLayoutEntities);

            // initializing viewPager
            viewPager = (ViewPager) treeViewHolder.findViewById(R.id.viewPagerEntities);

        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }

        private void updateItem(final int position) {
            this.expandableLayout.setOnExpandListener(new cExpandableLayout.OnExpandListener() {
                @Override
                public void onExpand(boolean expanded) {
                    registerExpand(position);
                }
            });

            this.expandableLayout.setExpand(expandedPositionSet.contains(position));
        }

        private void populateEntities(int privilegeDomainID, cEntityDomain entityDomain,
                                      int operationMask, int statusMask,
                                      ArrayList<cPermissionDomain> permissionDomains) {

            // initializing view pager adapter
            fragmentPagerAdapter = new cFragmentPagerAdapter(fragmentManager);

            fragmentPagerAdapter.addFrag(new cOperationsFragment().newInstance(
                    privilegeDomainID, entityDomain, createOperations, operationMask,
                    createStatuses, statusMask, permissionDomains, this), "Add");

            fragmentPagerAdapter.addFrag(new cOperationsFragment().newInstance(
                    privilegeDomainID, entityDomain, readOperations, operationMask,
                    readStatuses, statusMask, permissionDomains, this), "Read");

            fragmentPagerAdapter.addFrag(new cOperationsFragment().newInstance(
                    privilegeDomainID, entityDomain, updateOperations, operationMask,
                    updateStatuses, statusMask, permissionDomains, this), "Edit");

            fragmentPagerAdapter.addFrag(new cOperationsFragment().newInstance(
                    privilegeDomainID, entityDomain, deleteOperations, operationMask,
                    deleteStatuses, statusMask, permissionDomains, this), "Del");

            fragmentPagerAdapter.addFrag(new cOperationsFragment().newInstance(
                    privilegeDomainID, entityDomain, syncOperations, operationMask,
                    syncStatuses, statusMask, permissionDomains, this), "Sync");

            // adding adapter to pager
            viewPager.setAdapter(fragmentPagerAdapter);

            // adding onTabSelectedListener to swipe views
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    //ViewGroup.LayoutParams params = relativeLayoutHiddenLayer.getLayoutParams();
                    //params.height = context.getResources().getDimensionPixelSize(R.dimen.statuses_height);

                    /*
                    ViewGroup.LayoutParams params = relativeLayoutHiddenLayer.getLayoutParams();
                    if (position == 0){
                        params.height = context.getResources().getDimensionPixelSize(R.dimen.operations_height);
                    }

                    if (position == 1){
                        params.height = context.getResources().getDimensionPixelSize(R.dimen.statuses_height);
                    }
                    relativeLayoutHiddenLayer.requestLayout();
                    //Log.v(TAG,"HEIGHT = " + position);
                    */
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        /**
         * Called when a tab enters the selected state.
         *
         * @param tab The tab that was selected
         */
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            // on tab selected, show respected fragment view
            viewPager.setCurrentItem(tab.getPosition());
        }

        /**
         * Called when a tab exits the selected state.
         *
         * @param tab The tab that was unselected
         */
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        /**
         * Called when a tab that is already selected is chosen again by the user. Some applications
         * may use this action to return to the top level of a category.
         *
         * @param tab The tab that was reselected.
         */
        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }

        @Override
        public void onOperationUpdate(int option) {
            ViewGroup.LayoutParams params = relativeLayoutHiddenLayer.getLayoutParams();
            if (option == EXPAND) {
                params.height = context.getResources().getDimensionPixelSize(R.dimen.statuses_height);
            }

            if (option == COLLAPSE) {
                params.height = context.getResources().getDimensionPixelSize(R.dimen.details_height);
            }
            relativeLayoutHiddenLayer.requestLayout();
            //Log.v(TAG,"HEIGHT = " + position);
        }

        Gson gson = new Gson();

        public void createPermissions() {
            for (int i = 0; i < fragmentPagerAdapter.getCount(); i++) {
                cOperationsFragment opsFrag = (cOperationsFragment) fragmentPagerAdapter.getItem(i);
                String opsFragTitle = (String) fragmentPagerAdapter.getPageTitle(i);

                /** create the permissions **/
                if (opsFrag.getCreate_perms() != null && !opsFrag.getCreate_perms().isEmpty()) {
                    Log.d(TAG, "Records to be created under '" + opsFragTitle + " Permission' : " +
                            opsFrag.getCreate_perms().size() + " : " + gson.toJson(opsFrag.getCreate_perms()));

                    /** CREATE **/
                    opsFrag.createPermissions(new ArrayList<>(opsFrag.getCreate_perms()));
                }
            }
        }

        public void updatePermissions() {
            for (int i = 0; i < fragmentPagerAdapter.getCount(); i++) {
                cOperationsFragment opsFrag = (cOperationsFragment) fragmentPagerAdapter.getItem(i);
                String opsFragTitle = (String) fragmentPagerAdapter.getPageTitle(i);

                /** update the permissions **/
                if (opsFrag.getUpdate_perms() != null && !opsFrag.getUpdate_perms().isEmpty()) {
                    Log.d(TAG, "Records to be updated under '" + opsFragTitle + " Permission' : " +
                            gson.toJson(opsFrag.getUpdate_perms()));

                    /** UPDATE **/
                    opsFrag.updatePermissions(new ArrayList<>(opsFrag.getUpdate_perms()));

                }
            }
        }

        public void deletePermissions() {
            for (int i = 0; i < fragmentPagerAdapter.getCount(); i++) {
                cOperationsFragment opsFrag = (cOperationsFragment) fragmentPagerAdapter.getItem(i);
                String opsFragTitle = (String) fragmentPagerAdapter.getPageTitle(i);

                /** delete the permissions **/
                if (opsFrag.getDelete_perms() != null && !opsFrag.getDelete_perms().isEmpty()) {
                    Log.d(TAG, "Records to be deleted under '" + opsFragTitle + " Permission' : " +
                            gson.toJson(opsFrag.getDelete_perms()));

                    /** DELETE **/
                    opsFrag.deletePermissions(new ArrayList<>(opsFrag.getDelete_perms()));
                }
            }
        }

        /**
         * check whether there is any permission changed
         **/
        public boolean isPermissionCreated() {
            for (int i = 0; i < fragmentPagerAdapter.getCount(); i++) {
                cOperationsFragment opsFrag = (cOperationsFragment) fragmentPagerAdapter.getItem(i);

                /** refresh the permissions to be changed **/
                opsFrag.onRefreshPermissions();

                /** create the permissions **/
                if (opsFrag.getCreate_perms() != null && !opsFrag.getCreate_perms().isEmpty()) {
                    return true;
                }
            }
            return false;
        }

        public boolean isPermissionUpdated() {
            for (int i = 0; i < fragmentPagerAdapter.getCount(); i++) {
                cOperationsFragment opsFrag = (cOperationsFragment) fragmentPagerAdapter.getItem(i);

                /** refresh the permissions to be changed **/
                //opsFrag.onRefreshPermissions();
                Log.d(TAG, gson.toJson(opsFrag.getUpdate_perms()));

                /** update the permissions **/
                if (opsFrag.getUpdate_perms() != null && !opsFrag.getUpdate_perms().isEmpty()) {
                    return true;
                }
            }
            return false;
        }

        public boolean isPermissionDeleted() {
            for (int i = 0; i < fragmentPagerAdapter.getCount(); i++) {
                cOperationsFragment opsFrag = (cOperationsFragment) fragmentPagerAdapter.getItem(i);

                /** refresh the permissions to be changed **/
                opsFrag.onRefreshPermissions();

                /** delete the permissions **/
                if (opsFrag.getDelete_perms() != null && !opsFrag.getDelete_perms().isEmpty()) {
                    return true;
                }
            }
            return false;
        }

        private void setupTabIcons() {
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                //TextView tabOne = (TextView) LayoutInflater.from(context).inflate(R.layout.me_custom_tab, null);
                //TextDrawable tabOne = new TextDrawable(getContext());

                LinearLayout tabLinearLayout = (LinearLayout) LayoutInflater.from(context).
                        inflate(R.layout.me_custom_tab, null);
                TextView tabOne = (TextView) tabLinearLayout.findViewById(R.id.tabIcon);
                TextView tabLabel = (TextView) tabLinearLayout.findViewById(R.id.tabLabel);

                if (i == 0) {
                    tabOne.setText(context.getResources().getText(R.string.fa_create));
                    tabOne.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                    tabOne.setTextColor(Color.WHITE);
                    tabOne.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));

                    //tabLabel.setText(" "+entityViewPagerAdapter.getPageTitle(i));
                    //tabLabel.setTextSize(14);

                    tabLayout.getTabAt(i).setCustomView(tabLinearLayout);
                }

                if (i == 1) {
                    tabOne.setText(context.getResources().getText(R.string.fa_read));
                    tabOne.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                    tabOne.setTextColor(Color.WHITE);
                    tabOne.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));

                    //tabLabel.setText(" "+entityViewPagerAdapter.getPageTitle(i));
                    //tabLabel.setTextSize(14);

                    tabLayout.getTabAt(i).setCustomView(tabLinearLayout);
                }

                if (i == 2) {
                    tabOne.setText(context.getResources().getText(R.string.fa_update));
                    tabOne.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                    tabOne.setTextColor(Color.WHITE);
                    tabOne.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));

                    //tabLabel.setText(" "+entityViewPagerAdapter.getPageTitle(i));
                    //tabLabel.setTextSize(14);

                    tabLayout.getTabAt(i).setCustomView(tabLinearLayout);
                }

                if (i == 3) {
                    tabOne.setText(context.getResources().getText(R.string.fa_delete));
                    tabOne.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                    tabOne.setTextColor(Color.WHITE);
                    tabOne.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));

                    //tabLabel.setText(" "+entityViewPagerAdapter.getPageTitle(i));
                    //tabLabel.setTextSize(14);

                    tabLayout.getTabAt(i).setCustomView(tabLinearLayout);
                }

                if (i == 4) {
                    tabOne.setText(context.getResources().getText(R.string.fa_sync));
                    tabOne.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                    tabOne.setTextColor(Color.WHITE);
                    tabOne.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));

                    //tabLabel.setText(" "+entityViewPagerAdapter.getPageTitle(i));
                    //tabLabel.setTextSize(14);

                    tabLayout.getTabAt(i).setCustomView(tabLinearLayout);
                }
            }
        }

/*
        private void setupTabIcons() {
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                //TextView tabOne = (TextView) LayoutInflater.from(context).inflate(R.layout.me_custom_tab, null);
                //TextDrawable tabOne = new TextDrawable(context);
                LinearLayout tabLinearLayout = (LinearLayout)
                        LayoutInflater.from(context).inflate(R.layout.me_custom_tab, null);
                TextView tabOne = (TextView) tabLinearLayout.findViewById(R.id.tabIcon);
                TextView tabLabel = (TextView) tabLinearLayout.findViewById(R.id.tabLabel);

                if (i == 0) {
                    tabOne.setText(context.getResources().getText(R.string.fa_groups));
                    tabOne.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                    tabOne.setTextColor(Color.WHITE);
                    tabOne.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));

                    tabLabel.setText(" " + fragmentPagerAdapter.getPageTitle(i));
                    tabLabel.setTextSize(16);

                    tabLayout.getTabAt(i).setCustomView(tabLinearLayout);
                }

                if (i == 1) {
                    tabOne.setText(context.getResources().getText(R.string.fa_statuses));
                    tabOne.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                    tabOne.setTextColor(Color.WHITE);
                    tabOne.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));

                    tabLabel.setText(" " + fragmentPagerAdapter.getPageTitle(i));
                    tabLabel.setTextSize(16);

                    tabLayout.getTabAt(i).setCustomView(tabLinearLayout);
                }
            }
        }
*/
    }

    // Searches an operation ID in a list of operation IDs and return
    // the corresponding operation domain.
    public cOperationDomain getOperationByID(ArrayList<cOperationDomain> operationDomains,
                                             int operationID) {
        cOperationDomain operationDomain = null;
        for (int i = 0; i < operationDomains.size(); i++) {
            if (operationDomains.get(i).getOperationID() == operationID) {
                operationDomain = operationDomains.get(i);
                return operationDomain;
            }
        }
        return operationDomain;
    }

    public cStatusDomain getStatusByID(ArrayList<cStatusDomain> statusDomains,
                                       int statusID) {
        cStatusDomain statusDomain = null;
        for (int i = 0; i < statusDomains.size(); i++) {
            if (statusDomains.get(i).getStatusID() == statusID) {
                statusDomain = statusDomains.get(i);
                return statusDomain;
            }
        }
        return statusDomain;
    }

    private void registerExpand(int position) {
        if (expandedPositionSet.contains(position)) {
            removeExpand(position);
            //Toast.makeText(context, "Position: " + position + " collapsed!", Toast.LENGTH_SHORT).show();
        } else {
            addExpand(position);
            //Toast.makeText(context, "Position: " + position + " expanded!", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeExpand(int position) {
        expandedPositionSet.remove(position);
    }

    private void addExpand(int position) {
        expandedPositionSet.add(position);
    }

    void updateCheckBox(int position, CheckBox mCheckedTextView) {
        // use the sparse boolean array to check
        if (!checkBoxStateArray.get(position, false)) {
            mCheckedTextView.setChecked(false);
        } else {
            mCheckedTextView.setChecked(true);
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

/*

    /** return populated users through an interface **/
    //keyPairBoolUsers = permissionInterface.getAvailableUsers();
    //Log.d(TAG, gson.toJson(keyPairBoolUsers));

                    /* get all users from database
                    final ArrayList<cUserDomain> users = userHandler.getUserList(
                            session.loadUserID(),                  /* loggedIn user id
                            session.loadOrganizationID(),          /* loggedIn own org.
                            session.loadPrimaryRole(session.loadUserID(),
                                    session.loadOrganizationID()), /* primary group bit
                            session.loadSecondaryRoles(session.loadUserID(),
                                    session.loadOrganizationID())   secondary group bits
                    );

    // get all organizations from database
    final ArrayList<cOrganizationDomain> orgs =
            organizationHandler.getOrganizationList(
                    session.loadUserID(),                  /* loggedIn user id
                    session.loadOrganizationID(),          /* loggedIn own org.
                    session.loadPrimaryRole(session.loadUserID(),
                            session.loadOrganizationID()), /* primary group bit
                    session.loadSecondaryRoles(session.loadUserID(),
                            session.loadOrganizationID())  /* secondary group bits
            );

/* get the detailed of the loggedin user
//final cUserDomain loggedInUser = session.loadCurrentUser();

// create a pair list of user ids and names
                    /*final List<cKeyPairBoolData> keyPairBoolUsers = new ArrayList<>();
                    for (int i = 0; i < users.size(); i++) {
                        cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                        idNameBool.setId(users.get(i).getUserID());
                        idNameBool.setName(users.get(i).getName());
                        if (privilegeDomain.getOwnerID() == users.get(i).getUserID()) {
                            idNameBool.setSelected(true);
                        } else {
                            idNameBool.setSelected(false);
                        }
                        keyPairBoolUsers.add(idNameBool);
                    }

// -1 is no by default selection, 0 to length will select corresponding values
// called when click owner single spinner search
                    PVH.appCompatTextViewOwner.setText("Owner ");
                            PVH.singleSpinnerSearchOwner.setItems(keyPairBoolUsers, -1,
                            new cSpinnerListener() {
@Override
public void onItemsSelected(List<cKeyPairBoolData> items) {
        for (int i = 0; i < items.size(); i++) {
        if (items.get(i).isSelected()) {
        privilegeDomain.setOwnerID((int) items.get(i).getId());
        break;
        }
        }
        Log.d(TAG, "OWNER : " + privilegeDomain.getOwnerID());
        }
        });

// create a pair list of organization ids and names
final List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
        for (int i = 0; i < orgs.size(); i++) {
        cKeyPairBoolData idNameBool = new cKeyPairBoolData();
        idNameBool.setId(orgs.get(i).getOrganizationID());
        idNameBool.setName(orgs.get(i).getOrganizationName());
        if (privilegeDomain.getOrganizationID() == orgs.get(i).getOrganizationID()) {
        idNameBool.setSelected(true);
        } else {
        idNameBool.setSelected(false);
        }
        keyPairBoolOrgs.add(idNameBool);
        }

        // -1 is no by default selection, 0 to length will select corresponding values
        // called when click organization single spinner search
        PVH.singleSpinnerSearchOrg.setItems(keyPairBoolOrgs, -1, new cSpinnerListener() {
@Override
public void onItemsSelected(List<cKeyPairBoolData> items) {
        for (int i = 0; i < items.size(); i++) {
        if (items.get(i).isSelected()) {
        privilegeDomain.setOrgID((int) items.get(i).getId());
        break;
        }
        }
        Log.d(TAG, "ORGANIZATION ID : " + privilegeDomain.getOrgID());
        }
        });

// create a pair list of other organization ids and names
final List<cKeyPairBoolData> keyPairBoolOtherOrgs = new ArrayList<>();
        for (int i = 0; i < orgs.size(); i++) {
        cKeyPairBoolData idNameBool = new cKeyPairBoolData();
        idNameBool.setId(orgs.get(i).getOrganizationID());
        idNameBool.setName(orgs.get(i).getOrganizationName());
        if ((session.loadSecondaryRoles(session.loadUserID(), session.loadOrganizationID()) &
        orgs.get(i).getOrganizationID()) == orgs.get(i).getOrganizationID()) {
        idNameBool.setSelected(true);
        } else {
        idNameBool.setSelected(false);
        }
        keyPairBoolOtherOrgs.add(idNameBool);
        }

        // -1 is no by default selection, 0 to length will select corresponding values
        // called when click other organization multi spinner search
        PVH.multiSpinnerSearchOtherOrg.setItems(keyPairBoolOtherOrgs, -1, new cSpinnerListener() {
@Override
public void onItemsSelected(List<cKeyPairBoolData> items) {
        for (int i = 0; i < items.size(); i++) {
        int orgID = (int) items.get(i).getId();
        if (items.get(i).isSelected()) {
        if ((privilegeDomain.getGroupBITS() & orgID) != orgID) {
        // add other organizations
        privilegeDomain.setGroupBITS(privilegeDomain.getGroupBITS() | orgID);
        }
        }
        if (!items.get(i).isSelected()) {
        if ((privilegeDomain.getGroupBITS() & orgID) == orgID) {
        // remove other organizations
        privilegeDomain.setGroupBITS(privilegeDomain.getGroupBITS() & ~orgID);
        }
        }
        }
        Log.d(TAG, "OTHER ORGANIZATION : " + privilegeDomain.getGroupBITS());
        }
        });

// create a pair list of permission ids and names
final cKeyPairBoolData[] keyPairBoolPerms = new cKeyPairBoolData[NUM_PERMS];
        //if (permissionDomains.size() > 0) {
        int opBITS = privilegeDomain.getPermsBITS();
        //keyPairBoolPerms[0].setId();keyPairBoolPerms[0].setName();
        for (int i = 0; i < session.permissions.length; i++) {
        //Log.d(TAG, " "+(opBITS & session.permissions[i]));
        cKeyPairBoolData idNameBool = new cKeyPairBoolData();
        idNameBool.setId(session.permissions[i]);
        idNameBool.setName(session.perm_names[i]);
        idNameBool.setSelected((opBITS & session.permissions[i]) == session.permissions[i]);
        keyPairBoolPerms[i] = idNameBool;
        }

        //}
        // -1 is no by default selection, 0 to length will select corresponding values
        // called when click permissions multi spinner search
        PVH.tableSpinner.setItems(keyPairBoolPerms, -1, new cTableSpinnerListener() {
@Override
public void onFixedItemsSelected(cKeyPairBoolData[] items) {
        for (int i = 0; i < items.length; i++) {
        int permID = (int) items[i].getId();
        if (items[i].isSelected()) {
        if ((privilegeDomain.getPermsBITS() & permID) != permID) {
        // add operation
        privilegeDomain.setPermsBITS(privilegeDomain.getPermsBITS() | permID);
        }
        }
        if (!items[i].isSelected()) {
        if ((privilegeDomain.getPermsBITS() & permID) == permID) {
        // remove operation
        privilegeDomain.setPermsBITS(privilegeDomain.getPermsBITS() & ~permID);
        }
        }
        }
        Log.d(TAG, "PERMS : " + privilegeDomain.getPermsBITS());
        }
        });

// create a pair list of statuses ids and names
final List<cKeyPairBoolData> keyPairBoolStatuses = new ArrayList<>();
        for (int i = 0; i < statusDomains.size(); i++) {
        cKeyPairBoolData idNameBool = new cKeyPairBoolData();
        idNameBool.setId(statusDomains.get(i).getStatusID());
        idNameBool.setName(statusDomains.get(i).getName());
        if ((privilegeDomain.getStatusBITS() & statusDomains.get(i).getStatusID()) ==
        statusDomains.get(i).getStatusID()) {
        idNameBool.setSelected(true);
        } else {
        idNameBool.setSelected(false);
        }
        keyPairBoolStatuses.add(idNameBool);
        }

        // -1 is no by default selection, 0 to length will select corresponding values
        // called when click statuses multi spinner search
        PVH.multiSpinnerSearchStatuses.setItems(keyPairBoolStatuses, -1, new cSpinnerListener() {
@Override
public void onItemsSelected(List<cKeyPairBoolData> items) {
        for (int i = 0; i < items.size(); i++) {
        int statusID = (int) items.get(i).getId();
        if (items.get(i).isSelected()) {
        if ((privilegeDomain.getStatusBITS() & statusID) != statusID) {
        // add status
        privilegeDomain.setStatusBITS(privilegeDomain.getStatusBITS() | statusID);
        }
        }
        if (!items.get(i).isSelected()) {
        if ((privilegeDomain.getStatusBITS() & statusID) == statusID) {
        // remove status
        privilegeDomain.setStatusBITS(privilegeDomain.getStatusBITS() & ~statusID);
        }
        }
        }
        Log.d(TAG, "STATUSES : " + privilegeDomain.getStatusBITS());
        }
        });
*/