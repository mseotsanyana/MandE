package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.ArrayList;
import java.util.Date;

public class cOutcomeModel {
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

    /* foreign key */
    private cLogFrameModel logFrameModel;
    private cImpactModel impactModel;
    private ArrayList<cOutcomeModel> outcomeModels;

    /* many to many */
    private ArrayList<cOutputModel> outputModels;
    private ArrayList<cRaidModel> raidModels;
    private ArrayList<cOutcomeImpactModel> outcomeImpactModels;
    private ArrayList<cOutputOutcomeModel> outputOutcomeModels;
    private ArrayList<cOutcomeQuestionModel> outcomeQuestionModels;

    private class cOutcomeImpactModel{
        private cOutcomeModel outcomeModel;
        private cImpactModel impactModel;
        private cLogFrameModel parentModel;
        private cLogFrameModel childModel;

        public cOutcomeImpactModel(cOutcomeModel outcomeModel, cImpactModel impactModel,
                                   cLogFrameModel parentModel, cLogFrameModel childModel) {
            this.outcomeModel = outcomeModel;
            this.impactModel = impactModel;
            this.parentModel = parentModel;
            this.childModel = childModel;
        }
    }

    private class cOutputOutcomeModel{
        private cOutputModel outputModel;
        private cOutcomeModel outcomeModel;
        private cLogFrameModel parentModel;
        private cLogFrameModel childModel;

        public cOutputOutcomeModel(cOutputModel outputModel, cOutcomeModel outcomeModel,
                                   cLogFrameModel parentModel, cLogFrameModel childModel) {
            this.outputModel = outputModel;
            this.outcomeModel = outcomeModel;
            this.parentModel = parentModel;
            this.childModel = childModel;
        }
    }

    private class cOutcomeQuestionModel{
        private cOutcomeModel outcomeModel;
        private cQuestionModel questionModel;
        private cCriteriaModel criteriaModel;

        public cOutcomeQuestionModel(cOutcomeModel outcomeModel, cQuestionModel questionModel,
                                     cCriteriaModel criteriaModel) {
            this.outcomeModel = outcomeModel;
            this.questionModel = questionModel;
            this.criteriaModel = criteriaModel;
        }
    }

    public cOutcomeModel(int ID, int serverID, int ownerID, int orgID,
                         int groupBITS, int permsBITS, int statusBITS,
                         String name, String description, Date startDate, Date endDate,
                         Date createdDate, Date modifiedDate, Date syncedDate,
                         cLogFrameModel logFrameModel, cImpactModel impactModel,
                         ArrayList<cOutcomeModel> outcomeModels,
                         ArrayList<cOutputModel> outputModels,
                         ArrayList<cOutcomeImpactModel> outcomeImpactModels,
                         ArrayList<cOutputOutcomeModel> outputOutcomeModels,
                         ArrayList<cOutcomeQuestionModel> outcomeQuestionModels,
                         ArrayList<cRaidModel> raidModels) {
        this.ID = ID;
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
        this.logFrameModel = logFrameModel;
        this.impactModel = impactModel;
        this.outcomeModels = outcomeModels;
        this.outputModels = outputModels;
        this.outcomeImpactModels = outcomeImpactModels;
        this.outputOutcomeModels = outputOutcomeModels;
        this.outcomeQuestionModels = outcomeQuestionModels;
        this.raidModels = raidModels;
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

    public cImpactModel getImpactModel() {
        return impactModel;
    }

    public void setImpactModel(cImpactModel impactModel) {
        this.impactModel = impactModel;
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

    public ArrayList<cRaidModel> getRaidModels() {
        return raidModels;
    }

    public void setRaidModels(ArrayList<cRaidModel> raidModels) {
        this.raidModels = raidModels;
    }

    public ArrayList<cOutcomeImpactModel> getOutcomeImpactModels() {
        return outcomeImpactModels;
    }

    public void setOutcomeImpactModels(ArrayList<cOutcomeImpactModel> outcomeImpactModels) {
        this.outcomeImpactModels = outcomeImpactModels;
    }

    public ArrayList<cOutputOutcomeModel> getOutputOutcomeModels() {
        return outputOutcomeModels;
    }

    public void setOutputOutcomeModels(ArrayList<cOutputOutcomeModel> outputOutcomeModels) {
        this.outputOutcomeModels = outputOutcomeModels;
    }

    public ArrayList<cOutcomeQuestionModel> getOutcomeQuestionModels() {
        return outcomeQuestionModels;
    }

    public void setOutcomeQuestionModels(ArrayList<cOutcomeQuestionModel> outcomeQuestionModels) {
        this.outcomeQuestionModels = outcomeQuestionModels;
    }
}
