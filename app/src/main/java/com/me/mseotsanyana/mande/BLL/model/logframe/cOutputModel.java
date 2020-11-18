package com.me.mseotsanyana.mande.BLL.model.logframe;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class cOutputModel {
    private long outputID;
    private long parentID;
    private long logFrameID;
    private long outcomeID;
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

    /*** incoming mappings ***/
    private cOutcomeModel outcomeModel;
    private cLogFrameModel logFrameModel;
    private Set<cOutputModel> childrenOutputModelSet;

    /*** outgoing mappings */
    private Set<cActivityModel> activityModelSet;
    private Set<cQuestionModel> questionModelSet;
    private Set<cRaidModel> raidModelSet;
    /* set of outcome in a child logframe for the output of the parent logframe */
    private Set<cOutcomeModel> childOutcomeModelSet;

    public cOutputModel(){
        /* incoming mapping */
        //outputModel = new cOutputModel();
        outcomeModel = new cOutcomeModel();
        logFrameModel = new cLogFrameModel();
        childrenOutputModelSet = new HashSet<>();
        /* outgoing mapping */
        activityModelSet = new HashSet<>();
        questionModelSet = new HashSet<>();
        raidModelSet = new HashSet<>();
        childOutcomeModelSet = new HashSet<>();
    }

    public long getOutputID() {
        return outputID;
    }

    public void setOutputID(long outputID) {
        this.outputID = outputID;
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

    public long getOutcomeID() {
        return outcomeID;
    }

    public void setOutcomeID(long outcomeID) {
        this.outcomeID = outcomeID;
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

    public cOutcomeModel getOutcomeModel() {
        return outcomeModel;
    }

    public void setOutcomeModel(cOutcomeModel outcomeModel) {
        this.outcomeModel = outcomeModel;
    }

    public cLogFrameModel getLogFrameModel() {
        return logFrameModel;
    }

    public void setLogFrameModel(cLogFrameModel logFrameModel) {
        this.logFrameModel = logFrameModel;
    }

    public Set<cActivityModel> getActivityModelSet() {
        return activityModelSet;
    }

    public void setActivityModelSet(Set<cActivityModel> activityModelSet) {
        this.activityModelSet = activityModelSet;
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

    public Set<cOutputModel> getChildrenOutputModelSet() {
        return childrenOutputModelSet;
    }

    public void setChildrenOutputModelSet(Set<cOutputModel> childrenOutputModelSet) {
        this.childrenOutputModelSet = childrenOutputModelSet;
    }

    public Set<cOutcomeModel> getChildOutcomeModelSet() {
        return childOutcomeModelSet;
    }

    public void setChildOutcomeModelSet(Set<cOutcomeModel> childOutcomeModelSet) {
        this.childOutcomeModelSet = childOutcomeModelSet;
    }
}

