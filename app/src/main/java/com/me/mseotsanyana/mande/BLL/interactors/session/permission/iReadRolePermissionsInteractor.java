package com.me.mseotsanyana.mande.BLL.interactors.session.permission;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;
import java.util.Map;

public interface iReadRolePermissionsInteractor extends iInteractor {
    interface Callback{
        void onReadRolePermissionsFailed(String msg);
        void onReadRolePermissionsRetrieved(List<cTreeModel> treeModels);
    }
}
