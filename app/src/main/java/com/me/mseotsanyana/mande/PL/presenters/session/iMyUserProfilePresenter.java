package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iMyUserProfilePresenter extends iPresenter {
    interface View extends iBaseView {
        void onReadUserProfileFailed(String msg);
        void onReadUserProfileSucceeded(cUserProfileModel userProfileModel);

        void onUpdateUserProfileFailed(String msg);
        void onUpdateUserProfileSucceeded(String msg);
    }

    void readUserProfile();
    void updateUserProfile(cUserProfileModel userProfileModel);
}

