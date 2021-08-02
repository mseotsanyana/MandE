package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.List;

public interface iOrganizationRepository {
    void createOrganization(cOrganizationModel organizationModel,
                            iCreateOrganizationCallback callback);

    interface iCreateOrganizationCallback {
        void onCreateOrganizationSucceeded(String msg);

        void onCreateOrganizationFailed(String msg);
    }

    void readOrganizations(String organizationServerID, String userServerID,
                           int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                           List<Integer> statusBITS, iReadOrganizationsCallback callback);

    interface iReadOrganizationsCallback {
        void onReadOrganizationsSucceeded(List<cOrganizationModel> organizationModels);
        void onReadOrganizationsFailed(String msg);
    }

    void readOrganizationMembers(String organizationServerID, String userServerID,
                                 int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                 List<Integer> statusBITS,
                                 iReadOrganizationMembersCallback callback);

    interface iReadOrganizationMembersCallback {
        void onReadOrganizationMembersSucceeded(List<cUserProfileModel> userProfileModels);
        void onReadOrganizationMembersFailed(String msg);
    }
}
