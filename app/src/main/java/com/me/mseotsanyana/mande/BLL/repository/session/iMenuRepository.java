package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iMenuRepository {
    void readMenuPermissions(String organizationServerID, String userServerID,
                             int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                             List<Integer> statusBITS,
                             iReadMenuPermissionsCallback callback);

    interface iReadMenuPermissionsCallback {
        void onReadMenuPermissionsSucceeded(List<cTreeModel> treeModels);

        void onReadMenuPermissionsFailed(String msg);
    }

//    void getMenuModels(long userID, int primaryRole, int secondaryRoles, int statusBITS,
//                         String organizationID, String currentUserID,
//                         iMenuRepository.iReadMenuModelSetCallback callback);
//
//    interface iReadMenuModelSetCallback{
//        void onReadMenuSucceeded(List<cMenuModel> menuModels);
//        void onReadMenuFailed(String msg);
//    }
//
//    Set<cMenuModel> getMenuModelSet(long userID, int primaryRole,
//                                    int secondaryRoles, int statusBITS);
}
