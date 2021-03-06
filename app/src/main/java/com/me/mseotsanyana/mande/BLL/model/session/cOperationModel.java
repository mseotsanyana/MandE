package com.me.mseotsanyana.mande.BLL.model.session;

import java.util.Date;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cOperationModel {
    private int operationServerID;

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

    public cOperationModel(){}

    public cOperationModel(cOperationModel operationModel){
        this.setOperationServerID(operationModel.getOperationServerID());
        //this.setServerID(operationModel.getServerID());
        this.setOwnerID(operationModel.getOwnerID());
        this.setOrgID(operationModel.getOrgID());
        this.setGroupBITS(operationModel.getGroupBITS());
        this.setPermsBITS(operationModel.getPermsBITS());
        this.setStatusBITS(operationModel.getStatusBITS());
        this.setName(operationModel.getName());
        this.setDescription(operationModel.getDescription());
        this.setCreatedDate(operationModel.getCreatedDate());
        this.setModifiedDate(operationModel.getModifiedDate());
        this.setSyncedDate(operationModel.getSyncedDate());
    }

    public int getOperationServerID() {
        return operationServerID;
    }

    public void setOperationServerID(int operationServerID) {
        this.operationServerID = operationServerID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cOperationModel)) return false;
        cOperationModel that = (cOperationModel) o;
        return getOperationServerID() == that.getOperationServerID() &&
                getOwnerID() == that.getOwnerID() &&
                getOrgID() == that.getOrgID() &&
                getGroupBITS() == that.getGroupBITS() &&
                getPermsBITS() == that.getPermsBITS() &&
                getStatusBITS() == that.getStatusBITS() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getCreatedDate(), that.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), that.getModifiedDate()) &&
                Objects.equals(getSyncedDate(), that.getSyncedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperationServerID(), getOwnerID(),
                getOrgID(), getGroupBITS(), getPermsBITS(), getStatusBITS(),
                getName(), getDescription(), getCreatedDate(), getModifiedDate(),
                getSyncedDate());
    }
}
