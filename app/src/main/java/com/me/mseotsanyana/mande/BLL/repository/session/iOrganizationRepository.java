package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;

import java.util.ArrayList;
import java.util.List;

public interface iOrganizationRepository {
    void createOrganization(cOrganizationModel organizationModel,
                            iOrganizationRepositoryCallback callback);

    interface iOrganizationRepositoryCallback{
        void onCreateOrganizationSucceeded(String msg);
        void onCreateOrganizationFailed(String msg);
    }

    void readOrganizations(String userServerID, String orgServerID, int primaryTeamBIT,
                           int secondaryTeamBITS, int entitypermBITS, List<Integer> statuses,
                           int unixpermBITS, iReadOrganizationsModelSetCallback callback);

    interface iReadOrganizationsModelSetCallback{
        void onReadOrganizationsSucceeded(ArrayList<cOrganizationModel> organizationModels);
        void onReadOrganizationsFailed(String msg);
    }
}
