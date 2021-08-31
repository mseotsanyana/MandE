package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iEntityRepository {
    void readEntityPermissions(String organizationServerID, String userServerID,
                               int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                               List<Integer> statusBITS,
                               iReadEntityPermissionsCallback callback);

    interface iReadEntityPermissionsCallback {
        void onReadEntityPermissionsSucceeded(List<cTreeModel> treeModels);

        void onReadEntityPermissionsFailed(String msg);
    }
}
