package com.me.mseotsanyana.mande.BLL.interactors.session.role;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;

import java.util.List;

public interface iReadRolesInteractor extends iInteractor {
    interface Callback {
        void onReadRolesFailed(String msg);
        void onReadRolesSucceeded(List<cRoleModel> roleModels);
    }
}
