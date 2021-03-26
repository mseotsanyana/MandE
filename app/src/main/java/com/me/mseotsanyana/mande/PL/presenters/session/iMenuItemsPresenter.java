package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

import java.util.List;

public interface iMenuItemsPresenter extends iPresenter {
    /* implemented in either Fragments and/or Activities. Called in PresenterImpl */
    interface View extends iBaseView {
        void onReadMenuItemsFailed(String msg);
        void onReadMenuItemsSucceeded(List<cMenuModel> menuModels);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void readMenuItems();
}

