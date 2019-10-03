package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.ArrayList;
import java.util.Date;

public class cRaidModel {
    private int ID;
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

    private cRiskIssueModel riskIssueModel;
    private cDependencyModel dependencyModel;
    private cAssumptionModel assumptionModel;

    private ArrayList<cImpactModel> impactModels;
    private ArrayList<cOutcomeModel> outcomeModels;
    private ArrayList<cOutputModel> outputModels;
    private ArrayList<cActivityModel> activityModels;

    public cRaidModel(){}

    public cRaidModel(int ID, int serverID, int ownerID, int orgID,
                      int groupBITS, int permsBITS, int statusBITS,
                      String name, String description, Date createdDate,
                      Date modifiedDate, Date syncedDate, cLogFrameModel logFrameModel,
                      cRiskIssueModel riskIssueModel, cDependencyModel dependencyModel,
                      cAssumptionModel assumptionModel, ArrayList<cImpactModel> impactModels,
                      ArrayList<cOutcomeModel> outcomeModels, ArrayList<cOutputModel> outputModels,
                      ArrayList<cActivityModel> activityModels) {
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
        this.riskIssueModel = riskIssueModel;
        this.dependencyModel = dependencyModel;
        this.assumptionModel = assumptionModel;
        this.impactModels = impactModels;
        this.outcomeModels = outcomeModels;
        this.outputModels = outputModels;
        this.activityModels = activityModels;
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

    public cRiskIssueModel getRiskIssueModel() {
        return riskIssueModel;
    }

    public void setRiskIssueModel(cRiskIssueModel riskIssueModel) {
        this.riskIssueModel = riskIssueModel;
    }

    public cDependencyModel getDependencyModel() {
        return dependencyModel;
    }

    public void setDependencyModel(cDependencyModel dependencyModel) {
        this.dependencyModel = dependencyModel;
    }

    public cAssumptionModel getAssumptionModel() {
        return assumptionModel;
    }

    public void setAssumptionModel(cAssumptionModel assumptionModel) {
        this.assumptionModel = assumptionModel;
    }

    public ArrayList<cImpactModel> getImpactModels() {
        return impactModels;
    }

    public void setImpactModels(ArrayList<cImpactModel> impactModels) {
        this.impactModels = impactModels;
    }

    public ArrayList<cOutcomeModel> getOutcomeModels() {
        return outcomeModels;
    }

    public void setOutcomeModels(ArrayList<cOutcomeModel> outcomeModels) {
        this.outcomeModels = outcomeModels;
    }

    public ArrayList<cOutputModel> getOutputModels() {
        return outputModels;
    }

    public void setOutputModels(ArrayList<cOutputModel> outputModels) {
        this.outputModels = outputModels;
    }

    public ArrayList<cActivityModel> getActivityModels() {
        return activityModels;
    }

    public void setActivityModels(ArrayList<cActivityModel> activityModels) {
        this.activityModels = activityModels;
    }
}
