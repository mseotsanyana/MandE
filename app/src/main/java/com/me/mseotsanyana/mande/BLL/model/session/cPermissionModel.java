package com.me.mseotsanyana.mande.BLL.model.session;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cPermissionModel {
    private String roleServerID;
    private String description;

    // module identification and its entities
    private Map<String, List<cEntityModel>> entitymodules;
    // module identification and its menu items
    private Map<String, List<Integer>> menuitems;

    public cPermissionModel(){}

    public cPermissionModel(Map<String, List<cEntityModel>> entitymodules,
                            Map<String, List<Integer>>  menuitems){
        this.setEntitymodules(entitymodules);
        this.setMenuitems(menuitems);
    }

    public cPermissionModel(String description, Map<String, List<cEntityModel>> entitymodules,
                            Map<String, List<Integer>>  menuitems){
        this.setDescription(description);
        this.setEntitymodules(entitymodules);
        this.setMenuitems(menuitems);
    }

    @Exclude
    public String getRoleServerID() {
        return roleServerID;
    }

    public void setRoleServerID(String roleServerID) {
        this.roleServerID = roleServerID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, List<cEntityModel>> getEntitymodules() {
        return entitymodules;
    }

    public void setEntitymodules(Map<String, List<cEntityModel>> entitymodules) {
        this.entitymodules = entitymodules;
    }

    public Map<String, List<Integer>> getMenuitems() {
        return menuitems;
    }

    public void setMenuitems(Map<String, List<Integer>> menuitems) {
        this.menuitems = menuitems;
    }
}

//    // moduleServerID, entityServerID, operationServerID, list of statusBITS
//    private Map<String, Map<String, Map<String, List<Integer>>>> opstatus;
//    // moduleServerID, entityServerID, list of operationServerID
//    private Map<String, Map<String, List<Integer>>> unixperms;



//    public cPermissionModel(Map<String, Map<String, Map<String, List<Integer>>>> opstatus,
//                            Map<String, Map<String, List<Integer>>> unixperms){
//        this.setOpstatus(opstatus);
//        this.setUnixperms(unixperms);
//
//    }

//    public Map<String, Map<String, Map<String, List<Integer>>>> getOpstatus() {
//        return opstatus;
//    }
//
//    public void setOpstatus(Map<String, Map<String, Map<String, List<Integer>>>> opstatus) {
//        this.opstatus = opstatus;
//    }
//
//    public Map<String, Map<String, List<Integer>>> getUnixperms() {
//        return unixperms;
//    }
//
//    public void setUnixperms(Map<String, Map<String, List<Integer>>> unixperms) {
//        this.unixperms = unixperms;
//    }