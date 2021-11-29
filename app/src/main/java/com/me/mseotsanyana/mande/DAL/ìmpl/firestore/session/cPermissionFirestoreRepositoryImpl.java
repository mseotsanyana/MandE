package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOperationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOperationStatusCollection;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cSectionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUnixOperationCollection;
import com.me.mseotsanyana.mande.BLL.model.session.cUnixOperationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class cPermissionFirestoreRepositoryImpl implements iPermissionRepository {
    private static final String TAG = cPermissionFirestoreRepositoryImpl.class.getSimpleName();

    private static final int MENUITEM_SECTION     = 1;
    private static final int ENTITYMODULE_SECTION = 4;
    private static final int ENTITY_OPS_SECTION   = 6;
    private static final int UNIX_OPS_SECTION     = 9;

    private final Context context;
    private final FirebaseFirestore db;

    public cPermissionFirestoreRepositoryImpl(Context context) {
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    /* ###################################### READ ACTIONS ###################################### */

    @Override
    public void readRolePermissions(String organizationServerID, String userServerID,
                                    int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                    List<Integer> statusBITS, iReadRolePermissionsCallback callback) {

        CollectionReference coRoleRef = db.collection(cRealtimeHelper.KEY_ROLES);

        Query roleQuery = coRoleRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);

        roleQuery.get()
                .addOnCompleteListener(task -> {
                    //List<String> role_ids = new ArrayList<>();
                    Map<String, cRoleModel> roleModelMap = new HashMap<>();
                    cRoleModel roleModel;
                    for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
                        roleModel = role_doc.toObject(cRoleModel.class);

                        if (roleModel != null) {
                            roleModel.setRoleServerID(role_doc.getId());

                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                            perm.setUserOwnerID(roleModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(roleModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(roleModel.getUnixpermBITS());

                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
                                    secondaryTeamBITS)) {
                                //role_ids.add(roleModel.getRoleServerID());
                                roleModelMap.put(roleModel.getRoleServerID(), roleModel);
                            }
                        }
                    }

                    /* filtering the permissions by roles */
                    filterRolePermissions(roleModelMap, callback);
                })
                .addOnFailureListener(e ->
                        callback.onReadRolePermissionsFailed("Failed to read roles"));

    }

    private void filterRolePermissions(Map<String, cRoleModel> roleModelMap,
                                       iReadRolePermissionsCallback callback) {
        CollectionReference coPermissionRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
        List<String> role_ids = new ArrayList<>(roleModelMap.keySet());
        Query permissionQuery = coPermissionRef.whereIn(FieldPath.documentId(), role_ids);

        permissionQuery.get()
                .addOnCompleteListener(task -> {
                    cRoleModel roleModel;
                    cPermissionModel permissionModel;
                    List<cTreeModel> treeModels = new ArrayList<>();
                    for (DocumentSnapshot perm_doc : Objects.requireNonNull(task.getResult())) {

                        permissionModel = perm_doc.toObject(cPermissionModel.class);

                        if (permissionModel != null) {
                            permissionModel.setRoleServerID(perm_doc.getId());
                            for (Map.Entry<String, cRoleModel> entry : roleModelMap.entrySet()) {
                                if (entry.getKey().equals(permissionModel.getRoleServerID())) {
                                    // FIXME: what about if the user has list of multiple roles
                                    //  and permissions
                                    roleModel = entry.getValue();
                                    roleModel.setRoleServerID(entry.getKey());
                                    permissionModel.setRoleServerID(perm_doc.getId());
                                    permissionModel.setName(roleModel.getName());

                                    //Log.d(TAG, "RESULT = " + gson.toJson(permissionModel));

                                    // add maps of menu items and entities not in the db
                                    // return all menus
                                    treeModels = cDatabaseUtils.buildRolePermissions(context,
                                            permissionModel);

                                    break;
                                }
                            }
                        }
                    }

                    /* call back on list of user permissions by roles */
                    callback.onReadRolePermissionsSucceeded(treeModels);
                })
                .addOnFailureListener(e ->
                        callback.onReadRolePermissionsFailed("Failed to read permissions"));
    }

    private void realtimeReadRolePermissions(){
        CollectionReference coPermissionRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
        coPermissionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.d(TAG, "ERROR");
                }

                if(value != null){
                    Log.d(TAG, "================= IT WORKED ================");
                }
            }
        });
    }


    /* ##################################### UPDATE ACTIONS ##################################### */

    @Override
    public void updateRolePermissions(String organizationServerID, String userServerID,
                                      int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                      List<Integer> statusBITS, List<cNode> nodes,
                                      iUpdateRolePermissionsCallback callback) {
        /* find the root of the permission tree */
        cNode root = null;
        for (cNode node : nodes) {
            if (node.isRoot()) {
                root = node;
                break;
            }
        }

        /* use the root to build the tree of the selected permissions */
        cPermissionModel permissionModel = null;
        if (root != null) {
            permissionModel = updatePermissionTree(root);
        }

        /* update permissions in the cloud */
        if (permissionModel != null) {
            CollectionReference coRolePermsRef = db.collection(
                    cRealtimeHelper.KEY_ROLE_PERMISSIONS);
            coRolePermsRef.document(permissionModel.getRoleServerID()).set(permissionModel)
                    .addOnSuccessListener(unused -> callback.onUpdateRolePermissionsSucceeded(
                            "Permissions successfully updated"))
                    .addOnFailureListener(e -> callback.onUpdateRolePermissionsFailed(
                            "Error! Failed to update permissions "));

        }
    }

    /**
     * update the role permission which contains menu items and entity modules
     *
     * @param node root of the permission tree
     * @return root node
     */
    private cPermissionModel updatePermissionTree(cNode node) {
        cPermissionModel permissionModel = new cPermissionModel();
        cTreeModel permTreeModel = (cTreeModel) node.getObj();
        cPermissionModel perm = (cPermissionModel) permTreeModel.getModelObject();

        /* update the permission model */
        permissionModel.setRoleServerID(perm.getRoleServerID());
        permissionModel.setName(perm.getName());
        permissionModel.setDescription(perm.getDescription());

        Map<String, List<Integer>> menuitems = new HashMap<>();
        Map<String, List<cEntityModel>> entitymodules = new HashMap<>();
        for (cNode module : node.getChildren()) {
            cTreeModel permSectionTreeModel = (cTreeModel) module.getObj();
            cSectionModel sectionModel = (cSectionModel) permSectionTreeModel.getModelObject();

            /* MENU ITEM SECTION */

            if(permSectionTreeModel.getType() == MENUITEM_SECTION){

                for(cNode mainmenu: module.getChildren()) {

                    cTreeModel mainmenuTreeModel = (cTreeModel) mainmenu.getObj();
                    cMenuModel menuModel = (cMenuModel) mainmenuTreeModel.getModelObject();

                    List<Integer> submenu_ids = new ArrayList<>();
                    if (menuModel.isChecked()){
                        for (cNode submenu: mainmenu.getChildren()){
                            cTreeModel submenuTreeModel = (cTreeModel) submenu.getObj();
                            cMenuModel submenuModel = (cMenuModel)
                                    submenuTreeModel.getModelObject();
                            if(submenuModel.isChecked()) {
                                submenu_ids.add(submenuModel.getMenuServerID());
                            }
                        }
                        menuitems.put(String.valueOf(menuModel.getMenuServerID()), submenu_ids);
                    }
                }
            }

            /* ENTITY MODULE SECTION */

            if (permSectionTreeModel.getType() == ENTITYMODULE_SECTION ) {

                List<cEntityModel> entityModels = new ArrayList<>();
                for (cNode entity : module.getChildren()) {
                    cTreeModel entityTreeModel = (cTreeModel) entity.getObj();
                    cEntityModel entityModel = (cEntityModel) entityTreeModel.getModelObject();

                    /* is the entity selected? */
                    if (entityModel.isChecked()) {
                        Map<String, List<Integer>> entityperms = new HashMap<>();
                        List<Integer> unixperms = new ArrayList<>();
                        for (cNode section : entity.getChildren()) {
                            cTreeModel sectionTreeModel = (cTreeModel) section.getObj();

                            /* ENTITY OPERATIONS SECTION */

                            if (sectionTreeModel.getType() == ENTITY_OPS_SECTION) {

                                for (cNode operation : section.getChildren()) {
                                    cTreeModel operationTreeModel = (cTreeModel) operation.getObj();
                                    cOperationModel operationModel = (cOperationModel)
                                            operationTreeModel.getModelObject();

                                    /* is the entity operation selected? */
                                    if (operationModel.isChecked()) {

                                        for (cNode entity_op : operation.getChildren()) {
                                            cTreeModel entityOpTreeModel;
                                            entityOpTreeModel = (cTreeModel) entity_op.getObj();
                                            cOperationStatusCollection collection;
                                            collection = (cOperationStatusCollection)
                                                    entityOpTreeModel.getModelObject();

                                            List<Integer> statuses = new ArrayList<>();
                                            for (cStatusModel statusModel : collection.
                                                    getStatusCollection()) {
                                                /* are the operation status selected? */
                                                if (statusModel.isChecked()) {
                                                    statuses.add(Integer.parseInt(
                                                            statusModel.getStatusServerID()));
                                                }
                                            }

                                            entityperms.put(operationModel.getOperationServerID(),
                                                    statuses);
                                        }
                                    }
                                }
                            }

                            /* UNIX OPERATIONS SECTION */

                            if (sectionTreeModel.getType() == UNIX_OPS_SECTION) {
                                for (cNode unix_ops : section.getChildren()) {
                                    cTreeModel unixTreeModel = (cTreeModel) unix_ops.getObj();
                                    cUnixOperationCollection collection = (cUnixOperationCollection)
                                            unixTreeModel.getModelObject();

                                    for (cUnixOperationModel unix : collection.getUnixOperationModels()) {
                                        /* are unix permissions selected? */
                                        if (unix.isChecked()) {
                                            unixperms.add(Integer.parseInt(unix.getOperationServerID()));
                                        }
                                    }
                                }
                            }

                        }
                        // create entity model
                        entityModels.add(new cEntityModel(entityModel.getEntityServerID(),
                                entityperms, unixperms));
                    }
                }

                entitymodules.put(sectionModel.getModuleServerID(), entityModels);
            }
        }

        // update the menu items
        permissionModel.setMenuitems(menuitems);
        // update the entity modules
        permissionModel.setEntitymodules(entitymodules);

        return permissionModel;
    }


    /**
     * save user permissions locally for quick access control to the system resources
     *
     * @param callback call back
     */
    @Override
    public void saveUserPermissions(iSaveUserPermissionsCallback callback) {

        String userServerID = FirebaseAuth.getInstance().getUid();

        CollectionReference coUserAccountsRef = db.collection(cRealtimeHelper.KEY_USERACCOUNTS);
        Query userAccountQuery = coUserAccountsRef
                .whereEqualTo("userServerID", userServerID)
                .whereEqualTo("currentOrganization", true);

        userAccountQuery.get()
                .addOnCompleteListener(task -> {
                    /* find the current account of the logged in user */
                    String userAccountID = null, organizationID = null, primaryTeamID = null;
                    for (DocumentSnapshot useraccount : Objects.requireNonNull(task.getResult())) {
                        cUserAccountModel userAccountModel;
                        userAccountModel = useraccount.toObject(cUserAccountModel.class);
                        if (userAccountModel != null && userAccountModel.isCurrentOrganization()) {
                            userAccountID = userAccountModel.getUserAccountServerID();
                            organizationID = userAccountModel.getOrganizationServerID();
                            primaryTeamID = userAccountModel.getTeamServerID();
                            break;
                        }
                    }

                    /* save ownerID, organizationServerID, primaryTeamID and filter secondary teams,
                       otherwise clear preferences and load default login details */
                    if (userAccountID != null && organizationID != null && primaryTeamID != null) {
                        /* call back on saving logged in identification */
                        callback.onSaveOwnerID(userServerID);
                        /* call back on saving organization identification FIXME: save org name as well */
                        callback.onSaveOrganizationServerID(organizationID);
                        /* call back on saving primary bit */
                        callback.onSavePrimaryTeamBIT(Integer.parseInt(primaryTeamID));

                        filterSecondaryTeams(userAccountID, primaryTeamID, callback);
                    } else {
                        callback.onClearPreferences();
                        callback.onLoadDefaultSettings(
                                "No user account found! Loading default settings...");
                    }
                })
                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
                        "Failed due to user account entity!"));
    }

    /**
     * This filters secondary teams bits of the loggedIn user and saves it.
     *
     * @param accountServerID user account identification
     * @param primaryTeamID   primary team identification
     * @param callback        call back
     */
    public void filterSecondaryTeams(String accountServerID, String primaryTeamID,
                                     iSaveUserPermissionsCallback callback) {

        CollectionReference coTeamMembersRef = db.collection(cRealtimeHelper.KEY_TEAM_MEMBERS);

        Query teamMemberQuery = coTeamMembersRef
                .whereEqualTo("userAccountServerID", accountServerID);

        teamMemberQuery.get()
                .addOnCompleteListener(task -> {
                    Set<String> team_id_set = new HashSet<>();
                    for (DocumentSnapshot team : Objects.requireNonNull(task.getResult())) {
                        String teamID = Objects.requireNonNull(team.get("teamMemberServerID")).
                                toString();
                        team_id_set.add(teamID);
                    }

                    if (!team_id_set.isEmpty()) {
                        List<String> team_ids = new ArrayList<>(team_id_set);
                        filterTeams(team_ids, primaryTeamID, callback);
                    } else {
                        callback.onClearPreferences();
                        callback.onLoadDefaultSettings(
                                "Not a member of any team! Loading default settings...");
                    }
                })
                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
                        "Failed due to team membership!"));
    }

    /**
     * read the teams of the loggedIn user
     *
     * @param team_ids      team identifications
     * @param primaryTeamID primary team identification
     * @param callback      call back
     */
    private void filterTeams(List<String> team_ids, String primaryTeamID,
                             iSaveUserPermissionsCallback callback) {

        CollectionReference coTeamsRef = db.collection(cRealtimeHelper.KEY_TEAMS);

        Query teamQuery = coTeamsRef
                .whereIn("compositeServerID", team_ids);

        teamQuery.get()
                .addOnCompleteListener(task -> {
                    Set<Integer> secondary_team_set = new HashSet<>();
                    for (DocumentSnapshot team : Objects.requireNonNull(task.getResult())) {
                        String teamID = Objects.requireNonNull(team.get("teamServerID")).toString();
                        secondary_team_set.add(Integer.parseInt(teamID));
                    }

                    /* remove primary team if it is also in the set */
                    secondary_team_set.remove(Integer.parseInt(primaryTeamID));

                    /* call back on saving secondary team bits */
                    List<Integer> secondaryTeams = new ArrayList<>(secondary_team_set);
                    if (secondaryTeams.isEmpty()) {
                        secondaryTeams.add(0);
                    }

                    callback.onSaveSecondaryTeams(secondaryTeams);

                    // Team Roles - this is to get roles for extracting
                    // menu, entity and unix permissions
                    filterTeamsRoles(team_ids, callback);
                })
                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
                        "Failed due to team entity!"));
    }

    /**
     * read roles of the teams the user belongs to
     *
     * @param team_ids team identifications
     * @param callback call back
     */
    private void filterTeamsRoles(List<String> team_ids, iSaveUserPermissionsCallback
            callback) {
        CollectionReference coTeamsRolesRef = db.collection(cRealtimeHelper.KEY_TEAM_ROLES);

        Query roleQuery = coTeamsRolesRef
                .whereIn("teamServerID", team_ids);

        roleQuery.get()
                .addOnCompleteListener(task -> {
                    Set<String> role_id_set = new HashSet<>();
                    for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
                        String roleID = Objects.requireNonNull(role_doc.get("roleServerID")).
                                toString();
                        role_id_set.add(roleID);
                    }

                    if (!role_id_set.isEmpty()) {
                        List<String> role_ids = new ArrayList<>(role_id_set);
                        // filter roles permissions for saving purposes
                        filterRolesPermissions(role_ids, callback);
                    } else {
                        callback.onClearPreferences();
                        callback.onLoadDefaultSettings(
                                "No role(s) assigned to your team(s)! " +
                                        "Loading default settings...");
                    }
                })
                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
                        "Failed due to team roles!"));
    }


    /**
     * filter roles for permissions - both entity and unix permissions
     *
     * @param roles_ids role identifications
     * @param callback  call back
     */
    private void filterRolesPermissions(List<String> roles_ids,
                                        iSaveUserPermissionsCallback callback) {
        CollectionReference coRolePermsRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);

        Query permissionQuery = coRolePermsRef.whereIn(FieldPath.documentId(), roles_ids);

        permissionQuery.get()
                .addOnCompleteListener(task -> {

                    QuerySnapshot perm_snapshot = task.getResult();

                    if (perm_snapshot != null) {
                        Map<String, List<Integer>> menuitems = new HashMap<>();
                        Map<String, List<cEntityModel>> entitymodules = new HashMap<>();
                        for (DocumentSnapshot perm_doc : perm_snapshot.getDocuments()) {
                            cPermissionModel permissionModel = perm_doc.toObject(
                                    cPermissionModel.class);
                            if (permissionModel != null) {

                                // add all role menu item permissions to a map
                                menuitems.putAll(permissionModel.getMenuitems());

                                // add all role entity and unix permissions to a map
                                entitymodules.putAll(permissionModel.getEntitymodules());

                            } else {
                                callback.onClearPreferences();
                                callback.onLoadDefaultSettings(
                                        "No permission(s) assigned to your role(s)! " +
                                                "Loading default settings...");
                            }
                        }

                        // save menu item permissions
                        saveMenuPermissions(menuitems, callback);
                        // save entity and unix permissions
                        saveEntityAndUnixPermissions(entitymodules, callback);
                    } else {
                        callback.onClearPreferences();
                        callback.onLoadDefaultSettings(
                                "No permission(s) assigned to your role(s)! " +
                                        "Loading default settings...");
                        return;
                    }

                    callback.onSaveUserPermissionsSucceeded("successfully logged in");
                })
                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
                        "Failed due to permissions!"));
    }

    /**
     * save user menu permissions
     *
     * @param menuitems a map of main menu items with their sub menu items
     * @param callback  call back
     */
    private void saveMenuPermissions(Map<String, List<Integer>> menuitems,
                                     iSaveUserPermissionsCallback callback) {
        // build menu models from json data
        List<cMenuModel> menu_items = cDatabaseUtils.getMenuModelSet(context, menuitems);
        // save menu models to the shared preferences
        callback.onSaveMenuItems(menu_items);
    }

    /**
     * save user permissions
     *
     * @param entitymodules a map of modules with their entities
     * @param callback      call back
     */
    private void saveEntityAndUnixPermissions(Map<String, List<cEntityModel>> entitymodules,
                                              iSaveUserPermissionsCallback callback) {
        /* module entities */
        String moduleID;
        //Map<String, List<cEntityModel>> entitymodules;
        //entitymodules = permissionModel.getEntitymodules();
        for (Map.Entry<String, List<cEntityModel>> module_entities : entitymodules.entrySet()) {
            moduleID = module_entities.getKey();

            /* entities */
            List<cEntityModel> entities = module_entities.getValue();
            String entityID;
            int entityBITS = 0;
            for (cEntityModel entity : entities) {
                entityID = entity.getEntityServerID();
                entityBITS |= Integer.parseInt(entityID);

                /* entity permissions */
                Map<String, List<Integer>> entityperms = entity.getEntityperms();
                String operationID;
                int operationBITS = 0;
                for (Map.Entry<String, List<Integer>> ops : entityperms.entrySet()) {
                    operationID = ops.getKey();
                    operationBITS |= Integer.parseInt(ops.getKey());
                    /* statuses */
                    List<Integer> statuses = new ArrayList<>(ops.getValue());

                    // call back on saving status bits
                    callback.onSaveStatusBITS(moduleID, entityID, operationID,
                            statuses);
                }
                // call back on saving entity permission bits
                callback.onSaveEntityPermBITS(moduleID, entityID, operationBITS);

                // unix permissions
                List<Integer> unixperms = entity.getUnixperms();
                int unixpermBITS = 0;
                for (Integer unix_perm : unixperms) {
                    unixpermBITS |= unix_perm;
                }
                // call back on saving unix permission bits
                callback.onSaveUnixPermBITS(moduleID, entityID, unixpermBITS);
            }
            // call back on saving entity bits
            callback.onSaveEntityBITS(moduleID, entityBITS);
        }
    }
}

//@Override
//    public void readModulePermissions(String organizationServerID, String userServerID,
//                                      int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                                      List<Integer> statusBITS,
//                                      iReadModulePermissionsCallback callback) {
//
//    }
//
//
//    /**
//     * read user permissions
//     *
//     * @param organizationServerID organization identification
//     * @param userServerID         user identification
//     * @param primaryTeamBIT       primary team bit
//     * @param secondaryTeamBITS    secondary team bits
//     * @param statusBITS           status bits
//     * @param callback             call back
//     */
//    public void readUserPermissions(String organizationServerID, String userServerID,
//                                    int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                                    List<Integer> statusBITS,
//                                    iReadUserPermissionsCallback callback) {
//
//        CollectionReference coRoleRef = db.collection(cRealtimeHelper.KEY_ROLES);
//
//        Query roleQuery = coRoleRef
//                .whereEqualTo("organizationOwnerID", organizationServerID)
//                .whereIn("statusBIT", statusBITS);
//
//        roleQuery.get()
//                .addOnCompleteListener(task -> {
//                    //List<String> role_ids = new ArrayList<>();
//                    Map<String, cRoleModel> roleModelMap = new HashMap<>();
//                    cRoleModel roleModel;
//                    for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
//                        roleModel = role_doc.toObject(cRoleModel.class);
//
//                        if (roleModel != null) {
//                            roleModel.setRoleServerID(role_doc.getId());
//
//                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
//                            perm.setUserOwnerID(roleModel.getUserOwnerID());
//                            perm.setTeamOwnerBIT(roleModel.getTeamOwnerBIT());
//                            perm.setUnixpermBITS(roleModel.getUnixpermBITS());
//
//                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                                    secondaryTeamBITS)) {
//                                //role_ids.add(roleModel.getRoleServerID());
//                                roleModelMap.put(roleModel.getRoleServerID(), roleModel);
//                            }
//                        }
//                    }
//
//                    /* filtering the permissions by roles */
//                    filterRolePermissions(roleModelMap, callback);
//                })
//                .addOnFailureListener(e ->
//                        callback.onReadUserPermissionsFailed("Failed to read roles"));
//    }

//    /**
//     * filter user permissions by roles
//     *
//     * @param roleModelMap map of role identification and corresponding models
//     * @param callback     call back
//     */
//    private void filterRolePermissions(Map<String, cRoleModel> roleModelMap,
//                                       iReadUserPermissionsCallback callback) {
//
//        CollectionReference coPermissionRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
//        List<String> role_ids = new ArrayList<>(roleModelMap.keySet());
//        Query permissionQuery = coPermissionRef.whereIn(FieldPath.documentId(), role_ids);
//
//        permissionQuery.get()
//                .addOnCompleteListener(task -> {
//                    cRoleModel roleModel = null;
//                    cPermissionModel permissionModel = null;
//                    for (DocumentSnapshot perm_doc : Objects.requireNonNull(task.getResult())) {
//
//                        permissionModel = perm_doc.toObject(cPermissionModel.class);
//
//                        if (permissionModel != null) {
//                            permissionModel.setRoleServerID(perm_doc.getId());
//                            for (Map.Entry<String, cRoleModel> entry : roleModelMap.entrySet()) {
//                                //Log.d(TAG, " Role Model = "+entry.getKey()+" = "+permissionModel.getRoleServerID());
//                                if (entry.getKey().equals(permissionModel.getRoleServerID())) {
//
//                                    // rolePermissionModels.put(entry.getValue(), permissionModel);
//                                    // FIXME: what about if the user has list of multiple roles
//                                    //  and permissions
//                                    roleModel = entry.getValue();
//                                    roleModel.setRoleServerID(entry.getKey());
//
//                                    permissionModel.setName(roleModel.getName());
//                                    break;
//                                }
//                            }
//                        }
//                    }
//
//                    // add maps of menu items and entities not in the db
//                    // return all menus
//                    List<cTreeModel> treeModels = new ArrayList<>();
//                    if(roleModel != null) {
//
//                        //Log.d(TAG, " Type = "+roleModel.getName()+" -> "+permissionModel.getName());
//
//                        //treeModels = cDatabaseUtils.buildPermissionTree(context, roleModel,
//                        //        permissionModel);
//                    }
//
////                    Gson gson = new Gson();
////                    for (cTreeModel treeModel: treeModels){
////                        if(treeModel.getType() == 0) {
////                            Log.d(TAG, " Type = "+treeModel.getType()+" -> "+gson.toJson(treeModel));
////                        }
////                    }
//
//                    /* call back on list of user permissions by roles */
//                    callback.onReadUserPermissionsSucceeded(treeModels);
//                })
//                .addOnFailureListener(e ->
//                        callback.onReadUserPermissionsFailed("Failed to read permissions"));
//    }