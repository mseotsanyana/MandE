package com.me.mseotsanyana.mande.BLL.model.session;

import com.google.firebase.firestore.Exclude;

import java.util.List;
import java.util.Map;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cPermissionModel {
    private String permissionServerID;

    // moduleServerID, entityServerID, operationServerID, list of statusBITS
    private Map<String, Map<String, Map<String, List<Integer>>>> opstatus;
    // moduleServerID, entityServerID, list of operationServerID
    private Map<String, Map<String, List<Integer>>> unixperms;

    public cPermissionModel(){}

    public cPermissionModel(Map<String, Map<String, Map<String, List<Integer>>>> opstatus,
                            Map<String, Map<String, List<Integer>>> unixperms){
        this.setOpstatus(opstatus);
        this.setUnixperms(unixperms);

    }

    @Exclude
    public String getPermissionServerID() {
        return permissionServerID;
    }

    public void setPermissionServerID(String permissionServerID) {
        this.permissionServerID = permissionServerID;
    }

    public Map<String, Map<String, Map<String, List<Integer>>>> getOpstatus() {
        return opstatus;
    }

    public void setOpstatus(Map<String, Map<String, Map<String, List<Integer>>>> opstatus) {
        this.opstatus = opstatus;
    }

    public Map<String, Map<String, List<Integer>>> getUnixperms() {
        return unixperms;
    }

    public void setUnixperms(Map<String, Map<String, List<Integer>>> unixperms) {
        this.unixperms = unixperms;
    }
}
