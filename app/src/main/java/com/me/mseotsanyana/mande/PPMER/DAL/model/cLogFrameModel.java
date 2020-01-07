package com.me.mseotsanyana.mande.PPMER.DAL.model;

import com.me.mseotsanyana.mande.PPMER.DAL.cIndicatorModel;

import java.util.Date;
import java.util.HashSet;
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

    private Set<cLogFrameModel> logFrameModelSet;

    private Set<cImpactModel> impactModelSet;
    private Set<cOutcomeModel> outcomeModelSet;
    private Set<cOutputModel> outputModelSet;
    private Set<cActivityModel> activityModelSet;
    private Set<cInputModel> inputModelSet;
    private Set<cQuestionModel> questionModelSet;
    private Set<cIndicatorModel> indicatorModelSet;
    private Set<cRaidModel> raidModelSet;


    public cLogFrameModel(){
        logFrameModelSet = new HashSet<>();
        impactModelSet = new HashSet<>();
        outcomeModelSet = new HashSet<>();
        outputModelSet = new HashSet<>();
        activityModelSet = new HashSet<>();
        inputModelSet = new HashSet<>();
        questionModelSet = new HashSet<>();
        indicatorModelSet = new HashSet<>();
        raidModelSet = new HashSet<>();

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

    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
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

    public Set<cLogFrameModel> getLogFrameModelSet() {
        return logFrameModelSet;
    }

    public void setLogFrameModelSet(Set<cLogFrameModel> logFrameModelSet) {
        this.logFrameModelSet = logFrameModelSet;
    }

    public Set<cImpactModel> getImpactModelSet() {
        return impactModelSet;
    }

    public void setImpactModelSet(Set<cImpactModel> impactModelSet) {
        this.impactModelSet = impactModelSet;
    }

    public Set<cOutcomeModel> getOutcomeModelSet() {
        return outcomeModelSet;
    }

    public void setOutcomeModelSet(Set<cOutcomeModel> outcomeModelSet) {
        this.outcomeModelSet = outcomeModelSet;
    }

    public Set<cOutputModel> getOutputModelSet() {
        return outputModelSet;
    }

    public void setOutputModelSet(Set<cOutputModel> outputModelSet) {
        this.outputModelSet = outputModelSet;
    }

    public Set<cActivityModel> getActivityModelSet() {
        return activityModelSet;
    }

    public void setActivityModelSet(Set<cActivityModel> activityModelSet) {
        this.activityModelSet = activityModelSet;
    }

    public Set<cInputModel> getInputModelSet() {
        return inputModelSet;
    }

    public void setInputModelSet(Set<cInputModel> inputModelSet) {
        this.inputModelSet = inputModelSet;
    }

    public Set<cQuestionModel> getQuestionModelSet() {
        return questionModelSet;
    }

    public void setQuestionModelSet(Set<cQuestionModel> questionModelSet) {
        this.questionModelSet = questionModelSet;
    }

    public Set<cIndicatorModel> getIndicatorModelSet() {
        return indicatorModelSet;
    }

    public void setIndicatorModelSet(Set<cIndicatorModel> indicatorModelSet) {
        this.indicatorModelSet = indicatorModelSet;
    }

    public Set<cRaidModel> getRaidModelSet() {
        return raidModelSet;
    }

    public void setRaidModelSet(Set<cRaidModel> raidModelSet) {
        this.raidModelSet = raidModelSet;
    }
}
