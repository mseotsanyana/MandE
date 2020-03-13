package com.me.mseotsanyana.mande.BLL.domain.logframe;

import com.me.mseotsanyana.mande.UTIL.BLL.cIndicatorDomain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class cLogFrameDomain {
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

    private Set<cLogFrameDomain> logFrameModelSet;

    //private Set<cImpactDomain> impactDomainSet;
    private Set<cOutcomeDomain> outcomeDomainSet;
    private Set<cOutputDomain> outputDomainSet;
    private Set<cActivityDomain> activityDomainSet;
    //private Set<cInputDomain> inputDomainSet;
    //private Set<cQuestionDomain> questionDomainSet;
    private Set<cIndicatorDomain> indicatorDomainSet;
   // private Set<cRaidDomain> raidDomainSet;


    public cLogFrameDomain(){
        logFrameModelSet = new HashSet<>();
        /*impactModelSet = new HashSet<>();
        outcomeModelSet = new HashSet<>();
        outputModelSet = new HashSet<>();
        activityModelSet = new HashSet<>();
        inputModelSet = new HashSet<>();
        questionModelSet = new HashSet<>();
        indicatorModelSet = new HashSet<>();
        raidModelSet = new HashSet<>();*/

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
}
