package com.me.mseotsanyana.mande.PL.presenters.session;

//import android.support.design.widget.TextInputEditText;
//import android.support.design.widget.TextInputLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iUserLoginPresenter extends iPresenter {
    /* implemented in either Fragments and/or Activities. Called in PresenterImpl */
    interface View extends iBaseView {
        void onUserLoginFailed(String msg);
        void onUserLoginSucceeded(cUserModel userDomain);

        TextInputLayout getEmailTextInputLayout();
        TextInputLayout getPasswordTextInputLayout();
        TextInputEditText getEmailTextInputEditText();
        TextInputEditText getPasswordTextInputEditText();
        TextView getForgotPasswordTextView();

        String getResourceString(int resourceID);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void userLogin(String email, String password);
}

