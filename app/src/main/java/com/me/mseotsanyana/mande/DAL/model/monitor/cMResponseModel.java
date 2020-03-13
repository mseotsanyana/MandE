package com.me.mseotsanyana.mande.DAL.model.monitor;

import java.util.Date;

public class cMResponseModel {
    private int mresponseID;
    private int indicatorMilestoneID;
    private int dataCollectorID;
    private int serverID;
    private int ownerID;
    private int orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    public int getMresponseID() {
        return mresponseID;
    }

    public void setMresponseID(int mresponseID) {
        this.mresponseID = mresponseID;
    }

    public int getIndicatorMilestoneID() {
        return indicatorMilestoneID;
    }

    public void setIndicatorMilestoneID(int indicatorMilestoneID) {
        this.indicatorMilestoneID = indicatorMilestoneID;
    }

    public int getDataCollectorID() {
        return dataCollectorID;
    }

    public void setDataCollectorID(int dataCollectorID) {
        this.dataCollectorID = dataCollectorID;
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
}
