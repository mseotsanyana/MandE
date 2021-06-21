package com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iCreateOrganizationInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;

public class cCreateOrganizationInteractorImpl extends cAbstractInteractor
        implements iCreateOrganizationInteractor {
    private static String TAG = cCreateOrganizationInteractorImpl.class.getSimpleName();

    private iSharedPreferenceRepository sessionManagerRepository;
    private iOrganizationRepository organizationRepository;
    private Callback callback;
    private cOrganizationModel organizationModel;

    public cCreateOrganizationInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                             iSharedPreferenceRepository sessionManagerRepository,
                                             iOrganizationRepository organizationRepository,
                                             Callback callback, cOrganizationModel organizationModel) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || organizationRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sessionManagerRepository = sessionManagerRepository;
        this.organizationRepository = organizationRepository;
        this.callback = callback;
        this.organizationModel = organizationModel;

        /* add common attributes
        this.organizationModel.setOwnerID(sessionManagerRepository.loadUserID());
        this.organizationModel.setOrgID(sessionManagerRepository.loadOrganizationID());
        this.organizationModel.setGroupBITS(sessionManagerRepository.loadPrimaryRoleBITS()|
                sessionManagerRepository.loadSecondaryRoleBITS());
        this.organizationModel.setPermsBITS(sessionManagerRepository.loadDefaultPermsBITS());
        this.organizationModel.setStatusBITS(sessionManagerRepository.loadDefaultStatusBITS());
        this.organizationModel.setCreatedDate(new Date());
        this.organizationModel.setModifiedDate(new Date());
        this.organizationModel.setSyncedDate(new Date());*/
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onOrganizationCreateFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onOrganizationCreated(msg);
            }
        });
    }

    @Override
    public void run() {

        /* create a new organization object and insert it */
        organizationRepository.createOrganization(organizationModel,
                new iOrganizationRepository.iOrganizationRepositoryCallback() {
            @Override
            public void onCreateOrganizationSucceeded(String msg) {
                postMessage(msg);
            }

            @Override
            public void onCreateOrganizationFailed(String msg) {
                notifyError(msg);
            }
        });
    }
}
