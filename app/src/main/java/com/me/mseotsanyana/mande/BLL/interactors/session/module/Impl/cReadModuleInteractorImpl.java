package com.me.mseotsanyana.mande.BLL.interactors.session.module.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.module.iReadModuleInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cReadModuleInteractorImpl /*extends cAbstractInteractor implements iReadModuleInteractor*/ {
//    private static final String TAG = cReadModuleInteractorImpl.class.getSimpleName();
//
//    private final Callback callback;
//    private final iModuleRepository moduleRepository;
//
//    // permission data
//    private final String userServerID;
//    private final String organizationServerID;
//    private final int primaryTeamBIT;
//    private final List<Integer> secondaryTeamBITS;
//    private final List<Integer> statusBITS;
//
//    private final int entityBITS;
//    private final int entitypermBITS;
//
//    public cReadModuleInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
//                                     iSharedPreferenceRepository sharedPreferenceRepository,
//                                     iModuleRepository moduleRepository,
//                                     Callback callback) {
//        super(threadExecutor, mainThread);
//
//        if (sharedPreferenceRepository == null || moduleRepository == null ||
//                callback == null) {
//            throw new IllegalArgumentException("Arguments can not be null!");
//        }
//
//        this.callback = callback;
//        this.moduleRepository = moduleRepository;
//
//        // load user shared preferences
//        this.userServerID = sharedPreferenceRepository.loadUserID();
//        this.organizationServerID = sharedPreferenceRepository.loadOrganizationID();
//        this.primaryTeamBIT = sharedPreferenceRepository.loadPrimaryTeamBIT();
//        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryTeams();
//
//        // load entity shared preferences
//        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
//                cSharedPreference.SESSION_MODULE);
//        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
//                cSharedPreference.SESSION_MODULE, cSharedPreference.PERMISSION);
//        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
//                cSharedPreference.SESSION_MODULE, cSharedPreference.PERMISSION,
//                cSharedPreference.READ);
//
//        Log.d(TAG, " \n USER ID = " + this.userServerID +
//                " \n ORGANIZATION ID = " + this.organizationServerID +
//                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
//                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
//                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
//                " \n OPERATION STATUSES = " + this.statusBITS);
//    }
//
//    /* */
//    private void notifyError(String msg) {
//        mainThread.post(() -> callback.onReadModuleFailed(msg));
//    }
//
//    /* */
//    private void postMessage(List<cTreeModel> treeModels) {
//        mainThread.post(() -> callback.onReadModuleSucceeded(treeModels));
//    }


    //@Override
    public void run() {
//        if ((this.entityBITS & cSharedPreference.PERMISSION) != 0) {
//            if ((this.entitypermBITS & cSharedPreference.READ) != 0) {
//                this.moduleRepository.readModulePermissions(organizationServerID,
//                        userServerID, primaryTeamBIT, secondaryTeamBITS, statusBITS,
//                        new iModuleRepository.iReadModulePermissionsCallback() {
//                            @Override
//                            public void onReadModulePermissionsSucceeded(List<cTreeModel>
//                                                                                 treeModels) {
//                                postMessage(treeModels);
//                            }
//
//                            @Override
//                            public void onReadModulePermissionsFailed(String msg) {
//                                notifyError(msg);
//                            }
//                        });
//            } else {
//                notifyError("Permission denied! Please contact your administrator");
//            }
//        } else {
//            notifyError("No access to the entity! Please contact your administrator");
        }
}