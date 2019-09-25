package com.me.mseotsanyana.mande.Interface;

import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.io.Serializable;
import java.util.List;

public interface iPermissionInterface extends Serializable {
    /** privileges **/
    void onUpdatePrivilege(cTreeModel treeModel);
    void onDeletePrivilege(cTreeModel treeModel, int position);
    void onSyncPrivilege(cTreeModel treeModel, int position);
    /** permissions **/
    void onAddPermissionEntities(cNode node);
    void onRemovePermissionEntities(cNode node);
    /** response message for privileges and/or permissions **/
    void onResponseMessage(int titleResourceID, int messageResourceID);
}
