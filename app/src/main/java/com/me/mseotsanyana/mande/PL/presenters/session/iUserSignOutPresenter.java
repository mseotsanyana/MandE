package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iUserSignOutPresenter extends iPresenter {
    /* implemented in either Fragments and/or Activities. Called in PresenterImpl */
    interface View extends iBaseView {
        void onUserSignOutFailed(String msg);
        void onUserSignOutSucceeded(String msg);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void signOutWithEmailAndPassword();
}

