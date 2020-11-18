package com.me.mseotsanyana.mande.BLL.model.raid;

import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;

import java.util.Date;

public class cRAIDLOGModel {
    private long raidLogID;
    private long serverID;
    private long ownerID;
    private long orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String name;
    private String description;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    private cLogFrameModel logFrameModel;
    private cRiskRegisterModel riskRegisterModel;
    private cAssumptionRegisterModel assRegisterModel;
    private cIssueRegisterModel issueRegisterModel;
    private cDependencyRegisterModel depRegisterModel;

    public cRAIDLOGModel(){
        logFrameModel = new cLogFrameModel();
        riskRegisterModel = new cRiskRegisterModel();
        assRegisterModel = new cAssumptionRegisterModel();
        issueRegisterModel = new cIssueRegisterModel();
        depRegisterModel = new cDependencyRegisterModel();
    }

    public long getRaidLogID() {
        return raidLogID;
    }

    public void setRaidLogID(long raidLogID) {
        this.raidLogID = raidLogID;
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

    public cRiskRegisterModel getRiskRegisterModel() {
        return riskRegisterModel;
    }

    public void setRiskRegisterModel(cRiskRegisterModel riskRegisterModel) {
        this.riskRegisterModel = riskRegisterModel;
    }

    public cAssumptionRegisterModel getAssRegisterModel() {
        return assRegisterModel;
    }

    public void setAssRegisterModel(cAssumptionRegisterModel assRegisterModel) {
        this.assRegisterModel = assRegisterModel;
    }

    public cIssueRegisterModel getIssueRegisterModel() {
        return issueRegisterModel;
    }

    public void setIssueRegisterModel(cIssueRegisterModel issueRegisterModel) {
        this.issueRegisterModel = issueRegisterModel;
    }

    public cDependencyRegisterModel getDepRegisterModel() {
        return depRegisterModel;
    }

    public void setDepRegisterModel(cDependencyRegisterModel depRegisterModel) {
        this.depRegisterModel = depRegisterModel;
    }
}
