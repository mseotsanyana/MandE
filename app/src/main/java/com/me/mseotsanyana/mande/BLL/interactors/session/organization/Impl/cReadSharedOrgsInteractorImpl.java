package com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iReadSharedOrgsInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.DAL.model.session.cOrganizationModel;

import java.util.ArrayList;
import java.util.Set;

public class cReadSharedOrgsInteractorImpl extends cAbstractInteractor implements iReadSharedOrgsInteractor {
    private static String TAG = cReadSharedOrgsInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iSessionManagerRepository sessionManagerRepository;

    public cReadSharedOrgsInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                         iSessionManagerRepository sessionManagerRepository,
                                         Callback callback) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sessionManagerRepository = sessionManagerRepository;
        this.callback = callback;

    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadSharedOrgsFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(ArrayList<cOrganizationModel> organizationModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSharedOrgsRetrieved(organizationModels);
            }
        });
    }


    @Override
    public void run() {
        Set<cOrganizationModel> organizationModels = sessionManagerRepository.
                loadOrganizationOwners();

        if (organizationModels != null) {
                postMessage(new ArrayList<cOrganizationModel>(organizationModels));
        } else {
            notifyError("Failed to load shared organization preferences !!");
        }
    }
}
