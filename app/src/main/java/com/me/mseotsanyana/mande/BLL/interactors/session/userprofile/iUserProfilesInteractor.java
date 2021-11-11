package com.me.mseotsanyana.mande.BLL.interactors.session.userprofile;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.List;

public interface iUserProfilesInteractor extends iInteractor {
    interface Callback {
        /* read user profiles use case */
        void onReadUserProfilesFailed(String msg);
        void onReadUserProfilesSucceeded(List<cUserProfileModel> userProfileModels);

        /* upload user profile use case */
        void onUploadUserProfilesSucceeded(String msg);
        void onUploadUserProfilesFailed(String msg);
    }
}
