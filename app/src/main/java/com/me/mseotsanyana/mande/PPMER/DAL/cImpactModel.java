package com.me.mseotsanyana.mande.PPMER.DAL;

import java.lang.String;
import java.util.ArrayList;
import java.util.Date;

public class cImpactModel {
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
    private ArrayList<cImpactModel> impactModels;

    /* many to many */
    private ArrayList<cOutcomeModel> outcomeModels;
    private ArrayList<cRaidModel> raidModels;
    //private ArrayList<cOutcomeImpactModel> outcomeImpactModels;
    private ArrayList<cImpactQuestionModel> impactQuestionModels;

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

    private class cImpactQuestionModel{
        private cImpactModel impactModel;
        private cQuestionModel questionModel;
        private cCriteriaModel criteriaModel;

        public cImpactQuestionModel(cImpactModel impactModel, cQuestionModel questionModel,
                                    cCriteriaModel criteriaModel) {
            this.impactModel = impactModel;
            this.questionModel = questionModel;
            this.criteriaModel = criteriaModel;
        }
    }

    public cImpactModel(){}

    public cImpactModel(int ID, int serverID, int ownerID, int orgID,
                        int groupBITS, int permsBITS, int statusBITS,
                        String name, String description, Date startDate,
                        Date endDate, Date createdDate, Date modifiedDate,
                        Date syncedDate, cLogFrameModel logFrameModel,
                        ArrayList<cOutcomeModel> outcomeModels,
                        ArrayList<cImpactModel> impactModels,
                        ArrayList<cOutcomeImpactModel> outcomeImpactModels,
                        ArrayList<cImpactQuestionModel> impactQuestionModels,
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
        this.outcomeModels = outcomeModels;
        this.impactModels = impactModels;
        //this.outcomeImpactModels = outcomeImpactModels;
        this.impactQuestionModels = impactQuestionModels;
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

    public ArrayList<cOutcomeModel> getOutcomeModels() {
        return outcomeModels;
    }

    public void setOutcomeModels(ArrayList<cOutcomeModel> outcomeModels) {
        this.outcomeModels = outcomeModels;
    }

    public ArrayList<cImpactModel> getImpactModels() {
        return impactModels;
    }

    public void setImpactModels(ArrayList<cImpactModel> impactModels) {
        this.impactModels = impactModels;
    }
/*
    public ArrayList<cOutcomeImpactModel> getOutcomeImpactModels() {
        return outcomeImpactModels;
    }

    public void setOutcomeImpactModels(ArrayList<cOutcomeImpactModel> outcomeImpactModels) {
        this.outcomeImpactModels = outcomeImpactModels;
    }
*/
    public ArrayList<cImpactQuestionModel> getImpactQuestionModels() {
        return impactQuestionModels;
    }

    public void setImpactQuestionModels(ArrayList<cImpactQuestionModel> impactQuestionModels) {
        this.impactQuestionModels = impactQuestionModels;
    }

    public ArrayList<cRaidModel> getRaidModels() {
        return raidModels;
    }

    public void setRaidModels(ArrayList<cRaidModel> raidModels) {
        this.raidModels = raidModels;
    }
}
