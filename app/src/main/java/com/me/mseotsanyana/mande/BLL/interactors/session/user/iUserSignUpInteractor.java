package com.me.mseotsanyana.mande.BLL.interactors.session.user;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iUserSignUpInteractor extends iInteractor {
    interface Callback{
        void onUserSignUpFailed(String msg);
        void onUserSignUpSucceeded(String msg);
    }
}
