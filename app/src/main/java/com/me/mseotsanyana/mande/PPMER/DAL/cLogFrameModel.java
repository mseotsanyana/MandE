package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class cLogFrameModel {
    public int logFrameID;
    private int serverID;
    private int orgID;
    private int ownerID;
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

    private Set<cLogFrameModel> logFrameTrees;

    private Set<cImpactModel> impactModels;
    private Set<cOutcomeModel> outcomeModels;
    private Set<cOutputModel> outputModels;
    private Set<cActivityModel> activityModels;
    private Set<cInputModel> inputModels;
    private Set<cQuestionModel> questionModels;
    private Set<cIndicatorModel> indicatorModels;
    private Set<cRaidModel> raidModels;


    public cLogFrameModel(){}

    public cLogFrameModel(int logFrameID, int serverID, int ownerID, int orgID,
                          int groupBITS, int permsBITS, int statusBITS,
                          String name, String description, Date startDate,
                          Date endDate, Date createdDate, Date modifiedDate, Date syncedDate,
                          Set<cImpactModel> impactModels, Set<cOutcomeModel> outcomeModels,
                          Set<cOutputModel> outputModels, Set<cActivityModel> activityModels,
                          Set<cInputModel> inputModels, Set<cQuestionModel> questionModels,
                          Set<cIndicatorModel> indicatorModels, Set<cRaidModel> raidModels) {
        this.logFrameID = logFrameID;
        this.serverID = serverID;
        this.ownerID = ownerID;
        this.orgID = orgID;
        this.groupBITS = groupBITS;
        this.permsBITS = permsBITS;
        this.statusBITS = statusBITS;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.syncedDate = syncedDate;
        this.impactModels = impactModels;
        this.outcomeModels = outcomeModels;
        this.outputModels = outputModels;
        this.activityModels = activityModels;
        this.inputModels = inputModels;
        this.questionModels = questionModels;
        this.indicatorModels = indicatorModels;
        this.raidModels = raidModels;
    }

    public int getLogFrameID() {
        return logFrameID;
    }

    public void setLogFrameID(int logFrameID) {
        this.logFrameID = logFrameID;
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


    public Set<cLogFrameModel> getLogFrameTrees() {
        return logFrameTrees;
    }

    public void setLogFrameTrees(Set<cLogFrameModel> logFrameTrees) {
        this.logFrameTrees = logFrameTrees;
    }

    public Set<cImpactModel> getImpactModels() {
        return impactModels;
    }

    public void setImpactModels(Set<cImpactModel> impactModels) {
        this.impactModels = impactModels;
    }

    public Set<cOutcomeModel> getOutcomeModels() {
        return outcomeModels;
    }

    public void setOutcomeModels(Set<cOutcomeModel> outcomeModels) {
        this.outcomeModels = outcomeModels;
    }

    public Set<cOutputModel> getOutputModels() {
        return outputModels;
    }

    public void setOutputModels(Set<cOutputModel> outputModels) {
        this.outputModels = outputModels;
    }

    public Set<cActivityModel> getActivityModels() {
        return activityModels;
    }

    public void setActivityModels(Set<cActivityModel> activityModels) {
        this.activityModels = activityModels;
    }

    public Set<cInputModel> getInputModels() {
        return inputModels;
    }

    public void setInputModels(Set<cInputModel> inputModels) {
        this.inputModels = inputModels;
    }

    public Set<cQuestionModel> getQuestionModels() {
        return questionModels;
    }

    public void setQuestionModels(Set<cQuestionModel> questionModels) {
        this.questionModels = questionModels;
    }

    public Set<cIndicatorModel> getIndicatorModels() {
        return indicatorModels;
    }

    public void setIndicatorModels(Set<cIndicatorModel> indicatorModels) {
        this.indicatorModels = indicatorModels;
    }

    public Set<cRaidModel> getRaidModels() {
        return raidModels;
    }

    public void setRaidModels(Set<cRaidModel> raidModels) {
        this.raidModels = raidModels;
    }



}
