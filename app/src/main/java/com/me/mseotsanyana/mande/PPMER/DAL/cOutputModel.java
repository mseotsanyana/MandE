package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.ArrayList;
import java.util.Date;

public class cOutputModel {
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
    private cOutcomeModel outcomeModel;
    private ArrayList<cOutputModel> outputModels;

    /* many to many */
    private ArrayList<cActivityModel> activityModels;
    private ArrayList<cRaidModel> raidModels;
    private ArrayList<cOutputOutcomeModel> outputOutcomeModels;
    private ArrayList<cActivityOutputModel> activityOutputModels;
    private ArrayList<cOutputQuestionModel> outputQuestionModels;

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

    private class cActivityOutputModel{
        private cActivityModel activityModel;
        private cOutputModel outputModel;
        private cLogFrameModel parentModel;
        private cLogFrameModel childModel;

        public cActivityOutputModel(cActivityModel activityModel, cOutputModel outputModel,
                                    cLogFrameModel parentModel, cLogFrameModel childModel) {
            this.activityModel = activityModel;
            this.outputModel = outputModel;
            this.parentModel = parentModel;
            this.childModel = childModel;
        }
    }

    private class cOutputQuestionModel{
        private cOutputModel outputModel;
        private cQuestionModel questionModel;
        private cCriteriaModel criteriaModel;

        public cOutputQuestionModel(cOutputModel outputModel, cQuestionModel questionModel,
                                    cCriteriaModel criteriaModel) {
            this.outputModel = outputModel;
            this.questionModel = questionModel;
            this.criteriaModel = criteriaModel;
        }
    }

    public cOutputModel(int ID, int serverID, int ownerID, int orgID,
                        int groupBITS, int permsBITS, int statusBITS,
                        String name, String description, Date startDate, Date endDate,
                        Date createdDate, Date modifiedDate, Date syncedDate,
                        cLogFrameModel logFrameModel, cOutcomeModel outcomeModel,
                        ArrayList<cOutputModel> outputModels,
                        ArrayList<cActivityModel> activityModels,
                        ArrayList<cRaidModel> raidModels,
                        ArrayList<cOutputOutcomeModel> outputOutcomeModels,
                        ArrayList<cActivityOutputModel> activityOutputModels,
                        ArrayList<cOutputQuestionModel> outputQuestionModels) {
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
        this.outcomeModel = outcomeModel;
        this.outputModels = outputModels;
        this.activityModels = activityModels;
        this.raidModels = raidModels;
        this.outputOutcomeModels = outputOutcomeModels;
        this.activityOutputModels = activityOutputModels;
        this.outputQuestionModels = outputQuestionModels;
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

    public cOutcomeModel getOutcomeModel() {
        return outcomeModel;
    }

    public void setOutcomeModel(cOutcomeModel outcomeModel) {
        this.outcomeModel = outcomeModel;
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

    public ArrayList<cOutputOutcomeModel> getOutputOutcomeModels() {
        return outputOutcomeModels;
    }

    public void setOutputOutcomeModels(ArrayList<cOutputOutcomeModel> outputOutcomeModels) {
        this.outputOutcomeModels = outputOutcomeModels;
    }

    public ArrayList<cActivityOutputModel> getActivityOutputModels() {
        return activityOutputModels;
    }

    public void setActivityOutputModels(ArrayList<cActivityOutputModel> activityOutputModels) {
        this.activityOutputModels = activityOutputModels;
    }

    public ArrayList<cOutputQuestionModel> getOutputQuestionModels() {
        return outputQuestionModels;
    }

    public void setOutputQuestionModels(ArrayList<cOutputQuestionModel> outputQuestionModels) {
        this.outputQuestionModels = outputQuestionModels;
    }
}

