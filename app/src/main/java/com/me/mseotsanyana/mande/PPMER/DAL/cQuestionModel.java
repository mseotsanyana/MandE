package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.Date;

public class cQuestionModel {
    private int ID;
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

    private cLogFrameModel logFrameModel;
    private cQuestionGroupingModel questionGroupingModel;
    private cQuestionTypeModel questionTypeModel;
    private cChoiceSetModel choiceSetModel;

    private cEQuestionModel eQuestionModel;
    private cMQuestionModel mQuestionModel;

    public cQuestionModel(int ID, int serverID, int ownerID, int orgID,
                          int groupBITS, int permsBITS, int statusBITS,
                          String name, String description, Date createdDate,
                          Date modifiedDate, Date syncedDate, cLogFrameModel logFrameModel,
                          cQuestionGroupingModel questionGroupingModel, cQuestionTypeModel questionTypeModel,
                          cChoiceSetModel choiceSetModel, cEQuestionModel eQuestionModel,
                          cMQuestionModel mQuestionModel) {
        this.ID = ID;
        this.serverID = serverID;
        this.ownerID = ownerID;
        this.orgID = orgID;
        this.groupBITS = groupBITS;
        this.permsBITS = permsBITS;
        this.statusBITS = statusBITS;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.syncedDate = syncedDate;
        this.logFrameModel = logFrameModel;
        this.questionGroupingModel = questionGroupingModel;
        this.questionTypeModel = questionTypeModel;
        this.choiceSetModel = choiceSetModel;
        this.eQuestionModel = eQuestionModel;
        this.mQuestionModel = mQuestionModel;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public cChoiceSetModel getChoiceSetModel() {
        return choiceSetModel;
    }

    public void setChoiceSetModel(cChoiceSetModel choiceSetModel) {
        this.choiceSetModel = choiceSetModel;
    }

    public cEQuestionModel geteQuestionModel() {
        return eQuestionModel;
    }

    public void seteQuestionModel(cEQuestionModel eQuestionModel) {
        this.eQuestionModel = eQuestionModel;
    }

    public cMQuestionModel getmQuestionModel() {
        return mQuestionModel;
    }

    public void setmQuestionModel(cMQuestionModel mQuestionModel) {
        this.mQuestionModel = mQuestionModel;
    }
}
