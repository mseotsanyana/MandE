package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl.cUserSignUpInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUserSignUpInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserSignUpPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cInputValidation;


public class cUserSignUpPresenterImpl extends cAbstractPresenter implements iUserSignUpPresenter,
        iUserSignUpInteractor.Callback {
    //private static String TAG = cUserSignUpPresenterImpl.class.getSimpleName();

    private View view;
    private final iUserProfileRepository userProfileRepository;

    private final cInputValidation inputValidation;

    public cUserSignUpPresenterImpl(iExecutor executor, iMainThread mainThread,
                                    View view,
                                    iUserProfileRepository userProfileRepository) {
        super(executor, mainThread);

        this.view = view;
        this.userProfileRepository = userProfileRepository;

        this.inputValidation = new cInputValidation();
    }


    @Override
    public void createUserWithEmailAndPassword(cUserProfileModel userProfileModel) {
        if (!inputValidation.isEditTextFilled(view.getEmailEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextFilled(view.getPasswordEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextFilled(view.getConfirmPasswordEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextEmail(view.getEmailEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextMatches(view.getPasswordEditText(),
                view.getConfirmPasswordEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        iUserSignUpInteractor userSignUpInteractor = new cUserSignUpInteractorImpl(
                executor,
                mainThread,
                userProfileRepository,
                this,
                userProfileModel);

        view.showProgress();

        userSignUpInteractor.execute();
    }

    @Override
    public void onUserSignUpFailed(String msg) {
        if(this.view != null) {
            this.view.onUserSignUpFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUserSignUpSucceeded(String msg) {
        if(this.view != null) {
            this.view.onUserSignUpSucceeded(msg);
            this.view.hideProgress();
        }
    }

    /*============================= General Presenter methods ============================= */

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
