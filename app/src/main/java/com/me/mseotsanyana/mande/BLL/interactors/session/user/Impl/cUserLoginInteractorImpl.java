package com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUserLoginInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iRoleRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iStatusRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserRepository;
import com.me.mseotsanyana.mande.DAL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.DAL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;

import java.util.HashSet;
import java.util.Set;

public class cUserLoginInteractorImpl extends cAbstractInteractor implements iUserLoginInteractor {
    private static String TAG = cUserLoginInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iUserRepository userRepository;
    private iOrganizationRepository organizationRepository;
    private iRoleRepository roleRepository;
    private iStatusRepository statusRepository;
    private iSessionManagerRepository sessionManagerRepository;

    private String email, password;

    Gson gson = new Gson();

    public cUserLoginInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                    iUserRepository userRepository,
                                    iOrganizationRepository organizationRepository,
                                    iRoleRepository roleRepository,
                                    iStatusRepository statusRepository,
                                    iSessionManagerRepository sessionManagerRepository,
                                    Callback callback,
                                    String email, String password) {
        super(threadExecutor, mainThread);

        if (userRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }
        this.sessionManagerRepository = sessionManagerRepository;
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.roleRepository = roleRepository;
        this.statusRepository = statusRepository;
        this.callback = callback;

        this.email = email;
        this.password = password;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserLoginFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(cUserModel userModel) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserLoginSucceeded(userModel);
            }
        });
    }

    @Override
    public void run() {

        /* get the logged in user */
        cUserModel userModel = userRepository.getUserByEmailPassword(email, password);

        Gson gson = new Gson();
        Log.d(TAG,"USER MODEL: "+gson.toJson(userModel));


        if (userModel != null) {
            if (!userModel.getRoleModelSet().isEmpty()) {
                /* delete all shared preferences */
                sessionManagerRepository.deleteSettings();

                /* save user/owner ID */
                sessionManagerRepository.saveUserID(userModel.getUserID());
                /* save owner organization ID */
                sessionManagerRepository.saveOrganizationID(userModel.getOrganizationID());
                /* compute and save user primary role bits */
                sessionManagerRepository.savePrimaryRoleBITS(userModel);
                /* compute and save user secondary role bits */
                sessionManagerRepository.saveSecondaryRoleBITS(userModel);
                /* save default permission bits */
                sessionManagerRepository.saveDefaultPermsBITS(cBitwise.OWNER);
                /* set default status bits */
                sessionManagerRepository.saveDefaultStatusBITS(cBitwise.ACTIVATED);

                /* compute and save entity, operation and statuses bits for the user */
                Set<cPermissionModel> permissionModelSet = new HashSet<>();
                for (cRoleModel roleModel : userModel.getRoleModelSet()) {
                    Set<cPermissionModel> permissionSet = roleRepository.getPermissionSetByRoleID(
                            roleModel.getRoleID(), roleModel.getOrganizationID());
                    permissionModelSet.addAll(permissionSet);
                }

                if (!permissionModelSet.isEmpty()) {
                    sessionManagerRepository.savePermissionBITS(permissionModelSet);
                    /* compute and save the status and role sets */
                    sessionManagerRepository.saveStatusSet(statusRepository.getStatusSet());
                    sessionManagerRepository.saveRoleSet(roleRepository.getRoleModelSet());
                    /* save the individual and organization owners */
                    sessionManagerRepository.saveIndividualOwners(userRepository.getOwnerSet());
                    sessionManagerRepository.saveOrganizationOwners(
                            organizationRepository.getOrganizationSet());

                    /* save the shared preferences */
                    sessionManagerRepository.commitSettings();
                } else {
                    notifyError("Login failed since there are no roles assigned !!");
                }

                postMessage(userModel);
            } else {
                notifyError("Login failed since there are no privileges assigned !!");
            }
        } else {
            notifyError("Login failed due to invalid user login details !!");
        }
    }
}