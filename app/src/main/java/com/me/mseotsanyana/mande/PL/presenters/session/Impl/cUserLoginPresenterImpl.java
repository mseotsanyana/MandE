package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl.cUserLoginInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUserLoginInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserLoginPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cInputValidation;

public class cUserLoginPresenterImpl extends cAbstractPresenter implements iUserLoginPresenter,
        iUserLoginInteractor.Callback {

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iPermissionRepository privilegeRepository;
    private final iUserProfileRepository userProfileRepository;

    private final cInputValidation inputValidation;

    public cUserLoginPresenterImpl(iExecutor executor, iMainThread mainThread, View view,
                                   iSharedPreferenceRepository sharedPreferenceRepository,
                                   iPermissionRepository privilegeRepository,
                                   iUserProfileRepository userProfileRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.privilegeRepository = privilegeRepository;
        this.userProfileRepository = userProfileRepository;

        this.inputValidation = new cInputValidation();
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password) {
        if (!inputValidation.isEditTextFilled(view.getEmailEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextFilled(view.getPasswordEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextEmail(view.getEmailEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        iUserLoginInteractor userLoginInteractor = new cUserLoginInteractorImpl(
                executor, mainThread,this,
                sharedPreferenceRepository,
                privilegeRepository,
                userProfileRepository,
                email, password);

        view.showProgress();

        userLoginInteractor.execute();
    }

    @Override
    public void onUserLoginFailed(String msg) {
        if(this.view != null) {
            this.view.onUserLoginFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUserLoginSucceeded(String msg) {
        if(this.view != null) {
            this.view.onUserLoginSucceeded(msg);
            this.view.hideProgress();
        }
    }

    // general presentation methods

    @Override
    public void resume() {

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
