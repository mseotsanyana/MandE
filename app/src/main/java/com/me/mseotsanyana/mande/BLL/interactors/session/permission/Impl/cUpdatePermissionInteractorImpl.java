package com.me.mseotsanyana.mande.BLL.interactors.session.permission.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.permission.iUpdatePermissionInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iModuleRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cUpdatePermissionInteractorImpl extends cAbstractInteractor implements
        iUpdatePermissionInteractor {
    private static final String TAG = cUpdatePermissionInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iModuleRepository moduleRepository;

    // permission data
    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    private List<cNode> nodes;

    public cUpdatePermissionInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                           iSharedPreferenceRepository sharedPreferenceRepository,
                                           iModuleRepository moduleRepository,
                                           Callback callback, List<cNode> nodes) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || moduleRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.moduleRepository = moduleRepository;
        this.nodes = nodes;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadPrimaryTeamBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryTeams();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.SESSION_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.PERMISSION);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.PERMISSION,
                cSharedPreference.UPDATE);

        Log.d(TAG, " \n USER ID = " + this.userServerID +
                " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onUpdatePermissionFailed(msg));
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(() -> callback.onUpdatePermissionSucceeded(msg));
    }

    @Override
    public void run() {
        if ((this.entityBITS & cSharedPreference.PERMISSION) != 0) {
            if ((this.entitypermBITS & cSharedPreference.UPDATE) != 0) {
                this.moduleRepository.updatePermissions(organizationServerID,
                        userServerID, primaryTeamBIT, secondaryTeamBITS, statusBITS, nodes,
                        new iModuleRepository.iUpdatePermissionsCallback() {
                            @Override
                            public void onUpdatePermissionsSucceeded(String msg) {
                                postMessage(msg);
                            }

                            @Override
                            public void onReadModulePermissionsFailed(String msg) {
                                notifyError(msg);
                            }
                        });
            } else {
                notifyError("Permission denied! Please contact your administrator");
            }
        } else {
            notifyError("No access to the entity! Please contact your administrator");
        }
    }
}