package com.me.mseotsanyana.mande.PL.presenters.session;

import android.widget.EditText;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iUserSignUpPresenter extends iPresenter {
    /* implemented in either Fragments and/or Activities. Called in PresenterImpl */
    interface View extends iBaseView {
        void onUserSignUpFailed(String msg);
        void onUserSignUpSucceeded(String msg);

        EditText getEmailEditText();
        EditText getPasswordEditText();
        EditText getConfirmPasswordEditText();

        String getResourceString(int resourceID);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void createUserWithEmailAndPassword(String firstName, String surname, String email, String password);
}

