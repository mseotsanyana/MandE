package com.me.mseotsanyana.mande.BLL.model.session;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cPrivilegeModel {
    private String privilegeServerID;

    private String ownerID;
    private String orgOwnerID;
    private int teamOwnerBIT;
    private List<Integer> unixpermsBITS;
    private int statusesBITS;

    private String name;
    private String description;
    private Date createdDate;
    private Date modifiedDate;

    private Date syncedDate;

    public cPrivilegeModel(){}

    public cPrivilegeModel(cPrivilegeModel operationModel){
        this.setPrivilegeServerID(operationModel.getPrivilegeServerID());
        this.setOwnerID(operationModel.getOwnerID());
        this.setOrgOwnerID(operationModel.getOrgOwnerID());
        this.setTeamOwnerBIT(operationModel.getTeamOwnerBIT());
        this.setUnixpermsBITS(operationModel.getUnixpermsBITS());
        this.setStatusesBITS(operationModel.getStatusesBITS());
        this.setName(operationModel.getName());
        this.setDescription(operationModel.getDescription());
        this.setCreatedDate(operationModel.getCreatedDate());
        this.setModifiedDate(operationModel.getModifiedDate());
        this.setSyncedDate(operationModel.getSyncedDate());
    }

    @Exclude
    public String getPrivilegeServerID() {
        return privilegeServerID;
    }

    public void setPrivilegeServerID(String privilegeServerID) {
        this.privilegeServerID = privilegeServerID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getOrgOwnerID() {
        return orgOwnerID;
    }

    public void setOrgOwnerID(String orgOwnerID) {
        this.orgOwnerID = orgOwnerID;
    }

    public int getTeamOwnerBIT() {
        return teamOwnerBIT;
    }

    public void setTeamOwnerBIT(int teamOwnerBIT) {
        this.teamOwnerBIT = teamOwnerBIT;
    }

    public List<Integer> getUnixpermsBITS() {
        return unixpermsBITS;
    }

    public void setUnixpermsBITS(List<Integer> unixpermsBITS) {
        this.unixpermsBITS = unixpermsBITS;
    }

    public int getStatusesBITS() {
        return statusesBITS;
    }

    public void setStatusesBITS(int statusesBITS) {
        this.statusesBITS = statusesBITS;
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

    @Exclude
    public Date getSyncedDate() {
        return syncedDate;
    }

    public void setSyncedDate(Date syncedDate) {
        this.syncedDate = syncedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cPrivilegeModel)) return false;
        cPrivilegeModel that = (cPrivilegeModel) o;
        return  getPrivilegeServerID() == that.getPrivilegeServerID() &&
                getOwnerID() == that.getOwnerID() &&
                getOrgOwnerID() == that.getOrgOwnerID() &&
                getTeamOwnerBIT() == that.getTeamOwnerBIT() &&
                getUnixpermsBITS() == that.getUnixpermsBITS() &&
                getStatusesBITS() == that.getStatusesBITS() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getCreatedDate(), that.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), that.getModifiedDate()) &&
                Objects.equals(getSyncedDate(), that.getSyncedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrivilegeServerID(), getOwnerID(),
                getOrgOwnerID(), getTeamOwnerBIT(), getUnixpermsBITS(), getStatusesBITS(),
                getName(), getDescription(), getCreatedDate(), getModifiedDate(),
                getSyncedDate());
    }
}
