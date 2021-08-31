package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iModuleRepository {
    void readModulePermissions(String organizationServerID, String userServerID,
                               int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                               List<Integer> statusBITS,
                               iReadModulePermissionsCallback callback);

    interface iReadModulePermissionsCallback {
        void onReadModulePermissionsSucceeded(List<cTreeModel> treeModels);

        void onReadModulePermissionsFailed(String msg);
    }

    void updatePermissions(String organizationServerID, String userServerID,
                           int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                           List<Integer> statusBITS, List<cNode> nodes,
                           iUpdatePermissionsCallback callback );

    interface iUpdatePermissionsCallback {
        void onUpdatePermissionsSucceeded(String msg);

        void onReadModulePermissionsFailed(String msg);
    }
}
