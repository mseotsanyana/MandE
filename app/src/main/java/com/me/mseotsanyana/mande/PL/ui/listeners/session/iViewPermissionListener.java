package com.me.mseotsanyana.mande.PL.ui.listeners.session;

import com.me.mseotsanyana.treeadapterlibrary.cNode;

import java.util.List;

public interface iViewPermissionListener {
    void onClickUpdatePermission(List<cNode> nodes);
    void onClickDeletePermission(String permissionServerID);
}
