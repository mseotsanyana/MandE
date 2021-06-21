package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl.cReadUserProfileInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl.cUpdateUserProfileInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iReadUserProfileInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUpdateUserProfileInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserProfilePresenter;
import com.me.mseotsanyana.mande.UTIL.cInputValidation;

public class cUserProfilePresenterImpl extends cAbstractPresenter implements iUserProfilePresenter,
        iReadUserProfileInteractor.Callback, iUpdateUserProfileInteractor.Callback {
    private static String TAG = cUserProfilePresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sessionManagerRepository;
    private final iUserProfileRepository userProfileRepository;

    private final cInputValidation inputValidation;

    public cUserProfilePresenterImpl(iExecutor executor, iMainThread mainThread,
                                     View view,
                                     iSharedPreferenceRepository sessionManagerRepository,
                                     iUserProfileRepository userProfileRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sessionManagerRepository = sessionManagerRepository;
        this.userProfileRepository = userProfileRepository;

        this.inputValidation = new cInputValidation();
    }

    // CREATE

    // READ

    @Override
    public void readUserProfile() {
        iReadUserProfileInteractor readUserInteractor = new cReadUserProfileInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                userProfileRepository,
                this);

        view.showProgress();
        readUserInteractor.execute();
    }

    @Override
    public void onReadUserProfileRetrieved(cUserProfileModel readUserProfile) {
        if(this.view != null) {
            this.view.onReadUserProfileSucceeded(readUserProfile);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadUserProfileFailed(String msg) {
        if(this.view != null) {
            this.view.onReadUserProfileFailed(msg);
            this.view.hideProgress();
        }
    }

    // UPDATE

    @Override
    public void updateUserProfile(cUserProfileModel userProfileModel) {

        iUpdateUserProfileInteractor updateUserInteractor = new cUpdateUserProfileInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                userProfileRepository,
                this,
                userProfileModel);

        view.showProgress();

        updateUserInteractor.execute();
    }

    @Override
    public void onUpdateUserProfileFailed(String msg) {
        if(this.view != null) {
            this.view.onUpdateUserProfileFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUpdateUserProfileRetrieved(String msg) {
        if(this.view != null) {
            this.view.onUpdateUserProfileSucceeded(msg);
            this.view.hideProgress();
        }
    }

    // PRESENTER FUNCTIONS

    @Override
    public void resume() {
        readUserProfile();
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
