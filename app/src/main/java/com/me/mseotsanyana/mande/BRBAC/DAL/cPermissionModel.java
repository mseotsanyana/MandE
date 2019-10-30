package com.me.mseotsanyana.mande.BRBAC.DAL;

import java.util.Date;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cPermissionModel {
    private int privilegeID;
    private int entityID;
    private int entityTypeID;
    private int operationID;
    private int serverID;
    private int ownerID;
    private int orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String name;
    private String description;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    cEntityModel entityModel;
    cOperationModel operationModel;
    Set<cStatusModel> statusModelSet;

    public cPermissionModel(){}

    public cPermissionModel(cPermissionModel permissionModel){
        this.setPrivilegeID(permissionModel.getPrivilegeID());
        this.setEntityID(permissionModel.getEntityID());
        this.setEntityTypeID(permissionModel.getEntityTypeID());
        this.setOperationID(permissionModel.getOperationID());
        this.setServerID(permissionModel.getServerID());
        this.setOwnerID(permissionModel.getOwnerID());
        this.setOrgID(permissionModel.getOrgID());
        this.setGroupBITS(permissionModel.getGroupBITS());
        this.setPermsBITS(permissionModel.getPermsBITS());
        this.setStatusBITS(permissionModel.getStatusBITS());
        this.setCreatedDate(permissionModel.getCreatedDate());
        this.setModifiedDate(permissionModel.getModifiedDate());
        this.setSyncedDate(permissionModel.getSyncedDate());
    }

    public int getPrivilegeID() {
        return privilegeID;
    }

    public void setPrivilegeID(int privilegeID) {
        this.privilegeID = privilegeID;
    }

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public int getEntityTypeID() {
        return entityTypeID;
    }

    public void setEntityTypeID(int entityTypeID) {
        this.entityTypeID = entityTypeID;
    }

    public int getOperationID() {
        return operationID;
    }

    public void setOperationID(int operationID) {
        this.operationID = operationID;
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }


    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public int getGroupBITS() {
        return groupBITS;
    }

    public void setGroupBITS(int groupBITS) {
        this.groupBITS = groupBITS;
    }

    public int getPermsBITS() {
        return permsBITS;
    }

    public void setPermsBITS(int permsBITS) {
        this.permsBITS = permsBITS;
    }

    public int getStatusBITS() {
        return statusBITS;
    }

    public void setStatusBITS(int statusBITS) {
        this.statusBITS = statusBITS;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getSyncedDate() {
        return syncedDate;
    }

    public void setSyncedDate(Date syncedDate) {
        this.syncedDate = syncedDate;
    }

    public cEntityModel getEntityModel() {
        return entityModel;
    }

    public void setEntityModel(cEntityModel entityModel) {
        this.entityModel = entityModel;
    }

    public cOperationModel getOperationModel() {
        return operationModel;
    }

    public void setOperationModel(cOperationModel operationModel) {
        this.operationModel = operationModel;
    }

    public Set<cStatusModel> getStatusModelSet() {
        return statusModelSet;
    }

    public void setStatusModelSet(Set<cStatusModel> statusModelSet) {
        this.statusModelSet = statusModelSet;
    }
}
