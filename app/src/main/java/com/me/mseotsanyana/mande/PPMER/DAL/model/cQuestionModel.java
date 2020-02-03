package com.me.mseotsanyana.mande.PPMER.DAL.model;

import com.me.mseotsanyana.mande.PPMER.DAL.cMQuestionModel;

import java.util.Date;

public class cQuestionModel {
    private int questionID;
    private int logFrameID;
    private int questionTypeID;
    private int questionGroupID;
    private int serverID;
    private int ownerID;
    private int orgID;
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

    /*foreign keys  */
    private cLogFrameModel logFrameModel;
    private cQuestionGroupingModel questionGroupingModel;
    private cQuestionTypeModel questionTypeModel;

    /*one to one relation */
    private cQuestionnaireModel eQuestionModel;
    private cMQuestionModel mQuestionModel;

    public cQuestionModel(){}

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getLogFrameID() {
        return logFrameID;
    }

    public void setLogFrameID(int logFrameID) {
        this.logFrameID = logFrameID;
    }

    public int getQuestionTypeID() {
        return questionTypeID;
    }

    public void setQuestionTypeID(int questionTypeID) {
        this.questionTypeID = questionTypeID;
    }

    public int getQuestionGroupID() {
        return questionGroupID;
    }

    public void setQuestionGroupID(int questionGroupID) {
        this.questionGroupID = questionGroupID;
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

    public cLogFrameModel getLogFrameModel() {
        return logFrameModel;
    }

    public void setLogFrameModel(cLogFrameModel logFrameModel) {
        this.logFrameModel = logFrameModel;
    }

    public cQuestionGroupingModel getQuestionGroupingModel() {
        return questionGroupingModel;
    }

    public void setQuestionGroupingModel(cQuestionGroupingModel questionGroupingModel) {
        this.questionGroupingModel = questionGroupingModel;
    }

    public cQuestionTypeModel getQuestionTypeModel() {
        return questionTypeModel;
    }

    public void setQuestionTypeModel(cQuestionTypeModel questionTypeModel) {
        this.questionTypeModel = questionTypeModel;
    }

    public cQuestionnaireModel geteQuestionModel() {
        return eQuestionModel;
    }

    public void seteQuestionModel(cQuestionnaireModel eQuestionModel) {
        this.eQuestionModel = eQuestionModel;
    }

    public cMQuestionModel getmQuestionModel() {
        return mQuestionModel;
    }

    public void setmQuestionModel(cMQuestionModel mQuestionModel) {
        this.mQuestionModel = mQuestionModel;
    }
}
