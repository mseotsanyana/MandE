package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.List;
import java.util.Map;

public interface iTeamRepository {
    void readTeamsWithMembers(long userServerID, int primaryTeamBIT, int secondaryTeamBITS,
                              int operationsBITS, int statusBITS, String organizationServerID,
                              iReadTeamModelCallback callback);

    interface iReadTeamModelCallback{
        void onReadTeamsWithMembersSucceeded(Map<cTeamModel, List<cUserProfileModel>> teamModelMap);
        void onReadTeamFailed(String msg);
    }
}
