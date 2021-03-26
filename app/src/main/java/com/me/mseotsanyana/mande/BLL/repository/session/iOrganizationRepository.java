package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface iOrganizationRepository {
    void createOrganization(cOrganizationModel organizationModel,
                            iOrganizationRepository.iOrganizationRepositoryCallback callback);

    interface iOrganizationRepositoryCallback{
        void onCreateOrganizationSucceeded(String msg);
        void onCreateOrganizationFailed(String msg);
    }

    void readOrganizations(long userID, int primaryRole, int secondaryRoles, int statusBITS,
                       iOrganizationRepository.iReadOrganizationsModelSetCallback callback);

    interface iReadOrganizationsModelSetCallback{
        void onReadOrganizationsSucceeded(ArrayList<cOrganizationModel> organizationModels);
        void onReadOrganizationsFailed(String msg);
    }

    Set<cOrganizationModel> getOrganizationSet();
}
