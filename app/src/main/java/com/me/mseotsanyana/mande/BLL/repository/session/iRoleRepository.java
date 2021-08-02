package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;

import java.util.List;

public interface iRoleRepository {
    void readTeamRoles(String organizationServerID, String userServerID,
                       int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                       List<Integer> statusBITS, iReadTeamRolesCallback callback);

    interface iReadTeamRolesCallback {
        void onReadTeamRolesSucceeded(List<cRoleModel> roleModels);

        void onReadTeamRolesFailed(String msg);
    }

    void readRoleTeams(String roleServerID, String organizationServerID, String userServerID,
                       int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                       List<Integer> statusBITS, iReadRoleTeamsCallback callback);

    interface iReadRoleTeamsCallback {
        void onReadRoleTeamsSucceeded(List<cTeamModel> teamModels);

        void onReadRoleTeamsFailed(String msg);
    }
}