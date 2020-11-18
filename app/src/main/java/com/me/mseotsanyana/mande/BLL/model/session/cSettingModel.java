package com.me.mseotsanyana.mande.BLL.model.session;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class cSettingModel implements Serializable {
    private int settingID;
    private int serverID;
    private int ownerID;
    private int orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String name;
    private String description;
    private int settingValue;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    public cSettingModel(){}

    public cSettingModel(cSettingModel settingModel){
        this.setSettingID(settingModel.getSettingID());
        this.setServerID(settingModel.getServerID());
        this.setOwnerID(settingModel.getOwnerID());
        this.setOrgID(settingModel.getOrgID());
        this.setGroupBITS(settingModel.getGroupBITS());
        this.setPermsBITS(settingModel.getPermsBITS());
        this.setStatusBITS(settingModel.getStatusBITS());
        this.setName(settingModel.getName());
        this.setDescription(settingModel.getDescription());
        this.setSettingValue(settingModel.getSettingValue());
        this.setCreatedDate(settingModel.getCreatedDate());
        this.setModifiedDate(settingModel.getModifiedDate());
        this.setSyncedDate(settingModel.getSyncedDate());
    }

    public int getSettingID() {
        return settingID;
    }

    public void setSettingID(int settingID) {
        this.settingID = settingID;
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

    public int getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(int settingValue) {
        this.settingValue = settingValue;
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
        if (!(o instanceof cSettingModel)) return false;
        cSettingModel that = (cSettingModel) o;
        return getSettingID() == that.getSettingID() &&
                getServerID() == that.getServerID() &&
                getOwnerID() == that.getOwnerID() &&
                getOrgID() == that.getOrgID() &&
                getGroupBITS() == that.getGroupBITS() &&
                getPermsBITS() == that.getPermsBITS() &&
                getStatusBITS() == that.getStatusBITS() &&
                getSettingValue() == that.getSettingValue() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getCreatedDate(), that.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), that.getModifiedDate()) &&
                Objects.equals(getSyncedDate(), that.getSyncedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSettingID(), getServerID(), getOwnerID(), getOrgID(),
                getGroupBITS(), getPermsBITS(), getStatusBITS(), getName(),
                getDescription(), getSettingValue(), getCreatedDate(),
                getModifiedDate(), getSyncedDate());
    }
}
