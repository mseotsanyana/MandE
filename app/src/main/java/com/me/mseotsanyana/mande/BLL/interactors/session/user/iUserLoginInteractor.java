package com.me.mseotsanyana.mande.BLL.interactors.session.user;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;

public interface iUserLoginInteractor extends iInteractor {
    interface Callback{
        void onUserLoginFailed(String msg);
        void onUserLoginSucceeded(cUserModel userModel);
    }
}
