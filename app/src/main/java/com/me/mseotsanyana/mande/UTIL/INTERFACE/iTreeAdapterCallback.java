package com.me.mseotsanyana.mande.UTIL.INTERFACE;

import com.me.mseotsanyana.mande.BLL.domain.session.cPermissionDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cStatusDomain;

public interface iTreeAdapterCallback {
    void onCreatePermissions(cStatusDomain statusDomain);

    void onUpdatePermissions(cPermissionDomain originalDomain, cPermissionDomain modifiesDomain);

    void onRefreshPermissions();

    void onRefreshTreeAdapter();
}
