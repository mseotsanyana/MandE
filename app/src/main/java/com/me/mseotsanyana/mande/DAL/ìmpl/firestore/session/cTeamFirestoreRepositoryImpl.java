package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iTeamRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cTeamFirestoreRepositoryImpl implements iTeamRepository {
    private static String TAG = cTeamFirestoreRepositoryImpl.class.getSimpleName();

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
    public void readTeamRoles(String teamServerID, String organizationServerID, String userServerID,
                              int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                              List<Integer> statusBITS, iReadTeamsCallback callback) {

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
                    callback.onReadTeamRolesFailed("Failed to read Organization." + e);
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
    private void filterTeamRoles(List<String> role_ids, String organizationServerID, String userServerID,
                                 int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                 List<Integer> statusBITS, iReadTeamsCallback callback) {

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