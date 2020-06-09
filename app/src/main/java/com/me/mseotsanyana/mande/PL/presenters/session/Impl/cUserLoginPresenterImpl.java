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
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserLoginPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cInputValidation;

public class cUserLoginPresenterImpl extends cAbstractPresenter implements iUserLoginPresenter,
        iUserLoginInteractor.Callback {
    private static String TAG = cUserLoginPresenterImpl.class.getSimpleName();

    private View view;
    private iUserRepository userRepository;
    private iOrganizationRepository organizationRepository;
    private iRoleRepository roleRepository;
    private iStatusRepository statusRepository;
    private iSessionManagerRepository sessionManagerRepository;

    private cInputValidation inputValidation;

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
    public void userLogin(String email, String password) {
        //progressBar.setVisibility(View.VISIBLE);

        if (!inputValidation.isInputEditTextFilled(
                view.getEmailTextInputEditText(), view.getEmailTextInputLayout(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isInputEditTextEmail(
                view.getEmailTextInputEditText(), view.getEmailTextInputLayout(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(
                view.getPasswordTextInputEditText(), view.getPasswordTextInputLayout(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        iUserLoginInteractor userLoginInteractor = new cUserLoginInteractorImpl(
                executor,
                mainThread,
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

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     *
    private void localLogin() {
        if (!inputValidation.isInputEditTextFilled(view.getEmailTextInputEditText(),
                view.getEmailTextInputLayout(), getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(view.getEmailTextInputEditText(),
                view.getEmailTextInputLayout(), getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(view.getPasswordTextInputEditText(),
                view.getPasswordTextInputLayout(), getString(R.string.error_message_email))) {
            return;
        }

        userLoginPresenter.userLogin();

        // check whether the user is in the database
        cUserDomain userDomain = userHandler.getUserByEmailPassword(emailTextInputEditText.getText().toString().trim(),
                passwordTextInputEditText.getText().toString().trim());

        //boolean isUser = userHandler.checkUser(emailTextInputEditText.getText().toString().trim());

        if (userDomain != null) {

            emptyInputEditText();

            session.setUserSession(getActivity(), userDomain);
            //Toast.makeText(getActivity(), "MEMBERSHIPS = "+sessionManager.getMemberships(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), "ROLES = "+sessionManager.getLoggedInUserRoles().get(0).getName(), Toast.LENGTH_SHORT).show();

            pushFragment(cLogFrameFragment.newInstance(session));

        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(getView(), getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }*/
}
