package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.List;

public interface iUserProfileAndMenuItemsRepository {
    void updateHomePageModels(String userServerID, String orgServerID, int primaryTeamBIT,
                              int secondaryTeamBITS, int statusBITS, List<Integer> statuses, int permBITS,
                              iUserProfileAndMenuItemsCallback callback);

    interface iUserProfileAndMenuItemsCallback{
        void onReadUserProfileSucceeded(cUserProfileModel userProfileModel);
        void onReadMenuItemsSucceeded();
        void onDefaultHomePageSucceeded(cUserProfileModel userProfileModel,
                                        List<cMenuModel> menuModels);
        void onReadHomePageFailed(String msg);
    }
}
