package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;

import java.util.List;

public interface iRoleRepository {
    void readRoles(String userServerID, String organizationServerID,
                       int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                       List<Integer> statusBITS, iReadRolesCallback callback);

    void readRoleTeams(String roleServerID, String organizationServerID, String userServerID,
                       int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                       List<Integer> statusBITS, iReadRolesCallback callback);

    interface iReadRolesCallback{
        void onReadRolesSucceeded(List<cRoleModel> roleModels);
        void onReadRoleTeamsSucceeded(List<cTeamModel> teamModels);
        void onReadRolesFailed(String msg);
        void onReadRoleTeamsFailed(String msg);
    }
}