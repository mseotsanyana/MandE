package com.me.mseotsanyana.mande.PL.presenters.session;

import android.widget.EditText;
import android.widget.TextView;

import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iUserLoginPresenter extends iPresenter {
    /* implemented in either Fragments and/or Activities. Called in PresenterImpl */
    interface View extends iBaseView {
        void onUserLoginSucceeded(String msg);
        void onUserLoginFailed(String msg);

        EditText getEmailEditText();
        EditText getPasswordEditText();
        TextView getForgotPasswordTextView();

        String getResourceString(int resourceID);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void signInWithEmailAndPassword(String email, String password);
}

