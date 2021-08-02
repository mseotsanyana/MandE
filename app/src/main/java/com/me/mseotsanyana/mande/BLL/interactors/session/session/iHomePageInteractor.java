package com.me.mseotsanyana.mande.BLL.interactors.session.session;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.List;

public interface iHomePageInteractor extends iInteractor {
    interface Callback {
        void onReadUserProfileSucceeded(cUserProfileModel userProfileModel);
        void onReadMenuItemsSucceeded(List<cMenuModel> menuModels);
        void onDefaultHomePageSucceeded(List<cMenuModel> menuModels);
        void onReadHomePageFailed(String msg);
    }
}
