package com.me.mseotsanyana.mande.DAL.model.session;

import java.util.Date;
import java.util.Set;

public class cStatusSetModel {
    private int statusSetID;
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

    private Set<cStatusModel> statusModelSet;

    public cStatusSetModel(cStatusSetModel statusSetModel){
        this.setStatusSetID(statusSetModel.getStatusSetID());
        this.setServerID(statusSetModel.getServerID());
        this.setOwnerID(statusSetModel.getOwnerID());
        this.setOrgID(statusSetModel.getOrgID());
        this.setGroupBITS(statusSetModel.getGroupBITS());
        this.setPermsBITS(statusSetModel.getPermsBITS());
        this.setStatusBITS(statusSetModel.getStatusBITS());
        this.setName(statusSetModel.getName());
        this.setDescription(statusSetModel.getDescription());
        this.setCreatedDate(statusSetModel.getCreatedDate());
        this.setModifiedDate(statusSetModel.getModifiedDate());
        this.setSyncedDate(statusSetModel.getSyncedDate());

        this.setStatusModelSet(statusSetModel.getStatusModelSet());
    }

    public cStatusSetModel() {

    }

    public int getStatusSetID() {
        return statusSetID;
    }

    public void setStatusSetID(int statusSetID) {
        this.statusSetID = statusSetID;
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

    public Set<cStatusModel> getStatusModelSet() {
        return statusModelSet;
    }

    public void setStatusModelSet(Set<cStatusModel> statusModelSet) {
        this.statusModelSet = statusModelSet;
    }
}
