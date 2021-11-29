package com.me.mseotsanyana.mande.BLL.interactors.session.permission;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iUpdateRolePermissionInteractor extends iInteractor {
    interface Callback {
        void onUpdateRolePermissionFailed(String msg);
        void onUpdateRolePermissionSucceeded(String msg);
    }
}
