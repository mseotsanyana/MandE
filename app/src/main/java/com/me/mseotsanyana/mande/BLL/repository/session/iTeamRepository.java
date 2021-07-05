package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;

import java.util.List;

public interface iTeamRepository {
    void readTeams(String organizationServerID, String userServerID,
                   int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                   List<Integer> statusBITS, iReadTeamsCallback callback);

    void readTeamRoles(String roleServerID, String organizationServerID, String userServerID,
                       int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                       List<Integer> statusBITS, iReadTeamsCallback callback);

    interface iReadTeamsCallback {
        void onReadTeamsSucceeded(List<cTeamModel> teamModels);
        void onReadTeamRolesSucceeded(List<cRoleModel> roleModels);
        void onReadTeamsFailed(String msg);
        void onReadTeamRolesFailed(String msg);
    }
}
