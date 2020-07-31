package com.me.mseotsanyana.mande.DAL.model.evaluator;

import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;

import java.util.Date;

public class cUserEvaluationModel {
    private long useEvaluationID;
    private long serverID;
    private long ownerID;
    private long orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    /* evaluation */
    private cEvaluationModel evaluationModel;
    /* user */
    private cUserModel userModel;

    public cUserEvaluationModel(){
        evaluationModel = new cEvaluationModel();
        userModel = new cUserModel();
    }

    public long getUseEvaluationID() {
        return useEvaluationID;
    }

    public void setUseEvaluationID(long useEvaluationID) {
        this.useEvaluationID = useEvaluationID;
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

    public cEvaluationModel getEvaluationModel() {
        return evaluationModel;
    }

    public void setEvaluationModel(cEvaluationModel evaluationModel) {
        this.evaluationModel = evaluationModel;
    }

    public cUserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(cUserModel userModel) {
        this.userModel = userModel;
    }

}
