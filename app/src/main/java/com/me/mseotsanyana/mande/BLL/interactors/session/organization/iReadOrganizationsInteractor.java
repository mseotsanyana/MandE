package com.me.mseotsanyana.mande.BLL.interactors.session.organization;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;

import java.util.List;

public interface iReadOrganizationsInteractor extends iInteractor {
    interface Callback{
        void onReadOrganizationsFailed(String msg);
        void onReadOrganizationsRetrieved(List<cOrganizationModel> organizationModels);
    }
}
