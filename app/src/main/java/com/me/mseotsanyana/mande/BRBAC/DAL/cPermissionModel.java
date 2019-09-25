package com.me.mseotsanyana.mande.BRBAC.DAL;


import java.util.Date;

/**
 * Created by mseotsanyana on 2017/06/27.
 */

public class cPermissionModel {
    private int organizationID;
    private cPrivilegeModel privilegeModel;
    private cEntityModel entityModel;
    private cOperationModel operationModel;
    private cStatusModel statusModel;

    private int ownerID;
    private int orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public cPrivilegeModel getPrivilegeModel() {
        return privilegeModel;
    }

    public void setPrivilegeModel(cPrivilegeModel privilegeModel) {
        this.privilegeModel = privilegeModel;
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

    public cStatusModel getStatusModel() {
        return statusModel;
    }

    public void setStatusModel(cStatusModel statusModel) {
        this.statusModel = statusModel;
    }
}
