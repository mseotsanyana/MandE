package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

import java.util.List;

public interface iUserProfilesPresenter extends iPresenter {
    interface View extends iBaseView {
        void onUploadUserProfilesFailed(String msg);
        void onUploadUserProfilesSucceeded(String msg);

        void onReadUserProfilesFailed(String msg);
        void onReadUserProfilesSucceeded (List<cUserProfileModel> userProfileModels);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void uploadUserProfiles();
    void readUserProfiles();
}

