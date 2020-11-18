package com.me.mseotsanyana.mande.BLL.interactors.session.organization;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;

import java.util.ArrayList;

public interface iReadSharedOrgsInteractor extends iInteractor {
    interface Callback{
        void onReadSharedOrgsFailed(String msg);
        void onSharedOrgsRetrieved(ArrayList<cOrganizationModel> organizationModels);
    }
}
