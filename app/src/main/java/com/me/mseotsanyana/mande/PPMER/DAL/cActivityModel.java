package com.me.mseotsanyana.mande.PPMER.DAL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class cActivityModel {
    private int activityID;
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

    private cLogFrameModel logFrameModel;
    private cOutputModel outputModel;
    private Set<cActivityModel> activityModelSet;

    /* many to many */
    private Set<cPrecedingActivityModel> precedingActivityModelSet;
    private Set<cInputModel> inputModelSet;
    private Set<cRaidModel> raidModelSet;
    private Set<cQuestionModel> questionModelSet;

    private Set<cOutputModel> outputModelSet;

    /* many to many related to AWPB */
    private Set<cTaskModel> taskModelSet;
    private Set<cActivityAssignmentModel> activityAssignmentModelSet;
    private Set<cExpenditureModel> expenditureModelSet;

    public cActivityModel(){
        activityModelSet = new HashSet<>();
        precedingActivityModelSet = new HashSet<>();
        inputModelSet = new HashSet<>();
        raidModelSet = new HashSet<>();
        questionModelSet = new HashSet<>();
        outputModelSet = new HashSet<>();
        taskModelSet = new HashSet<>();
        activityAssignmentModelSet = new HashSet<>();
        expenditureModelSet = new HashSet<>();
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
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

    public cOutputModel getOutputModel() {
        return outputModel;
    }

    public void setOutputModel(cOutputModel outputModel) {
        this.outputModel = outputModel;
    }

    public Set<cActivityModel> getActivityModelSet() {
        return activityModelSet;
    }

    public void setActivityModelSet(Set<cActivityModel> activityModelSet) {
        this.activityModelSet = activityModelSet;
    }

    public Set<cPrecedingActivityModel> getPrecedingActivityModelSet() {
        return precedingActivityModelSet;
    }

    public void setPrecedingActivityModelSet(Set<cPrecedingActivityModel> precedingActivityModelSet) {
        this.precedingActivityModelSet = precedingActivityModelSet;
    }

    public Set<cInputModel> getInputModelSet() {
        return inputModelSet;
    }

    public void setInputModelSet(Set<cInputModel> inputModelSet) {
        this.inputModelSet = inputModelSet;
    }

    public Set<cRaidModel> getRaidModelSet() {
        return raidModelSet;
    }

    public void setRaidModelSet(Set<cRaidModel> raidModelSet) {
        this.raidModelSet = raidModelSet;
    }

    public Set<cQuestionModel> getQuestionModelSet() {
        return questionModelSet;
    }

    public void setQuestionModelSet(Set<cQuestionModel> questionModelSet) {
        this.questionModelSet = questionModelSet;
    }

    public Set<cOutputModel> getOutputModelSet() {
        return outputModelSet;
    }

    public void setOutputModelSet(Set<cOutputModel> outputModelSet) {
        this.outputModelSet = outputModelSet;
    }

    public Set<cTaskModel> getTaskModelSet() {
        return taskModelSet;
    }

    public void setTaskModelSet(Set<cTaskModel> taskModelSet) {
        this.taskModelSet = taskModelSet;
    }

    public Set<cActivityAssignmentModel> getActivityAssignmentModelSet() {
        return activityAssignmentModelSet;
    }

    public void setActivityAssignmentModelSet(Set<cActivityAssignmentModel> activityAssignmentModelSet) {
        this.activityAssignmentModelSet = activityAssignmentModelSet;
    }

    public Set<cExpenditureModel> getExpenditureModelSet() {
        return expenditureModelSet;
    }

    public void setExpenditureModelSet(Set<cExpenditureModel> expenditureModelSet) {
        this.expenditureModelSet = expenditureModelSet;
    }
}

