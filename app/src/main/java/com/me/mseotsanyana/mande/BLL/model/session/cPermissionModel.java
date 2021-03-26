package com.me.mseotsanyana.mande.BLL.model.session;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cPermissionModel {
    private int permissionID;
    private int entityID;
    private int entityTypeID;
    private int operationID;
    private int statusSetID;

    private String serverID;
    private String entityServerID;
    private String operationServerID;
    private String statusServerID;

    private int ownerID;
    private int orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    cEntityModel entityModel;
    cOperationModel operationModel;
    cStatusSetModel statusSetModel;

    public cPermissionModel(){}

    public cPermissionModel(cPermissionModel permissionModel){
        this.setPermissionID(permissionModel.getPermissionID());
        this.setEntityID(permissionModel.getEntityID());
        this.setEntityTypeID(permissionModel.getEntityTypeID());
        this.setOperationID(permissionModel.getOperationID());
        this.setStatusSetID(permissionModel.getStatusSetID());
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

    public int getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
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

    public int getStatusSetID() {
        return statusSetID;
    }

    public void setStatusSetID(int statusSetID) {
        this.statusSetID = statusSetID;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getEntityServerID() {
        return entityServerID;
    }

    public void setEntityServerID(String entityServerID) {
        this.entityServerID = entityServerID;
    }

    public String getOperationServerID() {
        return operationServerID;
    }

    public void setOperationServerID(String operationServerID) {
        this.operationServerID = operationServerID;
    }

    public String getStatusServerID() {
        return statusServerID;
    }

    public void setStatusServerID(String statusServerID) {
        this.statusServerID = statusServerID;
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

    public cStatusSetModel getStatusSetModel() {
        return statusSetModel;
    }

    public void setStatusSetModel(cStatusSetModel statusSetModel) {
        this.statusSetModel = statusSetModel;
    }
}
