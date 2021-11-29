package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.userprofile.Impl.cReadUserProfilesInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.userprofile.Impl.cUpdateUserProfileImageInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.userprofile.Impl.cUploadUserProfilesInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.userprofile.iUserProfilesInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserProfilePresenter;

import java.util.List;

public class cUserProfilesPresenterImpl extends cAbstractPresenter implements iUserProfilePresenter,
        iUserProfilesInteractor.Callback {
    //private static final String TAG = cOrganizationPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iUserProfileRepository userProfileRepository;

    public cUserProfilesPresenterImpl(iExecutor executor, iMainThread mainThread,
                                      View view,
                                      iSharedPreferenceRepository sharedPreferenceRepository,
                                      iUserProfileRepository userProfileRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.userProfileRepository = userProfileRepository;
    }


    // READ USER PROFILES
    @Override
    public void readUserProfiles() {
        iUserProfilesInteractor readUserProfilesInteractor;
        readUserProfilesInteractor = new cReadUserProfilesInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
                userProfileRepository,
                this);

        view.showProgress();
        readUserProfilesInteractor.execute();
    }

    @Override
    public void onReadUserProfilesFailed(String msg) {
        if (this.view != null) {
            this.view.onReadUserProfilesFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadUserProfilesSucceeded(List<cUserProfileModel> userProfileModels) {
        if (this.view != null) {
            this.view.onReadUserProfilesSucceeded(userProfileModels);
            this.view.hideProgress();
        }
    }



    // UPLOAD or UPDATE

    @Override
    public void uploadUserProfilesFromExcel(String filename) {
        iUserProfilesInteractor userProfilesInteractor =
                new cUploadUserProfilesInteractorImpl(executor, mainThread,
                        sharedPreferenceRepository,
                        userProfileRepository,
                        this, filename);

        view.showProgress();
        userProfilesInteractor.execute();
    }

    @Override
    public void onUploadUserProfilesSucceeded(String msg) {
        if (this.view != null) {
            this.view.onUploadUserProfilesSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUploadUserProfilesFailed(String msg) {
        if (this.view != null) {
            this.view.onUploadUserProfilesFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void updateUserProfileImage(String userServerID, byte[] userProfileImageData) {
        iUserProfilesInteractor userProfilesInteractor =
                new cUpdateUserProfileImageInteractorImpl(executor, mainThread,
                        sharedPreferenceRepository,
                        userProfileRepository,
                        this, userServerID, userProfileImageData);

        view.showProgress();
        userProfilesInteractor.execute();
    }

    @Override
    public void onUpdateUserProfileImageSucceeded(String msg) {
        if (this.view != null) {
            this.view.onUpdateUserProfileImageSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUpdateUserProfileImageFailed(String msg) {
        if (this.view != null) {
            this.view.onUpdateUserProfileImageFailed(msg);
            this.view.hideProgress();
        }
    }

    // PRESENTER FUNCTIONS

    @Override
    public void resume() {
        readUserProfiles();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if (this.view != null) {
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
