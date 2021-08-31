package com.me.mseotsanyana.mande.BLL.interactors.session.permission;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cNode;

import java.util.List;

public interface iUpdatePermissionInteractor extends iInteractor {
    interface Callback {
        void onUpdatePermissionFailed(String msg);
        void onUpdatePermissionSucceeded(String msg);
    }
}
