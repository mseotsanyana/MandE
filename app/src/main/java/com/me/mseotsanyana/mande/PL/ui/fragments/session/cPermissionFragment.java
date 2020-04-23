package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.domain.session.cEntityDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.entity.Impl.cEntityHandler;
import com.me.mseotsanyana.mande.BLL.domain.session.cOperationDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.operation.Impl.cOperationHandler;
import com.me.mseotsanyana.mande.BLL.domain.session.cPermissionDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.permission.Impl.cPermissionHandler;
import com.me.mseotsanyana.mande.BLL.domain.session.cRoleDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.role.Impl.cRoleHandler;
import com.me.mseotsanyana.mande.BLL.domain.session.cStatusDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.status.Impl.cStatusHandler;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cPermissionAdapter;
import com.me.mseotsanyana.mande.UTIL.INTERFACE.iPermissionInterface;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.cMultiSpinnerSearch;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSpinnerListener;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/24.
 */

public class cPermissionFragment extends Fragment implements iPermissionInterface {
    private static final String TAG = cPermissionFragment.class.getSimpleName();

    //private cSessionManager session;

    private ArrayList<cTreeModel> permissionTree;
    private ArrayList<cStatusDomain> statusDomains;
    private ArrayList<cOperationDomain> operationDomains;
    private ArrayList<cRoleDomain> roleDomains;

    private ArrayList<cPermissionDomain> selectedPermissions;

    private cRoleHandler roleHandler;
    private cPermissionHandler privilegeHandler;
    private cPermissionHandler permissionHandler;
    private cOperationHandler operationHandler;
    private cStatusHandler statusHandler;

    private cEntityHandler entityHandler;

    private cPermissionAdapter permissionTreeAdapter;

    private Toolbar toolBar;
    private Menu toolBarMenu;
    private MenuItem toolBarMenuItem;
    private RecyclerView recyclerView;

    private int level = 0;
    private List<cKeyPairBoolData> keyPairBoolData;

    Gson gson = new Gson();

    public cPermissionFragment() {

    }

    public static cPermissionFragment newInstance() {
        cPermissionFragment fragment = new cPermissionFragment();
        return fragment;
    }

    /*
     * this event fires 1st, before creation of fragment or any views
     * the onAttach method is called when the Fragment instance is
     * associated with an Activity and this does not mean the activity
     * is fully initialized.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * this method is fired 2nd, before views are created for the fragment,
     * the onCreate method is called when the fragment instance is being created,
     * or re-created use onCreate for any standard setup that does not require
     * the activity to be fully created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //session = new cSessionManager(getContext());

        permissionTree = new ArrayList<cTreeModel>();
        statusDomains = new ArrayList<cStatusDomain>();
        operationDomains = new ArrayList<cOperationDomain>();
        roleDomains = new ArrayList<cRoleDomain>();

        selectedPermissions = new ArrayList<cPermissionDomain>();

        // getting a action_list with all projects in a database

        roleHandler = new cRoleHandler(getActivity());
        privilegeHandler = new cPermissionHandler(getActivity());
        permissionHandler = new cPermissionHandler(getActivity());
        operationHandler = new cOperationHandler(getActivity());
        statusHandler = new cStatusHandler(getActivity());

        entityHandler = new cEntityHandler(getActivity());

        permissionTreeAdapter = new cPermissionAdapter(
                getActivity(),
                permissionTree, level,
                operationDomains, statusDomains,
                getChildFragmentManager(), this);
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Register the event to subscribe.
        //-cGlobalBus.getBus().register(this);
        return inflater.inflate(R.layout.permission_list_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // instantiate and initialize the action_list
        recyclerView = (RecyclerView) view.findViewById(R.id.permissionRecyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        // populate the action_list with data from database
        recyclerView.setAdapter(permissionTreeAdapter);

        recyclerView.setLayoutManager(llm);

        // populate role user tree from database
        /*readPrivileges(session.loadUserID(), /* loggedIn user id
                session.loadOrgID(),
                session.loadPrimaryRole(),   /* primary group bit
                session.loadSecondaryRoles());*/

        // initialise the floating action button (FAB)
        onCreatePrivilege(view);

        // initialize the toolbar
        toolBar = (Toolbar) view.findViewById(R.id.me_toolbar);
        toolBar.setTitle(R.string.permission_list_title);
        toolBar.setTitleTextColor(Color.WHITE);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolBar);
    }

    /**
     * CREATING AND SAVING A NEW PRIVILEGE DOMAIN IN THE DATABASE
     **/

    private void onCreatePrivilege(View view) {
        view.findViewById(R.id.permissionFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** call create privilege task to create a new privilege **/
                createPrivilege();
            }
        });
    }

    /**
     * create new privilege entity in the database
     **/
    protected void createPrivilege() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View formElementsView = inflater.inflate(R.layout.privilege_add_edit_record, null, false);
        // -1 is no by default selection, 0 to length will select corresponding values
        cSingleSpinnerSearch singleSpinnerSearchRole =
                (cSingleSpinnerSearch) formElementsView.findViewById(R.id.appCompatSpinnerRole);

        final AppCompatEditText editTextPrivilegeName =
                (AppCompatEditText) formElementsView.findViewById(R.id.editTextPrivilegeName);
        final AppCompatEditText editTextPrivilegeDescription =
                (AppCompatEditText) formElementsView.findViewById(R.id.editTextPrivilegeDescription);

        final cPermissionDomain privilege = new cPermissionDomain();

        /** read roles to populate the spinner **/
        new cReadRoles().execute(singleSpinnerSearchRole, privilege);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setView(formElementsView);

        /** setting icon to dialog **/
        TextDrawable faIcon = new TextDrawable(getActivity());
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(cFontManager.getTypeface(getActivity(), cFontManager.FONTAWESOME));
        faIcon.setText(getActivity().getResources().getText(R.string.fa_create));
        faIcon.setTextColor(getActivity().getColor(R.color.colorAccent));

        /** put an icon **/
        alertDialogBuilder.setIcon(faIcon);

        /** set title **/
        alertDialogBuilder.setTitle(R.string.privilege_add_title);

        /** set dialog message **/
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // add the privilege in the database
                        /*privilege.setOwnerID(session.loadUserID());
                        privilege.setOrgID(session.loadOrgID());
                        privilege.setGroupBITS(
                                session.loadPrimaryRole());
                        privilege.setName(editTextPrivilegeName.getText().toString());
                        privilege.setDescription(editTextPrivilegeDescription.getText().toString());
                        privilege.setPermsBITS(session.OWNER);
                        privilege.setStatusBITS(session.ACTIVATED);
                        Date timestamp = new Date();
                        privilege.setCreatedDate(timestamp);
                        privilege.setModifiedDate(timestamp);
                        privilege.setSyncedDate(timestamp);*/

                        /** call the create privilege task **/
                        new cCreatePrivilegeTask().execute(privilege);

                        Log.d(TAG, gson.toJson(privilege));

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
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

    /**
     * create new privilege task for adding privilege to the database
     **/
    class cCreatePrivilegeTask extends AsyncTask<cPermissionDomain, Void, Boolean> {

        private cPermissionDomain privilege;
        private boolean result;

        @Override
        protected Boolean doInBackground(cPermissionDomain... cPrivilegeDomains) {
            privilege = cPrivilegeDomains[0];
            result = privilegeHandler.addPrivilege(privilege);

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                /*try {
                    permissionTreeAdapter.notifyTreeInserted(
                            new cTreeModel(permissionTreeAdapter.maxID(), -1, 0, privilege));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }*/
                onResponseMessage(R.string.privilege_add_title, R.string.privilege_add_success);
            } else {
                onResponseMessage(R.string.privilege_add_title, R.string.privilege_add_error);
            }
        }
    }

    /**
     * read roles task for reading database and populate the spinner
     **/
    class cReadRoles extends AsyncTask<Object, Void, ArrayList<cRoleDomain>> {

        private cSingleSpinnerSearch singleSpinnerSearchRole;
        private cPermissionDomain privilege;

        @Override
        protected ArrayList<cRoleDomain> doInBackground(Object... objects) {

            singleSpinnerSearchRole = (cSingleSpinnerSearch) objects[0];
            privilege = (cPermissionDomain) objects[1];

            final ArrayList<cRoleDomain> roles = null;/*roleHandler.getRoleList(
                    session.loadUserID(),          /* loggedIn user
                    session.loadOrgID(),           /* loggedIn own org.
                    session.loadPrimaryRole(),     /* primary group bit
                    session.loadSecondaryRoles()); /* secondary group bits */

            return roles;
        }

        @Override
        protected void onPostExecute(final ArrayList<cRoleDomain> roleDomains) {
            // create a pair list of organization ids and names
            final List<cKeyPairBoolData> keyPairBoolRoles = new ArrayList<>();
            for (int i = 0; i < roleDomains.size(); i++) {
                cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                idNameBool.setId(roleDomains.get(i).getRoleID());
                idNameBool.setName(roleDomains.get(i).getName());
                if (privilege.getPrivilegeID() == roleDomains.get(i).getRoleID()) {
                    idNameBool.setSelected(true);
                    Log.d(TAG, "PRIVILEGE ID : " + privilege.getPrivilegeID());

                } else {
                    idNameBool.setSelected(false);
                }
                keyPairBoolRoles.add(idNameBool);
            }

            // called when click organization single spinner search
            singleSpinnerSearchRole.setItems(keyPairBoolRoles, -1, new cSpinnerListener() {
                @Override
                public void onItemsSelected(List<cKeyPairBoolData> items) {
                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).isSelected()) {
                            for (int j = 0; j < roleDomains.size(); j++) {
                                if (items.get(i).getId() == roleDomains.get(j).getRoleID()) {
                                    privilege.setPrivilegeID((int) items.get(i).getId());
                                    privilege.setOrganizationID(roleDomains.get(j).getOrganizationID());
                                    Log.d(TAG, "PRIVILEGE ID : " + privilege.getPrivilegeID());
                                    break;
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * READING PERMISSION DOMAINS FROM THE DATABASE
     **/

    protected void readPrivileges(int userID, int orgID, int primaryRole, int secondaryRoles) {
        new cReadPrivilegesTask().execute(userID, orgID, primaryRole, secondaryRoles);
    }

    /**
     * read roles task for reading database and populate the spinner
     **/
    class cReadPrivilegesTask extends AsyncTask<Object, Void, Boolean> {
        private int userID;
        private int orgID;
        private int primaryRole;
        private int secondaryRoles;

        @Override
        protected Boolean doInBackground(Object... objects) {
            userID = (int) objects[0];
            orgID = (int) objects[1];
            primaryRole = (int) objects[2];
            secondaryRoles = (int) objects[3];

            permissionTree.clear();
            ArrayList<cTreeModel> treeModels = null;//-permissionHandler.getPermissionTree(userID, orgID, primaryRole, secondaryRoles);

            if (treeModels != null) {
                permissionTree.addAll(treeModels);
            }

            operationDomains.clear();
            operationDomains.addAll(operationHandler.getOperationList());

            statusDomains.clear();
            statusDomains.addAll(statusHandler.getStatusList());

            return (treeModels != null);
        }

        @Override
        protected void onPostExecute(Boolean results) {
            super.onPostExecute(results);
            if (results) {
                try {
                    permissionTreeAdapter.sort(level);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                onResponseMessage(R.string.privilege_read_title, R.string.privilege_read_error);
            }
        }
    }

    /**
     * UPDATING A PRIVILEGE DOMAIN IN THE DATABASE
     **/

    @Override
    public void onUpdatePrivilege(final cTreeModel treeModel) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View formElementsView = inflater.inflate(R.layout.privilege_add_edit_record, null, false);
        // -1 is no by default selection, 0 to length will select corresponding values
        cSingleSpinnerSearch singleSpinnerSearchRole =
                (cSingleSpinnerSearch) formElementsView.findViewById(R.id.appCompatSpinnerRole);

        final AppCompatEditText editTextPrivilegeName =
                (AppCompatEditText) formElementsView.findViewById(R.id.editTextPrivilegeName);
        final AppCompatEditText editTextPrivilegeDescription =
                (AppCompatEditText) formElementsView.findViewById(R.id.editTextPrivilegeDescription);

        final cPermissionDomain privilegeDomain = new cPermissionDomain((cPermissionDomain) treeModel.getModelObject());

        /** read roles to populate the spinner **/
        new cReadRoles().execute(singleSpinnerSearchRole, privilegeDomain);

        //final cPermissionDomain privilegeDomain = (cPermissionDomain) treeModel.getModelObject();

        editTextPrivilegeName.setText(privilegeDomain.getName());
        editTextPrivilegeDescription.setText(privilegeDomain.getDescription());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setView(formElementsView);

        /** setting icon to dialog **/
        TextDrawable faIcon = new TextDrawable(getActivity());
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(cFontManager.getTypeface(getActivity(), cFontManager.FONTAWESOME));
        faIcon.setText(getActivity().getResources().getText(R.string.fa_create));
        faIcon.setTextColor(getActivity().getColor(R.color.colorAccent));

        /** put an icon **/
        alertDialogBuilder.setIcon(faIcon);

        /** set title **/
        alertDialogBuilder.setTitle(R.string.privilege_edit_title);

        /** set dialog message **/
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // update the permissions in the database

                        // add the privilege in the database
                        privilegeDomain.setName(editTextPrivilegeName.getText().toString());
                        privilegeDomain.setDescription(editTextPrivilegeDescription.getText().toString());

                        privilegeDomain.setOwnerID(privilegeDomain.getOwnerID());
                        privilegeDomain.setOrgID(privilegeDomain.getOrgID());
                        privilegeDomain.setGroupBITS(privilegeDomain.getGroupBITS());
                        privilegeDomain.setPermsBITS(privilegeDomain.getPermsBITS());
                        privilegeDomain.setStatusBITS(privilegeDomain.getStatusBITS());
                        Date timestamp = new Date();
                        privilegeDomain.setCreatedDate(privilegeDomain.getCreatedDate());
                        privilegeDomain.setModifiedDate(timestamp);
                        privilegeDomain.setSyncedDate(privilegeDomain.getSyncedDate());

                        /** update the privilege **/
                        new cUpdatePrivilegeTask().execute(privilegeDomain, treeModel);

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
                permissionTreeAdapter.notifyDataSetChanged();
                onResponseMessage(R.string.privilege_edit_title, R.string.privilege_edit_success);
            } else {
                onResponseMessage(R.string.privilege_edit_title, R.string.privilege_edit_error);
            }
        }
    }

    /**
     * DELETE A PRIVILEGE DOMAIN IN THE DATABASE
     **/
    @Override
    public void onDeletePrivilege(cTreeModel treeModel, int position) {
        cPermissionDomain privilegeDomain = (cPermissionDomain) treeModel.getModelObject();

        new cDeletePrivilegeTask().execute(privilegeDomain, treeModel);
    }

    /**
     * delete privilege task for deleting privilege in the database
     **/
    class cDeletePrivilegeTask extends AsyncTask<Object, Void, Boolean> {

        private cPermissionDomain privilegeDomain;
        private cTreeModel treeModel;
        boolean result;

        @Override
        protected Boolean doInBackground(Object... objects) {
            privilegeDomain = (cPermissionDomain) objects[0];
            treeModel = (cTreeModel) objects[1];
            result = privilegeHandler.deletePrivilege(privilegeDomain);
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                /* refresh the tree */
                /*try {
                    permissionTreeAdapter.notifyTreeRemoved(treeModel);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }*/
                onResponseMessage(R.string.privilege_del_title, R.string.privilege_del_success);
            } else {
                onResponseMessage(R.string.privilege_del_title, R.string.privilege_del_error);
            }
        }
    }

    /**
     * SYNC. A PRIVILEGE DOMAIN IN THE DATABASE
     **/
    @Override
    public void onSyncPrivilege(cTreeModel treeModel, int position) {

    }

    /**
     * =========== START PERMISSIONS ===========
     */

    /**
     * add entities task for adding entities to the database
     **/
    @Override
    public void onAddPermissionEntities(cNode node) {
        new cAddPermissionEntitiesTask().execute(node);
    }

    class cAddPermissionEntitiesTask extends AsyncTask<Object, Void, ArrayList<cEntityDomain>> {

        private cNode parentNode;
        private View filterLayout;
        private cPermissionDomain privilegeDomain;
        private ArrayList<cEntityDomain> entities;
        private ArrayList<cEntityDomain> selectedEntities;
        private cOperationDomain operationDomain;
        private cStatusDomain statusDomain;
        private List<cKeyPairBoolData> keyPairBoolEntities = new ArrayList<>();

        @Override
        protected ArrayList<cEntityDomain> doInBackground(Object... objects) {
            parentNode = (cNode) objects[0];

            entities = entityHandler.getEntityList();
            operationDomain = null;//operationHandler.getOperationByID(session.OWNER_READ);
            statusDomain = null;//statusHandler.getStatusByID(session.ACTIVATED);

            return entities;
        }

        @Override
        protected void onPostExecute(final ArrayList<cEntityDomain> entityDomains) {
            cTreeModel parentTreeModel = (cTreeModel) parentNode.getObj();
            privilegeDomain = (cPermissionDomain) parentTreeModel.getModelObject();
            //Log.d(TAG, gson.toJson(privilegeDomain));

            ArrayList<cNode> childNodes = parentNode.getChildren();
            // create a pair list of entity ids and names
            for (int i = 0; i < entityDomains.size(); i++) {
                cKeyPairBoolData idNameBool = new cKeyPairBoolData();

                idNameBool.setId(entityDomains.get(i).getEntityID());
                idNameBool.setName(entityDomains.get(i).getName());
                idNameBool.setSelected(false);
                idNameBool.setObject(entityDomains.get(i));

                for (int j = 0; j < childNodes.size(); j++) {
                    /** get child entity **/
                    cTreeModel childTreeModel = (cTreeModel) childNodes.get(j).getObj();
                    //cPermissionTreeDomain permissionTreeDomain = (cPermissionTreeDomain) childTreeModel.getModelObject();
                    cEntityDomain entityDomain = null;//permissionTreeDomain.getEntityDomain();

                    if ((entityDomain.getEntityID() == entityDomains.get(i).getEntityID()) &&
                            (entityDomain.getTypeID() == entityDomains.get(i).getTypeID())) {
                        idNameBool.setSelected(true);
                        break;
                    }
                }

                keyPairBoolEntities.add(idNameBool);
            }

            // create a spinner for filtering purposes spinner
            LayoutInflater inflater = getActivity().getLayoutInflater();
            filterLayout = inflater.inflate(R.layout.entity_filter, null);

            /***
             * -1 is no by default selection
             * 0 to length will select corresponding values
             */
            cMultiSpinnerSearch searchMultiSpinnerUnlimited =
                    (cMultiSpinnerSearch) filterLayout.findViewById(R.id.multiSpinnerSearchEnties);

            /** create permission domains **/
            onCreateFilteredPermissions(R.style.AnimateLeftRight, R.string.privilege_filter_add_title,
                    filterLayout);

            // called when click spinner
            searchMultiSpinnerUnlimited.setItems(keyPairBoolEntities, -1, new cSpinnerListener() {
                @Override
                public void onItemsSelected(List<cKeyPairBoolData> items) {
                    selectedEntities = new ArrayList<>();
                    for (int i = 0; i < items.size(); i++) {
                        cEntityDomain entity = (cEntityDomain) items.get(i).getObject();
                        if (items.get(i).isSelected()) {
                            if (!selectedEntities.contains(entity)) {
                                selectedEntities.add(entity);
                            }
                        } else if (!items.get(i).isSelected()) {
                            if (selectedEntities.contains(entity)) {
                                selectedEntities.remove(i);
                            }
                        }
                    }

                    /** create new permission object **/
                    selectedPermissions = new ArrayList<>();

                    for (int j = 0; j < selectedEntities.size(); j++) {
                        cPermissionDomain permissionDomain = new cPermissionDomain();

                        //permissionDomain.setOrganizationID(session.loadOrgID());
                        //-permissionDomain.setPrivilegeDomain(privilegeDomain);
                        //-permissionDomain.setEntityDomain(selectedEntities.get(j));
                        //-permissionDomain.setOperationDomain(operationDomain);
                        //-permissionDomain.setStatusDomain(statusDomain);
                        //permissionDomain.setOwnerID(session.loadUserID());
                        //permissionDomain.setOrgID(session.loadOrgID());
                        //permissionDomain.setGroupBITS(session.loadPrimaryRole());
                        //permissionDomain.setPermsBITS(session.READ);
                        //permissionDomain.setStatusBITS(session.ACTIVATED);

                        Date timestamp = new Date();
                        permissionDomain.setCreatedDate(timestamp);
                        permissionDomain.setModifiedDate(timestamp);
                        permissionDomain.setSyncedDate(timestamp);

                        selectedPermissions.add(permissionDomain);
                    }
                }
            });
        }
    }

    private void onCreateFilteredPermissions(int animationSource, int title, View filterLayout) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        /** setting icon to dialog **/
        TextDrawable faIcon = new TextDrawable(getActivity());
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(cFontManager.getTypeface(getActivity(), cFontManager.FONTAWESOME));
        faIcon.setText(getActivity().getResources().getText(R.string.fa_filter));
        faIcon.setTextColor(getActivity().getColor(R.color.colorAccent));
        builder.setIcon(faIcon);

        builder.setTitle(title);
        builder.setView(filterLayout);
        builder.setPositiveButton(
                "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        /** create filtered permissions **/
                        new cCreatePermissionsTask().execute(selectedPermissions);
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

    /**
     * create permissions
     **/
    class cCreatePermissionsTask extends AsyncTask<Object, Void, Boolean> {

        private ArrayList<cPermissionDomain> permissions;
        private ArrayList<cTreeModel> treeModels;

        private boolean result;

        @Override
        protected Boolean doInBackground(Object... objects) {
            permissions = (ArrayList<cPermissionDomain>) objects[0];
            for (int i = 0; i < permissions.size(); i++) {
                result = true;//-permissionHandler.addPermission(permissions.get(i));
                if (!result) {
                    return false;
                }
            }

            /** clear adapter for refreshing purposes **/
            permissionTree.clear();
            //-treeModels = permissionHandler.getPermissionTree(
            //-        session.loadUserID(), session.loadOrganizationID(),
            //-        session.loadPrimaryRole(session.loadUserID(), session.loadOrganizationID()),
            //-        session.loadSecondaryRoles(session.loadUserID(), session.loadOrganizationID()));

            if (treeModels != null) {
                permissionTree.addAll(treeModels);
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                try {
                    permissionTreeAdapter.sort(level);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                onResponseMessage(R.string.privilege_add_title, R.string.privilege_add_success);
            } else {
                onResponseMessage(R.string.privilege_add_title, R.string.privilege_add_error);
            }
        }
    }


    /**
     * remove entities task for removing entities from the database
     **/
    @Override
    public void onRemovePermissionEntities(cNode node) {
        new cRemovePermissionEntitiesTask().execute(node);
    }

    class cRemovePermissionEntitiesTask extends AsyncTask<Object, Void, ArrayList<cEntityDomain>> {

        private cNode parentNode;
        private cTreeModel parentTreeModel;
        private cPermissionDomain privilegeDomain;
        private ArrayList<cEntityDomain> selectedEntities;
        private View filterLayout;
        private ArrayList<cEntityDomain> entities;

        @Override
        protected ArrayList<cEntityDomain> doInBackground(Object... objects) {
            parentNode = (cNode) objects[0];
            entities = entityHandler.getEntityList();

            return entities;
        }

        @Override
        protected void onPostExecute(final ArrayList<cEntityDomain> entityDomains) {
            ArrayList<cNode> childNodes = parentNode.getChildren();

            parentTreeModel = (cTreeModel) parentNode.getObj();
            privilegeDomain = (cPermissionDomain) parentTreeModel.getModelObject();

            // create a pair list of entity ids and names
            List<cKeyPairBoolData> keyPairBoolEntities = new ArrayList<>();
            for (int i = 0; i < entityDomains.size(); i++) {
                cKeyPairBoolData idNameBool = new cKeyPairBoolData();

                idNameBool.setId(entityDomains.get(i).getEntityID());
                idNameBool.setName(entityDomains.get(i).getName());
                idNameBool.setObject(entityDomains.get(i));
                idNameBool.setSelected(false);

                for (int j = 0; j < childNodes.size(); j++) {
                    /** get child entity **/
                    cTreeModel treeModel = (cTreeModel) childNodes.get(j).getObj();
                    //cPermissionTreeDomain permissionTreeDomain = (cPermissionTreeDomain)
                            //treeModel.getModelObject();
                    cEntityDomain entityDomain = null;//permissionTreeDomain.getEntityDomain();

                    if (entityDomain.getEntityID() == entityDomains.get(i).getEntityID() &&
                            entityDomain.getTypeID() == entityDomains.get(i).getTypeID()) {
                        idNameBool.setSelected(true);
                        break;
                    }
                }

                keyPairBoolEntities.add(idNameBool);
            }

            // create a spinner for filtering purposes spinner
            LayoutInflater inflater = getActivity().getLayoutInflater();
            filterLayout = inflater.inflate(R.layout.entity_filter, null);


            /***
             * -1 is no by default selection
             * 0 to length will select corresponding values
             */
            cMultiSpinnerSearch searchMultiSpinnerUnlimited =
                    (cMultiSpinnerSearch) filterLayout.findViewById(R.id.multiSpinnerSearchEnties);

            /** build dialog **/
            onDeleteFilteredPermissions(R.style.AnimateLeftRight, R.string.privilege_filter_add_title,
                                filterLayout);

            // called when click spinner
            searchMultiSpinnerUnlimited.setItems(keyPairBoolEntities, -1, new cSpinnerListener() {
                @Override
                public void onItemsSelected(List<cKeyPairBoolData> items) {
                    selectedEntities = new ArrayList<>();
                    for (int i = 0; i < items.size(); i++) {
                        cEntityDomain entity = (cEntityDomain) items.get(i).getObject();

                        if (items.get(i).isSelected()) {
                            if (!selectedEntities.contains(entity)) {
                                selectedEntities.add(entity);
                            }
                        } else if (!items.get(i).isSelected()) {
                            if (selectedEntities.contains(entity)) {
                                selectedEntities.remove(entity);
                            }
                        }
                    }

                    selectedPermissions = new ArrayList<>();

                    /** create list of permission domains **/
                    for (int j = 0; j < selectedEntities.size(); j++) {
                        cPermissionDomain permissionDomain = new cPermissionDomain();

                        //-permissionDomain.setPrivilegeDomain(privilegeDomain);
                        //-permissionDomain.setEntityDomain(selectedEntities.get(j));

                        selectedPermissions.add(permissionDomain);
                    }
                }
            });
        }
    }

    private void onDeleteFilteredPermissions(int animationSource, int title, View filterLayout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        /** setting icon to dialog **/
        TextDrawable faIcon = new TextDrawable(getActivity());
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(cFontManager.getTypeface(getActivity(), cFontManager.FONTAWESOME));
        faIcon.setText(getActivity().getResources().getText(R.string.fa_filter));
        faIcon.setTextColor(getActivity().getColor(R.color.colorAccent));
        builder.setIcon(faIcon);

        builder.setTitle(title);
        builder.setView(filterLayout);
        builder.setPositiveButton(
                "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // delete permission domains
                        new cDeletePermissionsTask().execute(selectedPermissions);
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dismiss
            }});

        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = animationSource;
        builder.show();
    }

    class cDeletePermissionsTask extends AsyncTask<Object, Void, Boolean> {

        private ArrayList<cPermissionDomain> permissionDomains;
        private ArrayList<cTreeModel> treeModels;
        private boolean result;

        @Override
        protected Boolean doInBackground(Object... objects) {
            permissionDomains = (ArrayList<cPermissionDomain>) objects[0];
            for (int i = 0; i < permissionDomains.size(); i++) {
                //-int orgID = permissionDomains.get(i).getPrivilegeDomain().getOrganizationID();
                //-int privilegeID = permissionDomains.get(i).getPrivilegeDomain().getPrivilegeID();
                //-int entityID = permissionDomains.get(i).getEntityDomain().getEntityID();
                //-int typeID = permissionDomains.get(i).getEntityDomain().getTypeID();

                //-result = permissionHandler.deletePermissionByEntityIDs(orgID, privilegeID, entityID, typeID);
                if (!result) {
                    return false;
                }

            }

            /** clear adapter for refreshing purposes **/
            permissionTree.clear();
            //-treeModels = permissionHandler.getPermissionTree(
            //-        session.loadUserID(), session.loadOrganizationID(),
            //-        session.loadPrimaryRole(session.loadUserID(), session.loadOrganizationID()),
            //-        session.loadSecondaryRoles(session.loadUserID(), session.loadOrganizationID()));

            if (treeModels != null) {
                permissionTree.addAll(treeModels);
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                /* refresh the tree */
                try {
                    permissionTreeAdapter.sort(level);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                onResponseMessage(R.string.privilege_del_title, R.string.privilege_del_success);
            } else {
                onResponseMessage(R.string.privilege_del_title, R.string.privilege_del_error);
            }
        }
    }

    /**
     * =========== END PERMISSIONS ===========
     **/

    @Override
    public void onResponseMessage(final int titleID, final int messageID) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());

                // setting dialog title
                alertDialog.setTitle(titleID);

                // setting dialog message
                alertDialog.setMessage(messageID);

                // setting icon to dialog
                TextDrawable faIcon = new TextDrawable(getContext());
                faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                faIcon.setTypeface(cFontManager.getTypeface(getContext(), cFontManager.FONTAWESOME));
                faIcon.setText(getContext().getResources().getText(R.string.fa_exclamation_triangle));
                faIcon.setTextColor(getActivity().getColor(R.color.colorAccent));

                alertDialog.setIcon(faIcon);

                // setting OK button
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // go to main menu
                        pushFragment(cPermissionFragment.this);
                        dialog.cancel();
                    }
                });

                // modal mode
                alertDialog.setCancelable(false);

                // showing alert message
                alertDialog.show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        toolBar.inflateMenu(R.menu.menu_main);
        toolBarMenu = toolBar.getMenu();

        toolBarMenuItem = toolBarMenu.findItem(R.id.homeItem);

        toolBar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        SearchView searchView = (SearchView) toolBarMenu.findItem(R.id.searchItem).getActionView();

        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeItem:
                //pushFragment(cLogFrameFragment.newInstance(session));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //roleUserTreeAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    protected void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (ft != null) {
            ft.replace(R.id.fragment_frame, fragment);
            ft.commit();
        }
    }
}