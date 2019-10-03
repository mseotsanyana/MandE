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
    private ArrayList<cQuestionModel> questionModels;
    private ArrayList<cOutcomeImpactModel> outcomeImpactModels;

    public static class cOutcomeImpactModel{
        private int outcomeID;
        private int impactID;
        private int parentID;
        private int childID;
        private int serverID;
        private int ownerID;
        private int orgID;
        private int groupBITS;
        private int permsBITS;
        private int statusBITS;
        private Date createdDate;
        private Date modifiedDate;
        private Date syncedDate;

        public cOutcomeImpactModel(){}

        public cOutcomeImpactModel(int outcomeID, int impactID, int parentID, int childID,
                                   int serverID, int ownerID, int orgID, int groupBITS, int permsBITS,
                                   int statusBITS, Date createdDate, Date modifiedDate, Date syncedDate) {
            this.outcomeID = outcomeID;
            this.impactID = impactID;
            this.parentID = parentID;
            this.childID = childID;
            this.serverID = serverID;
            this.ownerID = ownerID;
            this.orgID = orgID;
            this.groupBITS = groupBITS;
            this.permsBITS = permsBITS;
            this.statusBITS = statusBITS;
            this.createdDate = createdDate;
            this.modifiedDate = modifiedDate;
            this.syncedDate = syncedDate;
        }

        public int getOutcomeID() {
            return outcomeID;
        }

        public void setOutcomeID(int outcomeID) {
            this.outcomeID = outcomeID;
        }

        public int getImpactID() {
            return impactID;
        }

        public void setImpactID(int impactID) {
            this.impactID = impactID;
        }

        public int getParentID() {
            return parentID;
        }

        public void setParentID(int parentID) {
            this.parentID = parentID;
        }

        public int getChildID() {
            return childID;
        }

        public void setChildID(int childID) {
            this.childID = childID;
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

    public cOutcomeModel(){}

    public cOutcomeModel(int ID, int serverID, int ownerID, int orgID,
                         int groupBITS, int permsBITS, int statusBITS,
                         String name, String description, Date startDate,
                         Date endDate, Date createdDate, Date modifiedDate,
                         Date syncedDate, cLogFrameModel logFrameModel,
                         cImpactModel impactModel, ArrayList<cOutcomeModel> outcomeModels,
                         ArrayList<cOutputModel> outputModels, ArrayList<cRaidModel> raidModels,
                         ArrayList<cQuestionModel> questionModels, ArrayList<cOutcomeImpactModel> outcomeImpactModels) {
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
        this.raidModels = raidModels;
        this.questionModels = questionModels;
        this.outcomeImpactModels = outcomeImpactModels;
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

    public ArrayList<cQuestionModel> getQuestionModels() {
        return questionModels;
    }

    public void setQuestionModels(ArrayList<cQuestionModel> questionModels) {
        this.questionModels = questionModels;
    }

    public ArrayList<cOutcomeImpactModel> getOutcomeImpactModels() {
        return outcomeImpactModels;
    }

    public void setOutcomeImpactModels(ArrayList<cOutcomeImpactModel> outcomeImpactModels) {
        this.outcomeImpactModels = outcomeImpactModels;
    }
}
