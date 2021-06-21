package com.me.mseotsanyana.mande.BLL.interactors.session.user;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

public interface iReadUserProfileInteractor extends iInteractor {
    interface Callback{
        void onReadUserProfileFailed(String msg);
        void onReadUserProfileRetrieved(cUserProfileModel userProfileModel);
    }
}
