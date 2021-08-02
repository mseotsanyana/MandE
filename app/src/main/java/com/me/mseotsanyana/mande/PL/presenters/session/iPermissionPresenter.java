package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iPermissionPresenter extends iPresenter {
    interface View extends iBaseView {
        void onReadPermissionFailed(String msg);
        void onReadPermissionSucceeded(List<cTreeModel> treeModels);
    }
    void readPermissions();
}

