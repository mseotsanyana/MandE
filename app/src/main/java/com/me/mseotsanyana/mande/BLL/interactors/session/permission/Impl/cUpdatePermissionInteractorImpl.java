package com.me.mseotsanyana.mande.BLL.interactors.session.permission.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.permission.iUpdateRolePermissionInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.treeadapterlibrary.cNode;

import java.util.List;

public class cUpdatePermissionInteractorImpl extends cAbstractInteractor implements
        iUpdateRolePermissionInteractor {
    private static final String TAG = cUpdatePermissionInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iPermissionRepository permissionRepository;

    // permission data
    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    private final List<cNode> nodes;

    public cUpdatePermissionInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                           iSharedPreferenceRepository sharedPreferenceRepository,
                                           iPermissionRepository permissionRepository,
                                           Callback callback, List<cNode> nodes) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || permissionRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.permissionRepository = permissionRepository;
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
        mainThread.post(() -> callback.onUpdateRolePermissionFailed(msg));
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(() -> callback.onUpdateRolePermissionSucceeded(msg));
    }

    @Override
    public void run() {
        if ((this.entityBITS & cSharedPreference.PERMISSION) != 0) {
            if ((this.entitypermBITS & cSharedPreference.UPDATE) != 0) {
                this.permissionRepository.updateRolePermissions(organizationServerID,
                        userServerID, primaryTeamBIT, secondaryTeamBITS, statusBITS, nodes,
                        new iPermissionRepository.iUpdateRolePermissionsCallback() {
                            @Override
                            public void onUpdateRolePermissionsSucceeded(String msg) {
                                postMessage(msg);
                            }

                            @Override
                            public void onUpdateRolePermissionsFailed(String msg) {
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