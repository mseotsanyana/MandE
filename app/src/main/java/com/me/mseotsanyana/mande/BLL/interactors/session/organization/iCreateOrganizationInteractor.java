package com.me.mseotsanyana.mande.BLL.interactors.session.organization;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;

public interface iCreateOrganizationInteractor extends iInteractor {
    interface Callback{
        void onOrganizationCreated(String msg);
        void onOrganizationCreateFailed(String msg);
    }
}
