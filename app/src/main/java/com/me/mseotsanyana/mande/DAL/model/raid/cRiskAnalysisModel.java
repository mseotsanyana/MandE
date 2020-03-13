package com.me.mseotsanyana.mande.DAL.model.raid;

import java.util.Date;

public class cRiskAnalysisModel {
    private int riskAnalysisID;
    private int riskMilestoneID;
    private int riskLikelihoodID;
    private int riskImpactID;
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

    public int getRiskAnalysisID() {
        return riskAnalysisID;
    }

    public void setRiskAnalysisID(int riskAnalysisID) {
        this.riskAnalysisID = riskAnalysisID;
    }

    public int getRiskMilestoneID() {
        return riskMilestoneID;
    }

    public void setRiskMilestoneID(int riskMilestoneID) {
        this.riskMilestoneID = riskMilestoneID;
    }

    public int getRiskLikelihoodID() {
        return riskLikelihoodID;
    }

    public void setRiskLikelihoodID(int riskLikelihoodID) {
        this.riskLikelihoodID = riskLikelihoodID;
    }

    public int getRiskImpactID() {
        return riskImpactID;
    }

    public void setRiskImpactID(int riskImpactID) {
        this.riskImpactID = riskImpactID;
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
}