package com.me.mseotsanyana.mande.UTILITY.INTERFACE;

import com.me.mseotsanyana.mande.BRBAC.BLL.cPermissionDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cStatusDomain;

public interface iTreeAdapterCallback {
    void onCreatePermissions(cStatusDomain statusDomain);

    void onUpdatePermissions(cPermissionDomain originalDomain, cPermissionDomain modifiesDomain);

    void onRefreshPermissions();

    void onRefreshTreeAdapter();
}
