package com.me.mseotsanyana.mande.BLL.interactors.session.user;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.List;

public interface iUserSignUpInteractor extends iInteractor {
    interface Callback{
        void onUserSignUpFailed(String msg);
        void onUserSignUpSucceeded(String msg);
    }
}
