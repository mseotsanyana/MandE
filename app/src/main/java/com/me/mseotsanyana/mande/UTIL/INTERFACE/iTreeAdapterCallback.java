package com.me.mseotsanyana.mande.UTIL.INTERFACE;


import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;

public interface iTreeAdapterCallback {
    void onCreatePermissions(cStatusModel statusDomain);

    void onUpdatePermissions(cPermissionModel originalDomain, cPermissionModel modifiesDomain);

    void onRefreshPermissions();

    void onRefreshTreeAdapter();
}
