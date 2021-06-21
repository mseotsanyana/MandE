package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iPrivilegeRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class cPrivilegeFirestoreRepositoryImpl implements iPrivilegeRepository {
    private static final String TAG = cPrivilegeFirestoreRepositoryImpl.class.getSimpleName();

    private final Context context;
    private final FirebaseFirestore db;

    public cPrivilegeFirestoreRepositoryImpl(Context context) {
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    /**
     * save user privileges locally for quick access
     *
     * @param callback call back
     */
    @Override
    public void saveUserPrivileges(iSaveUserPrivilegesCallback callback) {
        // clear the privilege permissions
        callback.onClearPreferences();

        String userServerID = FirebaseAuth.getInstance().getUid();

        CollectionReference coUserAccountsRef = db.collection(cRealtimeHelper.KEY_USERACCOUNTS);
        Query userAccountQuery = coUserAccountsRef
                .whereEqualTo("userServerID", userServerID)
                .whereEqualTo("currentOrganization", true);

        userAccountQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot useraccount : Objects.requireNonNull(task.getResult())) {
                            cUserAccountModel userAccountModel = useraccount.toObject(cUserAccountModel.class);
                            if(userAccountModel != null) {
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

                                    saveSecondaryTeamsBITS(userAccountID, primaryTeamID, callback);

                                    break;
                                }
                            }else{
                                callback.onSaveUserPrivilegesFailed(" due to user account entity!");
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onSaveUserPrivilegesFailed(" due to user account entity!");
                    }
                });
    }

    /**
     * This sets secondary teams bits of the loggedIn user and saves it.
     *
     * @param accountServerID user account identification
     */
    public void saveSecondaryTeamsBITS(String accountServerID, String primaryTeamID,
                                       iSaveUserPrivilegesCallback callback) {
        CollectionReference coTeamMembersRef = db.collection(cRealtimeHelper.KEY_TEAM_MEMBERS);

        Query teamMemberQuery = coTeamMembersRef
                .whereEqualTo("userAccountServerID", accountServerID);

        teamMemberQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<String> team_ids = new ArrayList<>();
                        for (DocumentSnapshot team : Objects.requireNonNull(task.getResult())) {
                            String teamID = Objects.requireNonNull(team.get("teamMemberServerID")).toString();
                            team_ids.add(teamID);
                        }
                        readTeams(team_ids, primaryTeamID, callback);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onSaveUserPrivilegesFailed(" due to team member entity!");
                    }
                });
    }

    /**
     * read the teams of the loggedIn user
     *
     * @param team_ids team identifications
     * @param primaryTeamID primary team identification
     * @param callback call back
     */
    private void readTeams(List<String> team_ids, String primaryTeamID,
                           iSaveUserPrivilegesCallback callback) {
        CollectionReference coTeamsRef = db.collection(cRealtimeHelper.KEY_TEAMS);

        Query teamQuery = coTeamsRef
                .whereIn("compositeServerID", team_ids);

        teamQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int secondaryTeamBITS = 0;
                        for (DocumentSnapshot team : Objects.requireNonNull(task.getResult())) {
                            String teamID = Objects.requireNonNull(team.get("teamServerID")).toString();
                            secondaryTeamBITS |= Integer.parseInt(teamID);

                        }
                        secondaryTeamBITS &= ~Integer.parseInt(primaryTeamID);

                        /* call back on saving secondary bits */
                        callback.onSaveSecondaryTeamBITS(secondaryTeamBITS);

                        // Team Roles
                        readTeamsRoles(team_ids, callback);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onSaveUserPrivilegesFailed(" due to team entity!");
                    }
                });

    }

    /**
     * read roles of the teams the user belongs to
     *
     * @param team_ids team identifications
     * @param callback call back
     */
    private void readTeamsRoles(List<String> team_ids, iSaveUserPrivilegesCallback callback) {
        CollectionReference coTeamsRolesRef = db.collection(cRealtimeHelper.KEY_TEAM_ROLES);

        Query roleQuery = coTeamsRolesRef
                .whereIn("teamServerID", team_ids);

        roleQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<String> role_ids = new ArrayList<>();
                        for (DocumentSnapshot role : Objects.requireNonNull(task.getResult())) {
                            String roleID = Objects.requireNonNull(role.get("roleServerID")).toString();
                            role_ids.add(roleID);
                        }
                        // read role menu items
                        readRoleMenuItems(role_ids, callback);
                        // read roles privileges
                        readRolesPrivileges(role_ids, callback);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onSaveUserPrivilegesFailed(" due to team team role entity!");
                    }
                });
    }

    private void readRoleMenuItems(List<String> role_ids,
                                   iSaveUserPrivilegesCallback callback) {
        CollectionReference coRoleMenuItemsRef;

        coRoleMenuItemsRef = db.collection(cRealtimeHelper.KEY_ROLE_MENU_ITEMS);
        Query menuServerQuery = coRoleMenuItemsRef
                .whereIn(FieldPath.documentId(), role_ids);

        menuServerQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Set<Integer> set_main_menu = new HashSet<>();
                        for (QueryDocumentSnapshot result : Objects.requireNonNull(task.getResult())) {
                            Map<String, Object> data = result.getData();
                            for(String menu_id: data.keySet()){
                                set_main_menu.add(Integer.parseInt(menu_id));
                            }
                        }

                        List<cMenuModel> menu_items;
                        menu_items = cDatabaseUtils.getMenuModelSet(context, set_main_menu);
                        callback.onSaveMenuItems(menu_items);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onSaveUserPrivilegesFailed("Failed to read role menu items: " +
                                e.toString());
                        Log.w(TAG, "Failed to read value.", e);
                    }
                });
    }


    /**
     * read privileges of the roles the user belongs to through the their teams
     *
     * @param role_ids role identifications
     * @param callback call back
     */
    private void readRolesPrivileges(List<String> role_ids, iSaveUserPrivilegesCallback callback) {
        CollectionReference coRolesPrivilegesRef = db.collection(cRealtimeHelper.KEY_ROLE_PRIVILEGES);

        Query privilegeQuery = coRolesPrivilegesRef
                .whereIn("roleServerID", role_ids);

        privilegeQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<String> privilege_ids = new ArrayList<>();
                        for (DocumentSnapshot privilege : Objects.requireNonNull(task.getResult())) {
                            String privilegeID = Objects.requireNonNull(
                                    privilege.get("privilegeServerID")).toString();
                            privilege_ids.add(privilegeID);
                        }
                        if (!privilege_ids.isEmpty()) {
                            readPrivilegesPermissions(privilege_ids, callback);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onSaveUserPrivilegesFailed(" due to role previlege entity!");
                    }
                });
    }

    /**
     * read permissions of the privileges of the user through their roles
     *
     * @param privilege_ids privilege identifications
     * @param callback call back
     */
    private void readPrivilegesPermissions(List<String> privilege_ids, iSaveUserPrivilegesCallback callback) {
        CollectionReference coPrivilegesPermsRef = db.collection(cRealtimeHelper.KEY_PRIVILEGE_PERMISSIONS);

        Query privilegeQuery = coPrivilegesPermsRef
                .whereIn(FieldPath.documentId(), privilege_ids);

        privilegeQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        QuerySnapshot privileges = task.getResult();

                        if (privileges != null) {
                            for (DocumentSnapshot privilege : privileges.getDocuments()) {
                                cPermissionModel permission = privilege.toObject(cPermissionModel.class);
                                if (permission != null) {
                                    // operation status
                                    Map<String, Map<String, Map<String, List<Integer>>>> opstatus;
                                    opstatus = permission.getOpstatus();
                                    String moduleID;

                                    /* modules */
                                    for (Map.Entry<String, Map<String, Map<String, List<Integer>>>> perms :
                                            opstatus.entrySet()) {
                                        moduleID = perms.getKey();
                                        Map<String, Map<String, List<Integer>>> modules = perms.getValue();
                                        String entityID;
                                        int entityBITS = 0;

                                        /* entities */
                                        for (Map.Entry<String, Map<String, List<Integer>>> entities : modules.entrySet()) {
                                            entityID = entities.getKey();
                                            entityBITS |= Integer.parseInt(entities.getKey());
                                            Map<String, List<Integer>> ops_status = entities.getValue();
                                            String operationID;
                                            int operationBITS = 0, statusBITS;

                                            /* operations */
                                            for (Map.Entry<String, List<Integer>> ops : ops_status.entrySet()) {
                                                operationID = ops.getKey();
                                                operationBITS |= Integer.parseInt(ops.getKey());

                                                /* statuses */
                                                List<Integer> statuses = new ArrayList<>(ops.getValue());
                                                callback.onSaveStatusBITS(moduleID, entityID, operationID,
                                                        statuses);
                                            }
                                            callback.onSaveOperationBITS(moduleID, entityID, operationBITS);
                                        }
                                        callback.onSaveEntityBITS(moduleID, entityBITS);
                                    }

                                    // unix permissions
                                    Map<String, Map<String, List<Integer>>> unixperm;
                                    unixperm = permission.getUnixperms();
                                    for (Map.Entry<String, Map<String, List<Integer>>> perms :
                                            unixperm.entrySet()) {
                                        moduleID = perms.getKey();
                                        Map<String, List<Integer>> modules = perms.getValue();
                                        String entityID;
                                        for (Map.Entry<String, List<Integer>> entities : modules.entrySet()) {
                                            entityID = entities.getKey();
                                            int unixpermBITS = 0;
                                            for (Integer unix_perm : entities.getValue()) {
                                                unixpermBITS |= unix_perm;
                                            }
                                            callback.onSaveUnixPermBITS(moduleID, entityID, unixpermBITS);
                                        }
                                    }
                                } else {
                                    callback.onSaveUserPrivilegesFailed(" due to privilege permission entity!");
                                }
                            }
                        }else {
                            callback.onSaveUserPrivilegesFailed(" due to privilege permission entity!");
                            return;
                        }

                        callback.onSaveUserPrivilegesSucceeded("successfully logged in");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onSaveUserPrivilegesFailed(" due to privilege permission entity!");
                    }
                });
    }
}