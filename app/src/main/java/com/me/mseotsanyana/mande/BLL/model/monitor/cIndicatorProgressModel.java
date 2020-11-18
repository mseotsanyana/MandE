package com.me.mseotsanyana.mande.BLL.model.monitor;

import java.util.Date;

public class cIndicatorProgressModel {
    private long progressID;
    private long serverID;
    private long ownerID;
    private long orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    private cIndicatorMilestoneModel indicatorMilestoneModel;
    private cDataCollectorModel dataCollectorModel;

    public long getProgressID() {
        return progressID;
    }

    public void setProgressID(long progressID) {
        this.progressID = progressID;
    }

    public long getServerID() {
        return serverID;
    }

    public void setServerID(long serverID) {
        this.serverID = serverID;
    }

    public long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(long ownerID) {
        this.ownerID = ownerID;
    }

    public long getOrgID() {
        return orgID;
    }

    public void setOrgID(long orgID) {
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

    public cIndicatorMilestoneModel getIndicatorMilestoneModel() {
        return indicatorMilestoneModel;
    }

    public void setIndicatorMilestoneModel(cIndicatorMilestoneModel indicatorMilestoneModel) {
        this.indicatorMilestoneModel = indicatorMilestoneModel;
    }

    public cDataCollectorModel getDataCollectorModel() {
        return dataCollectorModel;
    }

    public void setDataCollectorModel(cDataCollectorModel dataCollectorModel) {
        this.dataCollectorModel = dataCollectorModel;
    }
}
