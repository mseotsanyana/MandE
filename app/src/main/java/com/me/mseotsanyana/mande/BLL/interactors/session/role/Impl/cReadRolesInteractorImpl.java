package com.me.mseotsanyana.mande.BLL.interactors.session.role.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.menu.iReadMenuItemsInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iRoleRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;

import java.util.List;

public class cReadRolesInteractorImpl extends cAbstractInteractor
        implements iReadMenuItemsInteractor {
    private static String TAG = cReadRolesInteractorImpl.class.getSimpleName();

    private final Callback callback;

    //private final iUserRepository userRepository;
    private final iRoleRepository roleRepository;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final long userID;
    private final int primaryTeamBIT;
    private final int secondaryTeamBITS;
    private final int operationBITS;
    private final int statusBITS;


    public cReadRolesInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                    iSharedPreferenceRepository sharedPreferencesRepository,
                                    iRoleRepository roleRepository,
                                    Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferencesRepository == null || roleRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sharedPreferenceRepository = sharedPreferencesRepository;
        this.roleRepository = roleRepository;
        this.callback = callback;

        userID = 0;
        primaryTeamBIT = 0;
        secondaryTeamBITS = 0;
        operationBITS = 0;
        statusBITS = 0;

//        /* common attributes */
//        this.userID = this.sharedPreferencesRepository.loadUserID();
//        this.primaryTeamBIT = this.sharedPreferencesRepository.loadPrimaryRoleBITS();
//        this.secondaryTeamBITS = this.sharedPreferencesRepository.loadSecondaryRoleBITS();
//
//        /* attributes related to MENU entity */
//        this.operationBITS = this.sharedPreferencesRepository.loadOperationBITS(
//                cBitwise.MENU, cBitwise.SESSION_MODULE);
//        this.statusBITS = this.sharedPreferencesRepository.loadStatusBITS(
//                cBitwise.MENU, cBitwise.SESSION_MODULE, cBitwise.READ);

    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadMenuItemsFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(List<cMenuModel> menuModelSet) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadMenuItemsSucceeded(menuModelSet);
            }
        });
    }

    @Override
    public void run() {
        /*this.roleRepository.getMenuModels(userID, primaryTeamBIT, secondaryTeamBITS, statusBITS,
                new iMenuRepository.iReadMenuModelSetCallback() {
            @Override
            public void onReadMenuSucceeded(List<cMenuModel> menuModels) {
                postMessage(menuModels);
            }

            @Override
            public void onReadMenuFailed(String msg) {
                notifyError(msg);
            }
        });*/
    }
}