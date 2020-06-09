package com.me.mseotsanyana.mande.BLL.interactors.session.organization;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.DAL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;

import java.util.ArrayList;

public interface iReadSharedOrgsInteractor extends iInteractor {
    interface Callback{
        void onReadSharedOrgsFailed(String msg);
        void onSharedOrgsRetrieved(ArrayList<cOrganizationModel> organizationModels);
    }
}
