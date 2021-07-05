package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.List;

public interface iHomePageRepository {
    void updateHomePageModels(String userServerID, String orgServerID, int primaryTeamBIT,
                              List<Integer> secondaryTeamBITS, int statusBITS, List<Integer> statuses, int permBITS,
                              iHomePageCallback callback);

    interface iHomePageCallback {
        void onReadUserProfileSucceeded(cUserProfileModel userProfileModel);
        void onReadMenuItemsSucceeded();
        void onDefaultHomePageSucceeded(cUserProfileModel userProfileModel,
                                        List<cMenuModel> menuModels);
        void onReadHomePageFailed(String msg);
    }
}
