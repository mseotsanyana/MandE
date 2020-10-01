package com.me.mseotsanyana.mande.DAL.model.monitor;

import java.util.Date;

public class cIndicatorModel {
    private long indicatorID;
    private long logFrameID;
    private long targetID;
    private long indicatorTypeID;
    private long frequencyID;
    private long methodID;
    private long chartID;
    private long dataCollectorID;
    private int serverID;
    private int ownerID;
    private int orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String name;
    private String description;
    private String question;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    public long getIndicatorID() {
        return indicatorID;
    }

    public void setIndicatorID(long indicatorID) {
        this.indicatorID = indicatorID;
    }

    public long getLogFrameID() {
        return logFrameID;
    }

    public void setLogFrameID(long logFrameID) {
        this.logFrameID = logFrameID;
    }

    public long getTargetID() {
        return targetID;
    }

    public void setTargetID(long targetID) {
        this.targetID = targetID;
    }

    public long getIndicatorTypeID() {
        return indicatorTypeID;
    }

    public void setIndicatorTypeID(long indicatorTypeID) {
        this.indicatorTypeID = indicatorTypeID;
    }

    public long getFrequencyID() {
        return frequencyID;
    }

    public void setFrequencyID(long frequencyID) {
        this.frequencyID = frequencyID;
    }

    public long getMethodID() {
        return methodID;
    }

    public void setMethodID(long methodID) {
        this.methodID = methodID;
    }

    public long getChartID() {
        return chartID;
    }

    public void setChartID(long chartID) {
        this.chartID = chartID;
    }

    public long getDataCollectorID() {
        return dataCollectorID;
    }

    public void setDataCollectorID(long dataCollectorID) {
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
