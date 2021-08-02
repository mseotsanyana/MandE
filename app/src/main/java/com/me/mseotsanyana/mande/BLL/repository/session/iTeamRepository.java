package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.List;
import java.util.Map;

public interface iTeamRepository {
    void readTeams(String organizationServerID, String userServerID,
                   int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                   List<Integer> statusBITS, iReadTeamsCallback callback);

    interface iReadTeamsCallback {
        void onReadTeamsSucceeded(List<cTeamModel> teamModels);

        void onReadTeamsFailed(String msg);
    }

    void readTeamsWithMembers(String organizationServerID, String userServerID,
                              int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                              List<Integer> statusBITS, iReadTeamsWithMembersCallback callback);

    interface iReadTeamsWithMembersCallback {
        void onReadTeamsWithMembersSucceeded(Map<cTeamModel, List<cUserProfileModel>>
                                                     teamMembersMap);

        void onReadTeamsWithMembersFailed(String msg);
    }

    void readTeamsWithRoles(String organizationServerID, String userServerID,
                            int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                            List<Integer> statusBITS, iReadTeamsWithRolesCallback callback);

    interface iReadTeamsWithRolesCallback {
        void onReadTeamsWithRolesSucceeded(Map<cTeamModel, List<cRoleModel>> teamRolesMap);

        void onReadTeamsWithRolesFailed(String msg);
    }


    void readTeamRoles(String roleServerID, String organizationServerID,
                       String userServerID, int primaryTeamBIT,
                       List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
                       iReadTeamRolesCallback callback);

    interface iReadTeamRolesCallback {
        void onReadTeamsSucceeded(List<cTeamModel> teamModels);

        void onReadTeamMembersSucceeded(String teamServerID, List<cUserProfileModel> roleModels);

        void onReadTeamRolesSucceeded(List<cRoleModel> roleModels);

        void onReadTeamsFailed(String msg);

        void onReadTeamRolesFailed(String msg);
    }
}
