package com.me.mseotsanyana.mande.BLL.model.session;

import com.google.firebase.firestore.Exclude;

import java.util.List;
import java.util.Map;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cPermissionModel {
    private String roleServerID;

    private String name;
    private String description;

    // module identification and its entity permissions
    private Map<String, List<cEntityModel>> entitymodules;

    // parent identification and its child menu identification
    private Map<String, List<Integer>> menuitems;

    public cPermissionModel(){}

    public cPermissionModel(cPermissionModel permissionModel){
        this.setRoleServerID(permissionModel.getRoleServerID());
        this.setName(permissionModel.getName());
        this.setDescription(permissionModel.getDescription());
        this.setEntitymodules(permissionModel.getEntitymodules());
        this.setMenuitems(permissionModel.getMenuitems());
    }

    public cPermissionModel(Map<String, List<cEntityModel>> entitymodules,
                            Map<String, List<Integer>>  menuitems){
        this.setEntitymodules(entitymodules);
        this.setMenuitems(menuitems);
    }

    public cPermissionModel(Map<String, List<cEntityModel>> entitymodules){
        this.setEntitymodules(entitymodules);
    }

    public cPermissionModel(String description, Map<String, List<cEntityModel>> entitymodules,
                            Map<String, List<Integer>>  menuitems){
        this.setDescription(description);
        this.setEntitymodules(entitymodules);
        this.setMenuitems(menuitems);
    }

    public String getRoleServerID() {
        return roleServerID;
    }

    public void setRoleServerID(String roleServerID) {
        this.roleServerID = roleServerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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