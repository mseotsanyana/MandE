package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iMenuPresenter extends iPresenter {
    interface View extends iBaseView {
        void onReadMenuFailed(String msg);
        void onReadMenuSucceeded(List<cTreeModel> treeModels);
    }

    void readMenuItems();
}

