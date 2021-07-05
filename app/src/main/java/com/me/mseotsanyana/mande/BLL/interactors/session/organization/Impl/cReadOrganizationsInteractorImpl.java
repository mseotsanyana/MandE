package com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iReadOrganizationsInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import java.util.List;

public class cReadOrganizationsInteractorImpl extends cAbstractInteractor implements
        iReadOrganizationsInteractor {
    private static final String TAG = cReadOrganizationsInteractorImpl.class.getSimpleName();

    private final Callback callback;
    //private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iOrganizationRepository organizationRepository;


    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeams;
    private final List<Integer> statuses;

    private final int entitypermBITS;
    private final int unixpermBITS;

    public cReadOrganizationsInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                            iSharedPreferenceRepository sharedPreferenceRepository,
                                            iOrganizationRepository organizationRepository,
                                            Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || organizationRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        //this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.organizationRepository = organizationRepository;
        this.callback = callback;

        // load shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadPrimaryTeamBIT();
        this.secondaryTeams = sharedPreferenceRepository.loadSecondaryTeams();
        if (this.secondaryTeams.isEmpty()) {
            this.secondaryTeams.add(0);
        }
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.ORGANIZATION);
        this.statuses = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.ORGANIZATION,
                cSharedPreference.READ);

        this.unixpermBITS = sharedPreferenceRepository.loadUnixPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.ORGANIZATION);

        Log.d(TAG, " \n USER ID = " + this.userServerID +
                " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeams +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statuses +
                " \n UNIX PERMISSIONS = " + this.unixpermBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadOrganizationsFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(List<cOrganizationModel> organizationModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadOrganizationsRetrieved(organizationModels);
            }
        });
    }

    @Override
    public void run() {
        organizationRepository.readOrganizations(organizationServerID, userServerID,
                primaryTeamBIT, secondaryTeams, statuses, new iOrganizationRepository.iReadOrganizationsCallback() {
                    @Override
                    public void onReadOrganizationsSucceeded(List<cOrganizationModel> organizationModels) {
                        postMessage(organizationModels);
                    }

                    @Override
                    public void onReadOrganizationsFailed(String msg) {
                        notifyError(msg);
                    }
                });
    }
}
