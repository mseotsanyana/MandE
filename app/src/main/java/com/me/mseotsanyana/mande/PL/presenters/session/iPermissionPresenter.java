package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iPermissionPresenter extends iPresenter {
    interface View extends iBaseView {
        void onReadRolePermissionFailed(String msg);
        void onReadRolePermissionSucceeded(List<cTreeModel> treeModels);

        void onUpdateRolePermissionFailed(String msg);
        void onUpdateRolePermissionSucceeded(String msg);

        void onClickUpdateRolePermission(List<cNode> nodes);
        void onClickDeleteRolePermission(String permissionServerID);

    }
    void readRolePermissions();
    void updateRolePermissions(List<cNode> nodes);
}

