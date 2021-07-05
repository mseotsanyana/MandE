package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class cPermissionFirestoreRepositoryImpl implements iPermissionRepository {
    private static final String TAG = cPermissionFirestoreRepositoryImpl.class.getSimpleName();

    private final Context context;
    private final FirebaseFirestore db;

    public cPermissionFirestoreRepositoryImpl(Context context) {
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }


    /* ###################################### READ ACTIONS ###################################### */

    /**
     * read user permissions FIXME
     *
     * @param organizationServerID organization identification
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team bit
     * @param secondaryTeamBITS    secondary team bits
     * @param statusBITS           status bits
     * @param callback             call back
     */
    public void readUserPermissions(String organizationServerID, String userServerID,
                                    int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                    List<Integer> statusBITS,
                                    iSaveUserPermissionsCallback callback) {

        CollectionReference coRoleRef = db.collection(cRealtimeHelper.KEY_ROLES);

        Query roleQuery = coRoleRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);
        roleQuery.get()
                .addOnCompleteListener(task -> {
                    List<cRoleModel> roleModels = new ArrayList<>();
                    for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
                        cRoleModel roleModel = role_doc.toObject(cRoleModel.class);

                        if (roleModel != null) {
                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                            perm.setUserOwnerID(roleModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(roleModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(roleModel.getUnixpermBITS());

                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
                                    secondaryTeamBITS)) {
                                roleModels.add(roleModel);
                            }
                        }
                    }

                    // list of roles
                    // readMenuItems(roleModels)
                    // readPermissions(roleModels)

                    // call back on roles
                    //callback.onReadRolesSucceeded(roleModels);
                })
                .addOnFailureListener(e -> {
                });//callback.onReadRolesFailed("Failed to read roles"));
    }



    /* ##################################### UPDATE ACTIONS ##################################### */

    /**
     * save user privileges locally for quick access
     *
     * @param callback call back
     */
    @Override
    public void saveUserPermissions(iSaveUserPermissionsCallback callback) {
        // clear the privilege permissions
        //callback.onClearPreferences();

        String userServerID = FirebaseAuth.getInstance().getUid();

        CollectionReference coUserAccountsRef = db.collection(cRealtimeHelper.KEY_USERACCOUNTS);
        Query userAccountQuery = coUserAccountsRef
                .whereEqualTo("userServerID", userServerID)
                .whereEqualTo("currentOrganization", true);

        userAccountQuery.get()
                .addOnCompleteListener(task -> {
                    for (DocumentSnapshot useraccount : Objects.requireNonNull(task.getResult())) {
                        cUserAccountModel userAccountModel;
                        userAccountModel = useraccount.toObject(cUserAccountModel.class);
                        if (userAccountModel != null) {
                            if (userAccountModel.isCurrentOrganization()) {
                                String userAccountID = userAccountModel.getUserAccountServerID();
                                String organizationID = userAccountModel.getOrganizationServerID();
                                String primaryTeamID = userAccountModel.getTeamServerID();

                                /* call back on saving logged in identification */
                                callback.onSaveOwnerID(userServerID);
                                /* call back on saving organization identification */
                                callback.onSaveOrganizationServerID(organizationID);
                                /* call back on saving primary bit */
                                callback.onSavePrimaryTeamBIT(Integer.parseInt(primaryTeamID));

                                saveSecondaryTeams(userAccountID, primaryTeamID, callback);

                                break;
                            }
                        } else {
                            callback.onSaveUserPermissionsFailed(" due to user account entity!");
                        }
                    }
                })
                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
                        " due to user account entity!"));
    }

    /**
     * This filters secondary teams bits of the loggedIn user and saves it.
     *
     * @param accountServerID user account identification
     * @param primaryTeamID   primary team identification
     * @param callback        call back
     */
    public void saveSecondaryTeams(String accountServerID, String primaryTeamID,
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

                    List<String> team_ids = new ArrayList<>(team_id_set);
                    filterTeams(team_ids, primaryTeamID, callback);
                })
                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
                        " due to team member entity!"));
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
                        //secondaryTeamBITS |= Integer.parseInt(teamID);
                    }

                    /* remove primary team if it is also in the set */
                    secondary_team_set.remove(Integer.parseInt(primaryTeamID));

                    /* call back on saving secondary team bits */
                    List<Integer> secondaryTeams = new ArrayList<>(secondary_team_set);
                    callback.onSaveSecondaryTeams(secondaryTeams);

                    // Team Roles - this is to get roles for extracting
                    // menu, entity and unix permissions
                    filterTeamsRoles(team_ids, callback);
                })
                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
                        " due to team entity!"));
    }

    /**
     * read roles of the teams the user belongs to
     *
     * @param team_ids team identifications
     * @param callback call back
     */
    private void filterTeamsRoles(List<String> team_ids, iSaveUserPermissionsCallback callback) {
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

                    List<String> role_ids = new ArrayList<>(role_id_set);
                    // filter role menu items for saving purposes
                    //filterRolesMenuItems(role_ids, callback);
                    // filter roles privileges for saving purposes
                    filterRolesPermissions(role_ids, callback);
                })
                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
                        " due to team team role entity!"));
    }

//    /**
//     * filter roles for menu items
//     *
//     * @param role_ids role identifications
//     * @param callback caLL back
//     */
//    private void filterRolesMenuItems(List<String> role_ids,
//                                      iSaveUserPermissionsCallback callback) {
//        CollectionReference coRoleMenuItemsRef;
//
//        coRoleMenuItemsRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
//        Query menuServerQuery = coRoleMenuItemsRef.whereIn(FieldPath.documentId(), role_ids);
//
//        menuServerQuery.get()
//                .addOnCompleteListener(task -> {
//                    Set<Integer> menu_set = new HashSet<>();
//                    for (QueryDocumentSnapshot result : Objects.requireNonNull(task.getResult())) {
//                        Map<String, Object> data = result.getData();
//                        for (String menu_id : data.keySet()) {
//                            menu_set.add(Integer.parseInt(menu_id));
//                        }
//                    }
//
//                    List<cMenuModel> menu_items;
//                    menu_items = cDatabaseUtils.getMenuModelSet(context, menu_set);
//                    callback.onSaveMenuItems(menu_items);
//                })
//                .addOnFailureListener(e -> {
//                    callback.onSaveUserPermissionsFailed(" Failed to read role menu items ");
//                    Log.w(TAG, "Failed to read value.", e);
//                });
//    }

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
                                callback.onSaveUserPermissionsFailed(" due to permissions !");
                            }
                        }

                        // save menu item permissions
                        saveMenuPermissions(menuitems, callback);
                        // save entity and unix permissions
                        saveEntityAndUnixPermissions(entitymodules, callback);
                    } else {
                        callback.onSaveUserPermissionsFailed(" due to permissions!");
                        return;
                    }

                    callback.onSaveUserPermissionsSucceeded("successfully logged in");
                })
                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
                        " due to permissions!"));
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
        //Log.d(TAG, "==============>>>>>>>>>>>>"+menuitems.entrySet());
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

//    private void filterRolesPermissions(List<String> roles_ids,
//                                        iSaveUserPermissionsCallback callback) {
//        CollectionReference coRolePermsRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
//
//        Query permissionQuery = coRolePermsRef.whereIn(FieldPath.documentId(), roles_ids);
//
//        permissionQuery.get()
//                .addOnCompleteListener(task -> {
//
//                    QuerySnapshot perm_snapshot = task.getResult();
//
//                    if (perm_snapshot != null) {
//                        for (DocumentSnapshot perm_doc : perm_snapshot.getDocuments()) {
//                            cPermissionModel permissionModel = perm_doc.toObject(
//                                    cPermissionModel.class);
//                            if (permissionModel != null) {
//                                // save user permissions
//                                saveEntityAndUnixPermissions(permissionModel, callback);
//                            } else {
//                                callback.onSaveUserPermissionsFailed(" due to permissions !");
//                            }
//                        }
//                    } else {
//                        callback.onSaveUserPermissionsFailed(" due to permissions!");
//                        return;
//                    }
//
//                    callback.onSaveUserPermissionsSucceeded("successfully logged in");
//                })
//                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
//                        " due to permissions!"));
//    }

//    private void filterRolesMenuItems(List<String> role_ids,
//                                      iSaveUserPermissionsCallback callback) {
//        CollectionReference coRoleMenuItemsRef;
//
//        coRoleMenuItemsRef = db.collection(cRealtimeHelper.KEY_ROLE_MENU_ITEMS);
//        Query menuServerQuery = coRoleMenuItemsRef.whereIn(FieldPath.documentId(), role_ids);
//
//        menuServerQuery.get()
//                .addOnCompleteListener(task -> {
//                    Set<Integer> menu_set = new HashSet<>();
//                    for (QueryDocumentSnapshot result : Objects.requireNonNull(task.getResult())) {
//                        Map<String, Object> data = result.getData();
//                        for (String menu_id : data.keySet()) {
//                            menu_set.add(Integer.parseInt(menu_id));
//                        }
//                    }
//
//                    List<cMenuModel> menu_items;
//                    menu_items = cDatabaseUtils.getMenuModelSet(context, menu_set);
//                    callback.onSaveMenuItems(menu_items);
//                })
//                .addOnFailureListener(e -> {
//                    callback.onSaveUserPermissionsFailed(" Failed to read role menu items ");
//                    Log.w(TAG, "Failed to read value.", e);
//                });
//    }


//    /**
//     * read privileges of the roles the user belongs to through the their teams
//     *
//     * @param role_ids role identifications
//     * @param callback call back
//     */
//    private void readRolesPermissions(List<String> role_ids, iSaveUserPrivilegesCallback callback) {
//        CollectionReference coRolesPrivilegesRef = null;//db.collection(cRealtimeHelper.KEY_ROLE_PRIVILEGES);
//
//        Query privilegeQuery = coRolesPrivilegesRef
//                .whereIn("roleServerID", role_ids);
//
//        privilegeQuery.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        List<String> privilege_ids = new ArrayList<>();
//                        for (DocumentSnapshot privilege : Objects.requireNonNull(task.getResult())) {
//                            String privilegeID = Objects.requireNonNull(
//                                    privilege.get("privilegeServerID")).toString();
//                            privilege_ids.add(privilegeID);
//                        }
//                        if (!privilege_ids.isEmpty()) {
//                            readPrivilegesPermissions(privilege_ids, callback);
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        callback.onSaveUserPermissionsFailed(" due to role previlege entity!");
//                    }
//                });
//    }


//    private void readRolesPermissions(List<String> roles_ids, iSaveUserPermissionsCallback callback) {
//        CollectionReference coRolePermsRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
//
//        Query permissionQuery = coRolePermsRef
//                .whereIn(FieldPath.documentId(), roles_ids);
//
//        permissionQuery.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                        QuerySnapshot permissions = task.getResult();
//
//                        if (permissions != null) {
//                            for (DocumentSnapshot privilege : permissions.getDocuments()) {
//                                cPermissionModel permission = privilege.toObject(cPermissionModel.class);
//                                if (permission != null) {
//                                    // operation status
//                                    Map<String, Map<String, Map<String, List<Integer>>>> opstatus;
//                                    opstatus = null;//permission.getOpstatus();
//                                    String moduleID;
//
//                                    /* modules */
//                                    for (Map.Entry<String, Map<String, Map<String, List<Integer>>>> perms :
//                                            opstatus.entrySet()) {
//                                        moduleID = perms.getKey();
//                                        Map<String, Map<String, List<Integer>>> modules = perms.getValue();
//                                        String entityID;
//                                        int entityBITS = 0;
//
//                                        /* entities */
//                                        for (Map.Entry<String, Map<String, List<Integer>>> entities : modules.entrySet()) {
//                                            entityID = entities.getKey();
//                                            entityBITS |= Integer.parseInt(entities.getKey());
//                                            Map<String, List<Integer>> ops_status = entities.getValue();
//                                            String operationID;
//                                            int operationBITS = 0, statusBITS;
//
//                                            /* operations */
//                                            for (Map.Entry<String, List<Integer>> ops : ops_status.entrySet()) {
//                                                operationID = ops.getKey();
//                                                operationBITS |= Integer.parseInt(ops.getKey());
//
//                                                /* statuses */
//                                                List<Integer> statuses = new ArrayList<>(ops.getValue());
//                                                callback.onSaveStatusBITS(moduleID, entityID, operationID,
//                                                        statuses);
//                                            }
//                                            callback.onSaveOperationBITS(moduleID, entityID, operationBITS);
//                                        }
//                                        callback.onSaveEntityBITS(moduleID, entityBITS);
//                                    }
//
//                                    // unix permissions
//                                    Map<String, Map<String, List<Integer>>> unixperm;
//                                    unixperm = null;//permission.getUnixperms();
//                                    for (Map.Entry<String, Map<String, List<Integer>>> perms :
//                                            unixperm.entrySet()) {
//                                        moduleID = perms.getKey();
//                                        Map<String, List<Integer>> modules = perms.getValue();
//                                        String entityID;
//                                        for (Map.Entry<String, List<Integer>> entities : modules.entrySet()) {
//                                            entityID = entities.getKey();
//                                            int unixpermBITS = 0;
//                                            for (Integer unix_perm : entities.getValue()) {
//                                                unixpermBITS |= unix_perm;
//                                            }
//                                            callback.onSaveUnixPermBITS(moduleID, entityID, unixpermBITS);
//                                        }
//                                    }
//                                } else {
//                                    callback.onSaveUserPermissionsFailed(" due to privilege permission entity!");
//                                }
//                            }
//                        }else {
//                            callback.onSaveUserPermissionsFailed(" due to privilege permission entity!");
//                            return;
//                        }
//
//                        callback.onSaveUserPermissionsSucceeded("successfully logged in");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        callback.onSaveUserPermissionsFailed(" due to privilege permission entity!");
//                    }
//                });
//    }