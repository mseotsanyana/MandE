package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

import java.util.List;

public interface iHomePagePresenter extends iPresenter {
    /* implemented in either Fragments and/or Activities. Called in PresenterImpl */
    interface View extends iBaseView {
        void onReadUserProfileSucceeded(cUserProfileModel userProfileModel);
        void onReadMenuItemsSucceeded(List<cMenuModel> menuModels);
        void onDefaultHomePageSucceeded(cUserProfileModel userProfileModel,
                                        List<cMenuModel> menuModels);
        void onReadHomePageFailed(String msg);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void updateHomePage();
}

