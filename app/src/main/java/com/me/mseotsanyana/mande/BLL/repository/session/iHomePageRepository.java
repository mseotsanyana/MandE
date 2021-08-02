package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.List;

public interface iHomePageRepository {
    void loadHomePage(boolean isPermissionLoaded, iSharedPreferenceRepository sharedPreferenceRepository, iHomePageCallback callback);

    interface iHomePageCallback {
        void onReadUserProfileSucceeded(cUserProfileModel userProfileModel);
        void onReadMenuItemsSucceeded(List<cMenuModel> menuModels);
        void onDefaultHomePageSucceeded(List<cMenuModel> menuModels);
        void onReadHomePageFailed(String msg);
    }
}
