package com.me.mseotsanyana.mande.BLL.interactors.session.organization;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.List;

public interface iReadOrganizationMembersInteractor extends iInteractor {
    interface Callback{
        void onReadOrganizationMembersFailed(String msg);
        void onReadOrganizationMembersRetrieved(List<cUserProfileModel> userProfileModels);
    }
}
