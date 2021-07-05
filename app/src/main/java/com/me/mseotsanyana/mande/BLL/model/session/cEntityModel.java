package com.me.mseotsanyana.mande.BLL.model.session;

import java.util.List;
import java.util.Map;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cEntityModel {
    private String entityServerID;
    private List<Integer> unixperms;
    private Map<String, List<Integer>> entityperms;

    public cEntityModel(){}

    public cEntityModel(String entityServerID, Map<String, List<Integer>> entityperms,
                        List<Integer> unixperms) {
        this.entityServerID = entityServerID;
        this.entityperms = entityperms;
        this.unixperms = unixperms;
    }

    public String getEntityServerID() {
        return entityServerID;
    }

    public void setEntityServerID(String entityServerID) {
        this.entityServerID = entityServerID;
    }

    public List<Integer> getUnixperms() {
        return unixperms;
    }

    public void setUnixperms(List<Integer> unixperms) {
        this.unixperms = unixperms;
    }

    public Map<String, List<Integer>> getEntityperms() {
        return entityperms;
    }

    public void setEntityperms(Map<String, List<Integer>> entityperms) {
        this.entityperms = entityperms;
    }
}

//    private int serverID;
//    private int ownerID;
//    private int orgID;
//    private int groupBITS;
//    private int permsBITS;
//    private int statusBITS;
//    private String name;
//    private String description;
//    private Date createdDate;
//    private Date modifiedDate;
//    private Date syncedDate;
//    public cEntityModel(cEntityModel entityModel){
//        this.setEntityID(entityModel.getEntityID());
//        this.setModuleID(entityModel.getEntityID());
//        this.setServerID(entityModel.getServerID());
//        this.setOwnerID(entityModel.getOwnerID());
//        this.setOrgID(entityModel.getOrgID());
//        this.setGroupBITS(entityModel.getGroupBITS());
//        this.setPermsBITS(entityModel.getPermsBITS());
//        this.setStatusBITS(entityModel.getStatusBITS());
//        this.setName(entityModel.getName());
//        this.setDescription(entityModel.getDescription());
//        this.setCreatedDate(entityModel.getCreatedDate());
//        this.setModifiedDate(entityModel.getModifiedDate());
//        this.setSyncedDate(entityModel.getSyncedDate());
//    }