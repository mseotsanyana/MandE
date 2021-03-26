package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl.cUserLoginInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUserLoginInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iRoleRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iStatusRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserRepository;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserLoginPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cInputValidation;

public class cUserLoginPresenterImpl extends cAbstractPresenter implements iUserLoginPresenter,
        iUserLoginInteractor.Callback {
    private static String TAG = cUserLoginPresenterImpl.class.getSimpleName();

    private View view;
    private final iUserRepository userRepository;
    private final iOrganizationRepository organizationRepository;

    private final iRoleRepository roleRepository;
    private final iStatusRepository statusRepository;
    private final iSessionManagerRepository sessionManagerRepository;

    private final cInputValidation inputValidation;

    public cUserLoginPresenterImpl(iExecutor executor, iMainThread mainThread,
                                   View view,
                                   iUserRepository userRepository,
                                   iOrganizationRepository organizationRepository,
                                   iRoleRepository roleRepository,
                                   iStatusRepository statusRepository,
                                   iSessionManagerRepository sessionManagerRepository) {
        super(executor, mainThread);

        this.view = view;
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.roleRepository = roleRepository;
        this.statusRepository = statusRepository;
        this.sessionManagerRepository = sessionManagerRepository;

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
                executor, mainThread,
                userRepository,
                organizationRepository,
                roleRepository,
                statusRepository,
                sessionManagerRepository,
                this,
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
    public void onUserLoginSucceeded(cUserModel userModel) {
        if(this.view != null) {
            this.view.onUserLoginSucceeded(userModel);
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
