package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.List;

public interface iRoleRepository {
    void readRoleUsers(List<cRoleModel> roleModels, int primaryRole, int secondaryRoles, int statusBITS,
                       iRoleRepository.iReadRoleModelSetCallback callback);

    interface iReadRoleModelSetCallback{
        void onReadRoleUsersSucceeded(List<cUserProfileModel> userProfileModels);
        void onReadRoleUserFailed(String msg);
    }
}