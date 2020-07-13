package com.me.mseotsanyana.mande.DAL.model.logframe;

import android.util.Pair;

import java.lang.String;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class cImpactModel {
	private long impactID;
	private long parentID;
	private long logFrameID;
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
/*
    /*** incoming mappings ***/
    private cLogFrameModel logFrameModel;
    private Set<cImpactModel> impactModelSet; //children

    /*** outgoing mappings ***/
    private Set<cOutcomeModel> outcomeModelSet;
    private Set<cQuestionModel> questionModelSet;
    private Set<cRaidModel> raidModelSet;
    /* a parent outcome of the impact in a sub-logframe */

    public cImpactModel(){
        /* incoming mappings */
        logFrameModel = new cLogFrameModel();
        impactModelSet = new HashSet<>();
        /* outgoing mappings */
        outcomeModelSet = new HashSet<>();
        questionModelSet = new HashSet<>();
        raidModelSet = new HashSet<>();
    }

    public long getImpactID() {
        return impactID;
    }

    public void setImpactID(long impactID) {
        this.impactID = impactID;
    }

    public long getParentID() {
        return parentID;
    }

    public void setParentID(long parentID) {
        this.parentID = parentID;
    }

    public long getLogFrameID() {
        return logFrameID;
    }

    public void setLogFrameID(long logFrameID) {
        this.logFrameID = logFrameID;
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

    public cLogFrameModel getLogFrameModel() {
        return logFrameModel;
    }

    public void setLogFrameModel(cLogFrameModel logFrameModel) {
        this.logFrameModel = logFrameModel;
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

    public Set<cQuestionModel> getQuestionModelSet() {
        return questionModelSet;
    }

    public void setQuestionModelSet(Set<cQuestionModel> questionModelSet) {
        this.questionModelSet = questionModelSet;
    }

    public Set<cRaidModel> getRaidModelSet() {
        return raidModelSet;
    }

    public void setRaidModelSet(Set<cRaidModel> raidModelSet) {
        this.raidModelSet = raidModelSet;
    }
}
