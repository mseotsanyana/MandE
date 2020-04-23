package com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUserLoginInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iRoleRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserRepository;
import com.me.mseotsanyana.mande.DAL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.DAL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;

import java.util.HashSet;
import java.util.Set;

public class cUserLoginInteractorImpl extends cAbstractInteractor implements iUserLoginInteractor {
    private static String TAG = cUserLoginInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iUserRepository userRepository;
    private iRoleRepository roleRepository;
    private iSessionManagerRepository sessionManagerRepository;

    private String email, password;

    Gson gson = new Gson();

    public cUserLoginInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                    iSessionManagerRepository sessionManagerRepository,
                                    iUserRepository userRepository,
                                    iRoleRepository roleRepository,
                                    Callback callback,
                                    String email, String password) {
        super(threadExecutor, mainThread);

        if (userRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }
        this.sessionManagerRepository = sessionManagerRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

        if (userModel != null) {
            if (!userModel.getRoleModelSet().isEmpty()) {
                /* delete all shared preferences */
                sessionManagerRepository.deleteSettings();

                /* compute and save primary role bits for the user */
                sessionManagerRepository.savePrimaryRoleBITS(userModel);

                /* compute and save secondary role bits for the user */
                sessionManagerRepository.saveSecondaryRoleBITS(userModel);

                /* compute and save entity, operation and statuses bits for the user */
                Set<cPermissionModel> permissionModelSet = new HashSet<>();
                for (cRoleModel roleModel : userModel.getRoleModelSet()) {
                    Set<cPermissionModel> permissionSet = roleRepository.getPermissionSetByRoleID(
                            roleModel.getRoleID(), roleModel.getOrganizationID());
                    permissionModelSet.addAll(permissionSet);
                }

                if (!permissionModelSet.isEmpty()) {
                    sessionManagerRepository.savePermissionBITS(permissionModelSet);
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