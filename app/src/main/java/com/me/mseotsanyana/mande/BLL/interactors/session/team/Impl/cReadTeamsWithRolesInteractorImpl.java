package com.me.mseotsanyana.mande.BLL.interactors.session.team.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.cInteractorUtils;
import com.me.mseotsanyana.mande.BLL.interactors.session.team.iReadTeamsWithRolesInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iTeamRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class cReadTeamsWithRolesInteractorImpl extends cAbstractInteractor
        implements iReadTeamsWithRolesInteractor {
    private static final String TAG = cReadTeamsWithRolesInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iTeamRepository teamRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadTeamsWithRolesInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                             iSharedPreferenceRepository sharedPreferenceRepository,
                                             iTeamRepository teamRepository, Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || teamRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.teamRepository = teamRepository;
        this.callback = callback;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadPrimaryTeamBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryTeams();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.SESSION_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.TEAM);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.TEAM,
                cSharedPreference.READ);

        Log.d(TAG, " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n USER ID = " + this.userServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY BITS = " + this.entityBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadTeamsWithRolesFailed(msg));
    }

    /* */
    private void teamsWithRolesMessage(List<cTreeModel> teamsRolesTree) {
        mainThread.post(() -> callback.onReadTeamsWithRolesSucceeded(teamsRolesTree));
    }

    @Override
    public void run() {
        Gson gson = new Gson();

        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
            if ((this.entityBITS & cSharedPreference.ORGANIZATION) != 0) {
                if ((this.entitypermBITS & cSharedPreference.READ) != 0) {
                    this.teamRepository.readTeamsWithRoles(organizationServerID, userServerID,
                            primaryTeamBIT, secondaryTeamBITS, statusBITS,
                            new iTeamRepository.iReadTeamsWithRolesCallback() {
                                @Override
                                public void onReadTeamsWithRolesSucceeded(Map<cTeamModel,
                                        List<cRoleModel>> teamRolesMap) {
                                    List<cTreeModel> teamsRolesTree = new ArrayList<>();
                                    int parentIndex = 0, childIndex;
                                    for (Map.Entry<cTeamModel,
                                            List<cRoleModel>> entry : teamRolesMap.entrySet()) {
                                        /* a team */
                                        cTeamModel teamModel = entry.getKey();
                                        teamsRolesTree.add(new cTreeModel(parentIndex, -1,
                                                0, teamModel));

                                        /* a list of team roles under the team */
                                        Log.d(TAG, "teamRolesMap ==>> " +
                                                gson.toJson(entry.getValue()));
                                        childIndex = parentIndex;
                                        for (cRoleModel roleModel : entry.getValue()) {
                                            childIndex = childIndex + 1;
                                            teamsRolesTree.add(new cTreeModel(childIndex,
                                                    parentIndex, 1, roleModel));
                                        }
                                        /* next parent index */
                                        parentIndex = childIndex + 1;
                                    }


                                    teamsWithRolesMessage(teamsRolesTree);
                                }

                                @Override
                                public void onReadTeamsWithRolesFailed(String msg) {
                                    notifyError(msg);
                                }
                            });
                } else {
                    notifyError("Permission denied! Please contact your administrator");
                }
            } else {
                notifyError("No access to the entity! Please contact your administrator");
            }
        } else {
            notifyError("Error in default settings");
        }
    }
}