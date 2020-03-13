package com.me.mseotsanyana.mande.DAL.model.logframe;

import android.util.Pair;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class cOutcomeModel {
    private int outcomeID;
    private int parentID;
    private int logFrameID;
    private int impactID;
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

    /*** incoming mappings ***/
    private cLogFrameModel logFrameModel;
    private cImpactModel impactModel;
    private cOutcomeModel outcomeModel;
    private Set<cOutcomeModel> outcomeModelSet;

    /*** outgoing mappings ***/
    private Set<cOutputModel> outputModelSet;
    private Set<cQuestionModel> questionModelSet;
    private Set<cRaidModel> raidModelSet;
    /* set of impact in a sub-logframe for the parent outcome */
    private Map<Pair<cLogFrameModel, cLogFrameModel>, Set<cImpactModel>> outcomeModelSetMap;
    /* a parent output of the outcome in a sub-logframe */
    private Map<Pair<cLogFrameModel, cLogFrameModel>, cOutputModel> outcomeModelMap;

    public cOutcomeModel(){
        /* incoming mappings */
        logFrameModel = new cLogFrameModel();
        impactModel = new cImpactModel();
        //outcomeModel = new cOutcomeModel();
        outcomeModelSet = new HashSet<>();
        /* outgoing mappings */
        outputModelSet = new HashSet<>();
        questionModelSet = new HashSet<>();
        raidModelSet = new HashSet<>();
        outcomeModelSetMap = new HashMap<>();
        outcomeModelMap = new HashMap<>();
    }

    public int getOutcomeID() {
        return outcomeID;
    }

    public void setOutcomeID(int outcomeID) {
        this.outcomeID = outcomeID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getLogFrameID() {
        return logFrameID;
    }

    public void setLogFrameID(int logFrameID) {
        this.logFrameID = logFrameID;
    }

    public int getImpactID() {
        return impactID;
    }

    public void setImpactID(int impactID) {
        this.impactID = impactID;
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

    public cImpactModel getImpactModel() {
        return impactModel;
    }

    public void setImpactModel(cImpactModel impactModel) {
        this.impactModel = impactModel;
    }

    public cOutcomeModel getOutcomeModel() {
        return outcomeModel;
    }

    public void setOutcomeModel(cOutcomeModel outcomeModel) {
        this.outcomeModel = outcomeModel;
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

    public Map<Pair<cLogFrameModel, cLogFrameModel>, Set<cImpactModel>> getOutcomeModelSetMap() {
        return outcomeModelSetMap;
    }

    public void setOutcomeModelSetMap(Map<Pair<cLogFrameModel, cLogFrameModel>, Set<cImpactModel>> outcomeModelSetMap) {
        this.outcomeModelSetMap = outcomeModelSetMap;
    }

    public Map<Pair<cLogFrameModel, cLogFrameModel>, cOutputModel> getOutcomeModelMap() {
        return outcomeModelMap;
    }

    public void setOutcomeModelMap(Map<Pair<cLogFrameModel, cLogFrameModel>, cOutputModel> outcomeModelMap) {
        this.outcomeModelMap = outcomeModelMap;
    }
}