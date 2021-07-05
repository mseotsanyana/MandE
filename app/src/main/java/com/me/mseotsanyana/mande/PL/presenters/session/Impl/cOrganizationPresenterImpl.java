package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl.cCreateOrganizationInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl.cReadOrganizationsInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iCreateOrganizationInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iReadOrganizationsInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iOrganizationPresenter;
import com.me.mseotsanyana.mande.UTIL.cInputValidation;

import java.util.ArrayList;
import java.util.List;

public class cOrganizationPresenterImpl extends cAbstractPresenter implements iOrganizationPresenter,
        iCreateOrganizationInteractor.Callback, iReadOrganizationsInteractor.Callback {
    private static String TAG = cOrganizationPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sessionManagerRepository;
    private final iOrganizationRepository organizationRepository;

    private final cInputValidation inputValidation;

    public cOrganizationPresenterImpl(iExecutor executor, iMainThread mainThread,
                                      View view,
                                      iSharedPreferenceRepository sessionManagerRepository,
                                      iOrganizationRepository organizationRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sessionManagerRepository = sessionManagerRepository;
        this.organizationRepository = organizationRepository;

        this.inputValidation = new cInputValidation();
    }

    // CREATE

    @Override
    public void createOrganization(cOrganizationModel organizationModel) {
        /*if (!inputValidation.isEditTextFilled(view.getNameEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextFilled(view.getEmailEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextFilled(view.getWebsiteEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextEmail(view.getEmailEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }*/

        iCreateOrganizationInteractor createOrganizationInteractor = new cCreateOrganizationInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                organizationRepository,
                this,
                organizationModel);

        view.showProgress();

        createOrganizationInteractor.execute();
    }

    @Override
    public void onOrganizationCreated(String msg) {
        if(this.view != null) {
            this.view.onCreateOrganizationSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onOrganizationCreateFailed(String msg) {
        if(this.view != null) {
            this.view.onCreateOrganizationFailed(msg);
            this.view.hideProgress();
        }
    }

    // READ

    @Override
    public void readOrganizations() {
        iReadOrganizationsInteractor readOrganizationsInteractor = new cReadOrganizationsInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                organizationRepository,
                this);

        view.showProgress();
        readOrganizationsInteractor.execute();
    }

    @Override
    public void onReadOrganizationsRetrieved(List<cOrganizationModel> organizationModels) {
        if(this.view != null) {
            this.view.onReadOrganizationsSucceeded(organizationModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadOrganizationsFailed(String msg) {
        if(this.view != null) {
            this.view.onReadOrganizationsFailed(msg);
            this.view.hideProgress();
        }
    }

    // PRESENTER FUNCTIONS

    @Override
    public void resume() {
        readOrganizations();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if(this.view != null){
            this.view.hideProgress();
        }
    }

    @Override
    public void destroy() {
        this.view = null;
    }

    @Override
    public void onError(String message) {

    }
}
