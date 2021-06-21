package com.me.mseotsanyana.mande.BLL.interactors.session.menu.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.menu.iReadMenuItemsInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iMenuRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;

import java.util.List;

public class cReadMenuItemsInteractorImpl extends cAbstractInteractor
        implements iReadMenuItemsInteractor {
    private static String TAG = cReadMenuItemsInteractorImpl.class.getSimpleName();

    private final Callback callback;

    //private final iUserRepository userRepository;
    private final iMenuRepository menuRepository;
    private final iSharedPreferenceRepository sharedPreferencesRepository;
    private final long userID;
    private final int primaryRoleBITS;
    private final int secondaryRoleBITS;
    private final int operationMenuBITS;
    private final int statusMenuBITS;

    private String organizationID;// = "-MXhsXL576t4oWtytAUZ";
    private String currentUserID;// = "U536MGessQYpMdPsPZDttHavUD73";

    public cReadMenuItemsInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                        iSharedPreferenceRepository sharedPreferencesRepository,
                                        iMenuRepository menuRepository,
                                        Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferencesRepository == null || menuRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sharedPreferencesRepository = sharedPreferencesRepository;
        this.menuRepository = menuRepository;
        this.callback = callback;

        userID = 0;
        primaryRoleBITS = 0;
        secondaryRoleBITS = 0;
        operationMenuBITS = 0;
        statusMenuBITS = 0;

//        /* common attributes */
//        this.userID = this.sharedPreferencesRepository.loadUserID();
//        this.primaryRoleBITS = this.sharedPreferencesRepository.loadPrimaryRoleBITS();
//        this.secondaryRoleBITS = this.sharedPreferencesRepository.loadSecondaryRoleBITS();
//
//        /* attributes related to MENU entity */
//        this.operationMenuBITS = this.sharedPreferencesRepository.loadOperationBITS(
//                cBitwise.MENU, cBitwise.SESSION_MODULE);
//        this.statusMenuBITS = this.sharedPreferencesRepository.loadStatusBITS(
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
        this.menuRepository.getMenuModels(userID, primaryRoleBITS, secondaryRoleBITS, statusMenuBITS,
                organizationID, currentUserID, new iMenuRepository.iReadMenuModelSetCallback() {
            @Override
            public void onReadMenuSucceeded(List<cMenuModel> menuModels) {
                postMessage(menuModels);
            }

            @Override
            public void onReadMenuFailed(String msg) {
                notifyError(msg);
            }
        });
    }
}