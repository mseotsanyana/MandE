package com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iReadOrganizationsInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iReadSharedOrgsInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;

import java.util.ArrayList;
import java.util.Set;

public class cReadOrganizationsInteractorImpl extends cAbstractInteractor implements iReadOrganizationsInteractor {
    private static String TAG = cReadOrganizationsInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iSessionManagerRepository sessionManagerRepository;
    private iOrganizationRepository organizationRepository;

    public cReadOrganizationsInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                            iSessionManagerRepository sessionManagerRepository,
                                            iOrganizationRepository organizationRepository,
                                            Callback callback) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sessionManagerRepository = sessionManagerRepository;
        this.organizationRepository = organizationRepository;
        this.callback = callback;

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
    private void postMessage(ArrayList<cOrganizationModel> organizationModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadOrganizationsRetrieved(organizationModels);
            }
        });
    }


    @Override
    public void run() {
        organizationRepository.readOrganizations(0, 0, 0, 0,
                new iOrganizationRepository.iReadOrganizationsModelSetCallback() {
            @Override
            public void onReadOrganizationsSucceeded(ArrayList<cOrganizationModel> organizationModels) {
                postMessage(organizationModels);
            }

            @Override
            public void onReadOrganizationsFailed(String msg) {
                notifyError(msg);
            }
        });
    }
}
