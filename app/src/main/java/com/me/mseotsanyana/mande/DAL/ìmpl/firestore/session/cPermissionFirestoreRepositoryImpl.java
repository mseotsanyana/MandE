package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class cPermissionFirestoreRepositoryImpl implements iPermissionRepository {
//    private static final String TAG = cPermissionFirestoreRepositoryImpl.class.getSimpleName();

    private final Context context;
    private final FirebaseFirestore db;

    public cPermissionFirestoreRepositoryImpl(Context context) {
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    /* ###################################### READ ACTIONS ###################################### */

    /**
     * read user permissions
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
                                    iReadUserPermissionsCallback callback) {

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
                        callback.onReadUserPermissionsFailed("Failed to read roles"));
    }

    @Override
    public void readModulePermissions(String organizationServerID, String userServerID,
                                      int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                      List<Integer> statusBITS,
                                      iReadModulePermissionsCallback callback) {

    }

    /**
     * filter user permissions by roles
     *
     * @param roleModelMap map of role identification and corresponding models
     * @param callback     call back
     */
    private void filterRolePermissions(Map<String, cRoleModel> roleModelMap,
                                       iReadUserPermissionsCallback callback) {

        CollectionReference coPermissionRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
        List<String> role_ids = new ArrayList<>(roleModelMap.keySet());
        Query permissionQuery = coPermissionRef.whereIn(FieldPath.documentId(), role_ids);

        permissionQuery.get()
                .addOnCompleteListener(task -> {
                    cRoleModel roleModel = null;
                    cPermissionModel permissionModel = null;
                    for (DocumentSnapshot perm_doc : Objects.requireNonNull(task.getResult())) {

                        permissionModel = perm_doc.toObject(cPermissionModel.class);

                        if (permissionModel != null) {
                            permissionModel.setRoleServerID(perm_doc.getId());
                            for (Map.Entry<String, cRoleModel> entry : roleModelMap.entrySet()) {
                                //Log.d(TAG, " Role Model = "+entry.getKey()+" = "+permissionModel.getRoleServerID());
                                if (entry.getKey().equals(permissionModel.getRoleServerID())) {

                                    // rolePermissionModels.put(entry.getValue(), permissionModel);
                                    // FIXME: what about if the user has list of multiple roles
                                    //  and permissions
                                    roleModel = entry.getValue();
                                    roleModel.setRoleServerID(entry.getKey());

                                    permissionModel.setName(roleModel.getName());
                                    break;
                                }
                            }
                        }
                    }

                    // add maps of menu items and entities not in the db
                    // return all menus
                    List<cTreeModel> treeModels = new ArrayList<>();
                    if(roleModel != null) {

                        //Log.d(TAG, " Type = "+roleModel.getName()+" -> "+permissionModel.getName());

                        //treeModels = cDatabaseUtils.buildPermissionTree(context, roleModel,
                        //        permissionModel);
                    }

//                    Gson gson = new Gson();
//                    for (cTreeModel treeModel: treeModels){
//                        if(treeModel.getType() == 0) {
//                            Log.d(TAG, " Type = "+treeModel.getType()+" -> "+gson.toJson(treeModel));
//                        }
//                    }

                    /* call back on list of user permissions by roles */
                    callback.onReadUserPermissionsSucceeded(treeModels);
                })
                .addOnFailureListener(e ->
                        callback.onReadUserPermissionsFailed("Failed to read permissions"));
    }

    /* ##################################### UPDATE ACTIONS ##################################### */

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
                    }else{
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

                    if(!role_id_set.isEmpty()) {
                        List<String> role_ids = new ArrayList<>(role_id_set);
                        // filter roles permissions for saving purposes
                        filterRolesPermissions(role_ids, callback);
                    }else{
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
                                                "Loading default settings...");                            }
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