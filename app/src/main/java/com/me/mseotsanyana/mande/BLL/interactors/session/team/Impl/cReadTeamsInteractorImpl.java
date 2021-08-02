package com.me.mseotsanyana.mande.BLL.interactors.session.team.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.cInteractorUtils;
import com.me.mseotsanyana.mande.BLL.interactors.session.team.iReadTeamInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iTeamRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import java.util.List;

public class cReadTeamsInteractorImpl extends cAbstractInteractor
        implements iReadTeamInteractor {
    private static final String TAG = cReadTeamsInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iTeamRepository teamRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadTeamsInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                    iSharedPreferenceRepository sharedPreferenceRepository,
                                    iTeamRepository teamRepository,
                                    Callback callback) {
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
                cSharedPreference.SESSION_MODULE, cSharedPreference.ORGANIZATION);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.ORGANIZATION,
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
        mainThread.post(() -> callback.onReadTeamsFailed(msg));
    }

    /* */
    private void teamsMessage(List<cTeamModel> teamModels) {
        mainThread.post(() -> callback.onReadTeamsSucceeded(teamModels));
    }

    @Override
    public void run() {
        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
            if ((this.entityBITS & cSharedPreference.ORGANIZATION) != 0) {
                if ((this.entitypermBITS & cSharedPreference.READ) != 0) {

                    this.teamRepository.readTeams(organizationServerID, userServerID,
                            primaryTeamBIT, secondaryTeamBITS, statusBITS,
                            new iTeamRepository.iReadTeamsCallback() {
                                @Override
                                public void onReadTeamsSucceeded(List<cTeamModel> teamModels) {
                                    teamsMessage(teamModels);
                                }

                                @Override
                                public void onReadTeamsFailed(String msg) {
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