package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iTeamRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cTeamFirestoreRepositoryImpl implements iTeamRepository {
    private static final String TAG = cTeamFirestoreRepositoryImpl.class.getSimpleName();

    private final FirebaseFirestore db;

    public cTeamFirestoreRepositoryImpl(Context context) {
        this.db = FirebaseFirestore.getInstance();
    }

    /* ###################################### READ ACTIONS ###################################### */

    /**
     * read roles
     *
     * @param organizationServerID organization identification
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team bit
     * @param secondaryTeamBITS    secondary team bits
     * @param statusBITS           status bits
     * @param callback             call back
     */

    @Override
    public void readTeams(String organizationServerID, String userServerID, int primaryTeamBIT,
                          List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
                          iReadTeamsCallback callback) {

        CollectionReference coTeamRef = db.collection(cRealtimeHelper.KEY_TEAMS);

        Query teamQuery = coTeamRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);

        teamQuery.get()
                .addOnCompleteListener(task -> {
                    List<cTeamModel> teamModels = new ArrayList<>();
                    for (DocumentSnapshot team_doc : Objects.requireNonNull(task.getResult())) {
                        cTeamModel teamModel = team_doc.toObject(cTeamModel.class);

                        if (teamModel != null) {
                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                            perm.setUserOwnerID(teamModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(teamModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(teamModel.getUnixpermBITS());

                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
                                    secondaryTeamBITS)) {
                                teamModels.add(teamModel);
                            }
                        }
                    }

                    // call back on teams
                    callback.onReadTeamsSucceeded(teamModels);
                })
                .addOnFailureListener(e -> callback.onReadTeamsFailed(
                        "Failed to read roles"));
    }

    @Override
    public void readTeamsWithMembers(String organizationServerID, String userServerID,
                                     int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                     List<Integer> statusBITS,
                                     iReadTeamsWithMembersCallback callback) {

        CollectionReference coTeamRef = db.collection(cRealtimeHelper.KEY_TEAMS);

        Query teamQuery = coTeamRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);

        teamQuery.get()
                .addOnCompleteListener(task -> {
                    Map<String, cTeamModel> teamModelMap = new HashMap<>();
                    for (DocumentSnapshot team_doc : Objects.requireNonNull(task.getResult())) {
                        cTeamModel teamModel = team_doc.toObject(cTeamModel.class);

                        if (teamModel != null) {
                            teamModel.setCompositeServerID(team_doc.getId());

                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                            perm.setUserOwnerID(teamModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(teamModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(teamModel.getUnixpermBITS());

                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
                                    secondaryTeamBITS)) {
                                teamModelMap.put(teamModel.getCompositeServerID(), teamModel);
                            }
                        }
                    }

                    filterTeamMembers(teamModelMap, organizationServerID, userServerID,
                            primaryTeamBIT, secondaryTeamBITS, statusBITS, callback);

                })
                .addOnFailureListener(e -> callback.onReadTeamsWithMembersFailed(
                        "Failed to read teams with members"));

    }

    private void filterTeamMembers(Map<String, cTeamModel> teamModelMap,
                                   String organizationServerID, String userServerID,
                                   int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                   List<Integer> statusBITS,
                                   iReadTeamsWithMembersCallback callback) {

        if(!teamModelMap.isEmpty()) {
            CollectionReference coTeamMemberRef = db.collection(cRealtimeHelper.KEY_TEAM_MEMBERS);
            List<String> team_id_set = new ArrayList<>(teamModelMap.keySet());
            Query teamMemberQuery = coTeamMemberRef.whereIn("teamMemberServerID", team_id_set);

            teamMemberQuery.get()
                    .addOnCompleteListener(task -> {
                        /* retrieve a pair of user account and team member ids */
                        List<Pair<String, String>> team_acc_ids = new ArrayList<>();
                        for (DocumentSnapshot acc_doc : Objects.requireNonNull(task.getResult())) {
                            String accountID = acc_doc.getString("userAccountServerID");
                            String teamID = acc_doc.getString("teamMemberServerID");

                            if (teamID != null && accountID != null) {
                                team_acc_ids.add(new Pair<>(teamID, accountID));
                            }
                        }

                        /* map team model with a corresponding list of user account ids */
                        Map<cTeamModel, List<String>> team_user_acc_ids = new HashMap<>();
                        for (Map.Entry<String, cTeamModel> entry : teamModelMap.entrySet()) {
                            cTeamModel teamModel = entry.getValue();

                            List<String> user_acc_ids = new ArrayList<>();
                            for (Pair<String, String> pair : team_acc_ids) {
                                if (teamModel.getCompositeServerID().equals(pair.first)) {
                                    user_acc_ids.add(pair.second);
                                }
                            }
                            team_user_acc_ids.put(teamModel, user_acc_ids);
                        }

                        // filter the account of the team member
                        filterMemberAccounts(team_user_acc_ids, organizationServerID,
                                userServerID, primaryTeamBIT, secondaryTeamBITS, statusBITS,
                                callback);
                    })
                    .addOnFailureListener(e -> callback.onReadTeamsWithMembersFailed(
                            "Failed to filter team members"));
        }else {
            callback.onReadTeamsWithMembersFailed("Failed to filter team members");
        }
    }

    private void filterMemberAccounts(Map<cTeamModel, List<String>> team_user_acc_ids,
                                      String organizationServerID, String userServerID,
                                      int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                      List<Integer> statusBITS,
                                      iReadTeamsWithMembersCallback callback) {

        CollectionReference coUserAccountsRef = db.collection(cRealtimeHelper.KEY_USERACCOUNTS);

        /* collapsing the lists of user account ids to a list of all user account ids */
        Collection<List<String>> user_account_ids = team_user_acc_ids.values();
        List<String> account_ids = new ArrayList<>();
        for (List<String> acc_ids : user_account_ids) {
            account_ids.addAll(acc_ids);
        }

        Query userAccountQuery = coUserAccountsRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("userAccountServerID", account_ids);

        userAccountQuery.get()
                .addOnCompleteListener(task -> {
                    /* retrieve a pair of user account and profile ids */
                    Map<String, String> user_acc_prof_map = new HashMap<>();
                    for (DocumentSnapshot account : Objects.requireNonNull(task.getResult())) {
                        cUserAccountModel accountModel;
                        accountModel = account.toObject(cUserAccountModel.class);

                        if (accountModel != null) {
                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                            perm.setUserOwnerID(accountModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(accountModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(accountModel.getUnixpermBITS());
                            perm.setStatusBIT(accountModel.getStatusBIT());

                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
                                    secondaryTeamBITS, statusBITS)) {

                                String userAccountID = accountModel.getUserAccountServerID();
                                String userProfileID = accountModel.getUserServerID();
                                user_acc_prof_map.put(userAccountID, userProfileID);
                            }
                        }
                    }

                    /* map team model with a corresponding list of user profile ids */
                    Map<cTeamModel, List<String>> team_user_profile_map = new HashMap<>();

                    for (Map.Entry<cTeamModel,
                            List<String>> map_entry : team_user_acc_ids.entrySet()) {
                        cTeamModel teamModel = map_entry.getKey();

                        List<String> user_profile_ids = new ArrayList<>();
                        for (Map.Entry<String, String> pair_entry : user_acc_prof_map.entrySet()) {
                            String user_account_id = pair_entry.getKey();
                            String user_profile_id = pair_entry.getValue();
                            if (map_entry.getValue().contains(user_account_id)) {
                                user_profile_ids.add(user_profile_id);
                            }
                        }

                        team_user_profile_map.put(teamModel, user_profile_ids);
                    }

                    /* filter user profiles of the corresponding teams */
                    filterUserProfiles(team_user_profile_map, callback);
                })
                .addOnFailureListener(e -> callback.onReadTeamsWithMembersFailed(
                        "Failed to filter member accounts"));
    }

    private void filterUserProfiles(Map<cTeamModel, List<String>> team_user_profile_map,
                                    iReadTeamsWithMembersCallback callback) {

        CollectionReference coUserProfilesRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);

        Collection<List<String>> col_user_profile_ids = team_user_profile_map.values();
        List<String> user_profile_ids = new ArrayList<>();
        for (List<String> acc_ids : col_user_profile_ids) {
            user_profile_ids.addAll(acc_ids);
        }

        Query userProfileQuery = coUserProfilesRef
                .whereIn(FieldPath.documentId(), user_profile_ids);

        userProfileQuery.get()
                .addOnCompleteListener(task -> {
                    /* retrieve corresponding user profiles to their ids */
                    List<cUserProfileModel> userProfileModels = new ArrayList<>();
                    for (QueryDocumentSnapshot doc :
                            Objects.requireNonNull(task.getResult())) {
                        cUserProfileModel user = doc.toObject(cUserProfileModel.class);
                        user.setUserServerID(doc.getId());
                        userProfileModels.add(user);
                    }

                    /* map team model with a corresponding list of user profile models */
                    Map<cTeamModel, List<cUserProfileModel>> teamMemberMap = new HashMap<>();
                    for (Map.Entry<cTeamModel,
                            List<String>> entry : team_user_profile_map.entrySet()) {
                        cTeamModel teamModel = entry.getKey();
                        List<cUserProfileModel> users = new ArrayList<>();
                        for (cUserProfileModel user : userProfileModels) {
                            if (entry.getValue().contains(user.getUserServerID())) {
                                users.add(user);
                            }
                        }

                        teamMemberMap.put(teamModel, users);
                    }

                    // call back on organization members
                    callback.onReadTeamsWithMembersSucceeded(teamMemberMap);
                })
                .addOnFailureListener(e -> callback.onReadTeamsWithMembersFailed(
                        "Failed to filter user profile"));
    }

    @Override
    public void readTeamsWithRoles(String organizationServerID, String userServerID,
                                   int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                   List<Integer> statusBITS, iReadTeamsWithRolesCallback callback) {

        CollectionReference coTeamRef = db.collection(cRealtimeHelper.KEY_TEAMS);

        Query teamQuery = coTeamRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);

        teamQuery.get()
                .addOnCompleteListener(task -> {
                    Map<String, cTeamModel> teamModelMap = new HashMap<>();
                    for (DocumentSnapshot team_doc : Objects.requireNonNull(task.getResult())) {
                        cTeamModel teamModel = team_doc.toObject(cTeamModel.class);

                        if (teamModel != null) {
                            teamModel.setCompositeServerID(team_doc.getId());

                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                            perm.setUserOwnerID(teamModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(teamModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(teamModel.getUnixpermBITS());

                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
                                    secondaryTeamBITS)) {
                                teamModelMap.put(teamModel.getCompositeServerID(), teamModel);
                            }
                        }
                    }

                    filterTeamsWithRoles(teamModelMap, organizationServerID, userServerID,
                            primaryTeamBIT, secondaryTeamBITS, statusBITS, callback);

                })
                .addOnFailureListener(e -> callback.onReadTeamsWithRolesFailed(
                        "Failed to read teams with roles"));

    }

    private void filterTeamsWithRoles(Map<String, cTeamModel> teamModelMap,
                                      String organizationServerID, String userServerID,
                                      int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                      List<Integer> statusBITS, iReadTeamsWithRolesCallback callback) {

        CollectionReference coTeamRoleRef = db.collection(cRealtimeHelper.KEY_TEAM_ROLES);
        List<String> team_id_set = new ArrayList<>(teamModelMap.keySet());
        Query teamRoleQuery = coTeamRoleRef.whereIn("teamServerID", team_id_set);

        teamRoleQuery.get()
                .addOnCompleteListener(task -> {
                    /* retrieve a pair of user account and team member ids */
                    List<Pair<String, String>> team_role_pair = new ArrayList<>();
                    for (DocumentSnapshot team_role_doc : Objects.requireNonNull(task.getResult())) {
                        String roleID = team_role_doc.getString("roleServerID");
                        String teamID = team_role_doc.getString("teamServerID");

                        if (teamID != null && roleID != null) {
                            team_role_pair.add(new Pair<>(teamID, roleID));
                        }
                    }

                    /* map team model with a corresponding list of role ids */
                    Map<cTeamModel, List<String>> team_role_ids = new HashMap<>();
                    for (Map.Entry<String, cTeamModel> entry : teamModelMap.entrySet()) {
                        cTeamModel teamModel = entry.getValue();

                        List<String> role_ids = new ArrayList<>();
                        for (Pair<String, String> pair : team_role_pair) {
                            if (teamModel.getCompositeServerID().equals(pair.first)) {
                                role_ids.add(pair.second);
                            }
                        }
                        team_role_ids.put(teamModel, role_ids);
                    }

                    // filter the roles of the corresponding teams
                    mapRolesToTeams(team_role_ids, organizationServerID, userServerID,
                            primaryTeamBIT, secondaryTeamBITS, statusBITS, callback);
                })
                .addOnFailureListener(e -> callback.onReadTeamsWithRolesFailed(
                        "Failed to filter team roles"));
    }


    private void mapRolesToTeams(Map<cTeamModel, List<String>> team_role_map,
                                 String organizationServerID, String userServerID,
                                 int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                 List<Integer> statusBITS, iReadTeamsWithRolesCallback callback) {

        CollectionReference coRolesRef = db.collection(cRealtimeHelper.KEY_ROLES);

        Collection<List<String>> col_role_ids = team_role_map.values();
        List<String> role_ids = new ArrayList<>();
        for (List<String> acc_ids : col_role_ids) {
            role_ids.addAll(acc_ids);
        }

        Query roleQuery = coRolesRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn(FieldPath.documentId(), role_ids);

        roleQuery.get()
                .addOnCompleteListener(task -> {
                    /* retrieve corresponding roles to their ids */
                    List<cRoleModel> roleModels = new ArrayList<>();
                    for (QueryDocumentSnapshot doc :
                            Objects.requireNonNull(task.getResult())) {
                        cRoleModel roleModel = doc.toObject(cRoleModel.class);
                        roleModel.setRoleServerID(doc.getId());

                        cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                        perm.setUserOwnerID(roleModel.getUserOwnerID());
                        perm.setTeamOwnerBIT(roleModel.getTeamOwnerBIT());
                        perm.setUnixpermBITS(roleModel.getUnixpermBITS());
                        perm.setStatusBIT(roleModel.getStatusBIT());

                        if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
                                secondaryTeamBITS, statusBITS)) {
                            roleModels.add(roleModel);
                        }
                    }

                    /* map team model with a corresponding list of role models */
                    Map<cTeamModel, List<cRoleModel>> teamRoleMap = new HashMap<>();
                    for (Map.Entry<cTeamModel, List<String>> entry : team_role_map.entrySet()) {
                        cTeamModel teamModel = entry.getKey();
                        List<cRoleModel> roles = new ArrayList<>();
                        for (cRoleModel roleModel : roleModels) {
                            if (entry.getValue().contains(roleModel.getRoleServerID())) {
                                roles.add(roleModel);
                            }
                        }

                        teamRoleMap.put(teamModel, roles);
                    }

                    // call back on organization members
                    callback.onReadTeamsWithRolesSucceeded(teamRoleMap);
                })
                .addOnFailureListener(e -> callback.onReadTeamsWithRolesFailed(
                        "Failed to map teams to roles"));
    }


    /**
     * read role teams
     *
     * @param teamServerID         team identification
     * @param organizationServerID organization identification
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team bit
     * @param secondaryTeamBITS    secondary team bits
     * @param statusBITS           status bits
     * @param callback             call back
     */
    public void readTeamRoles(String teamServerID, String organizationServerID, String
            userServerID,
                              int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                              List<Integer> statusBITS, iReadTeamRolesCallback callback) {

        CollectionReference coTeamRolesRef = db.collection(cRealtimeHelper.KEY_TEAM_ROLES);
        Query teamRolesQuery = coTeamRolesRef.whereEqualTo("teamServerID", teamServerID);

        teamRolesQuery.get()
                .addOnCompleteListener(task -> {
                    Set<String> role_id_set = new HashSet<>();
                    for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
                        String roleID = role_doc.getString("roleServerID");
                        if (roleID != null) {
                            role_id_set.add(roleID);
                        }
                    }
                    // filter the roles of the team identification
                    List<String> team_ids = new ArrayList<>(role_id_set);
                    filterTeamRoles(team_ids, organizationServerID, userServerID, primaryTeamBIT,
                            secondaryTeamBITS, statusBITS, callback);
                })
                .addOnFailureListener(e -> {
                    callback.onReadTeamsFailed("Failed to read Organization." + e);
                    Log.w(TAG, "Failed to read value.", e);
                });

    }

    /**
     * read teams
     *
     * @param role_ids             list of team identifications
     * @param organizationServerID organization identification
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team bit
     * @param secondaryTeamBITS    secondary team bits
     * @param statusBITS           status bits
     * @param callback             call back
     */
    private void filterTeamRoles(List<String> role_ids, String organizationServerID,
                                 String userServerID, int primaryTeamBIT,
                                 List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
                                 iReadTeamRolesCallback callback) {

        CollectionReference coTeamRef = db.collection(cRealtimeHelper.KEY_ROLES);

        Query teamQuery = coTeamRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("teamServerID", role_ids);

        teamQuery.get()
                .addOnCompleteListener(task -> {
                    List<cRoleModel> roleModels = new ArrayList<>();
                    for (DocumentSnapshot role_doc :
                            Objects.requireNonNull(task.getResult())) {

                        cRoleModel roleModel = role_doc.toObject(cRoleModel.class);
                        if (roleModel != null) {
                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                            perm.setUserOwnerID(roleModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(roleModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(roleModel.getUnixpermBITS());
                            perm.setStatusBIT(roleModel.getStatusBIT());

                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
                                    secondaryTeamBITS, statusBITS)) {
                                roleModels.add(roleModel);
                            }
                        }
                    }

                    /* call back on role teams */
                    callback.onReadTeamRolesSucceeded(roleModels);
                })
                .addOnFailureListener(e -> {
                    callback.onReadTeamRolesFailed("Failed to read teams.");
                    Log.w(TAG, "Failed to read value.", e);
                });
    }
}