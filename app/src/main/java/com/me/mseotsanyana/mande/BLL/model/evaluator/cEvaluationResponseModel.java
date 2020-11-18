package com.me.mseotsanyana.mande.BLL.model.evaluator;

import java.util.Date;

public class cEvaluationResponseModel {
    private long evaluationResponseID;
    private long userEvaluationID;
    private long questionID;

    private long serverID;
    private long ownerID;
    private long orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    private cUserEvaluationModel userEvaluationModel;

    public cEvaluationResponseModel() {
        userEvaluationModel = new cUserEvaluationModel();
    }

    public long getEvaluationResponseID() {
        return evaluationResponseID;
    }

    public void setEvaluationResponseID(long evaluationResponseID) {
        this.evaluationResponseID = evaluationResponseID;
    }

    public long getUserEvaluationID() {
        return userEvaluationID;
    }

    public void setUserEvaluationID(long userEvaluationID) {
        this.userEvaluationID = userEvaluationID;
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
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

    public cUserEvaluationModel getUserEvaluationModel() {
        return userEvaluationModel;
    }

    public void setUserEvaluationModel(cUserEvaluationModel userEvaluationModel) {
        this.userEvaluationModel = userEvaluationModel;
    }
}
