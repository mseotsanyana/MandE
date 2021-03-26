package com.me.mseotsanyana.mande.BLL.interactors.session.menu.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.menu.iReadMenuItemsInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iMenuRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;

import java.util.List;

public class cReadMenuItemsInteractorImpl extends cAbstractInteractor
        implements iReadMenuItemsInteractor {
    private static String TAG = cReadMenuItemsInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private iSessionManagerRepository sessionManagerRepository;
    private final iMenuRepository menuRepository;
    private final long userID;
    private final int primaryRoleBITS;
    private final int secondaryRoleBITS;
    private final int operationMenuBITS;
    private final int statusMenuBITS;

    public cReadMenuItemsInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                        iSessionManagerRepository sessionManagerRepository,
                                        iMenuRepository menuRepository,
                                        Callback callback) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || menuRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sessionManagerRepository = sessionManagerRepository;
        this.menuRepository = menuRepository;
        this.callback = callback;

        /* common attributes */
        this.userID = this.sessionManagerRepository.loadUserID();
        this.primaryRoleBITS = this.sessionManagerRepository.loadPrimaryRoleBITS();
        this.secondaryRoleBITS = this.sessionManagerRepository.loadSecondaryRoleBITS();

        /* attributes related to MENU entity */
        this.operationMenuBITS = this.sessionManagerRepository.loadOperationBITS(
                cBitwise.MENU, cBitwise.SESSION_MODULE);
        this.statusMenuBITS = this.sessionManagerRepository.loadStatusBITS(
                cBitwise.MENU, cBitwise.SESSION_MODULE, cBitwise.READ);

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
        this.menuRepository.getMenuModels(userID, primaryRoleBITS, secondaryRoleBITS,
                statusMenuBITS, new iMenuRepository.iReadMenuModelSetCallback() {
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