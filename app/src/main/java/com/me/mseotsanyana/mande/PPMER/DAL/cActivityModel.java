package com.me.mseotsanyana.mande.PPMER.DAL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class cActivityModel implements Serializable {
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
    private cOutputModel outputModel;
    private ArrayList<cActivityModel> activityModels;

    /* many to many */
    private ArrayList<cPrecedingActivityModel> precedingActivityModels;
    private ArrayList<cInputModel> inputModels;
    private ArrayList<cRaidModel> raidModels;
    private ArrayList<cQuestionModel> activityQuestionModels;
    private ArrayList<cActivityOutputModel> activityOutputModels;

    /* many to many related to AWPB */
    private ArrayList<cTaskModel> taskModels;
    private ArrayList<cActivityAssignmentModel> activityAssignmentModels;
    private ArrayList<cExpenditureModel> expenditureModels;

    public class cActivityOutputModel {
        private int activityID;
        private int outputID;
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

        public cActivityOutputModel() {
        }

        public cActivityOutputModel(int activityID, int outputID, int parentID, int childID,
                                    int serverID, int ownerID, int orgID, int groupBITS, int permsBITS,
                                    int statusBITS, Date createdDate, Date modifiedDate, Date syncedDate) {
            this.activityID = activityID;
            this.outputID = outputID;
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

        public int getActivityID() {
            return activityID;
        }

        public void setActivityID(int activityID) {
            this.activityID = activityID;
        }

        public int getOutputID() {
            return outputID;
        }

        public void setOutputID(int outputID) {
            this.outputID = outputID;
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

    public cActivityModel() {}

    public cActivityModel(int ID, int serverID, int ownerID, int orgID,
                          int groupBITS, int permsBITS, int statusBITS,
                          String name, String description, Date startDate, Date endDate,
                          Date createdDate, Date modifiedDate, Date syncedDate,
                          cLogFrameModel logFrameModel, cOutputModel outputModel,
                          ArrayList<cActivityModel> activityModels,
                          ArrayList<cPrecedingActivityModel> precedingActivityModels,
                          ArrayList<cInputModel> inputModels, ArrayList<cRaidModel> raidModels,
                          ArrayList<cQuestionModel> activityQuestionModels,
                          ArrayList<cActivityOutputModel> activityOutputModels, ArrayList<cTaskModel> taskModels,
                          ArrayList<cActivityAssignmentModel> activityAssignmentModels,
                          ArrayList<cExpenditureModel> expenditureModels) {
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
        this.outputModel = outputModel;
        this.activityModels = activityModels;
        this.precedingActivityModels = precedingActivityModels;
        this.inputModels = inputModels;
        this.raidModels = raidModels;
        this.activityQuestionModels = activityQuestionModels;
        this.activityOutputModels = activityOutputModels;
        this.taskModels = taskModels;
        this.activityAssignmentModels = activityAssignmentModels;
        this.expenditureModels = expenditureModels;
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

    public cOutputModel getOutputModel() {
        return outputModel;
    }

    public void setOutputModel(cOutputModel outputModel) {
        this.outputModel = outputModel;
    }

    public ArrayList<cActivityModel> getActivityModels() {
        return activityModels;
    }

    public void setActivityModels(ArrayList<cActivityModel> activityModels) {
        this.activityModels = activityModels;
    }

    public ArrayList<cPrecedingActivityModel> getPrecedingActivityModels() {
        return precedingActivityModels;
    }

    public void setPrecedingActivityModels(ArrayList<cPrecedingActivityModel> precedingActivityModels) {
        this.precedingActivityModels = precedingActivityModels;
    }

    public ArrayList<cInputModel> getInputModels() {
        return inputModels;
    }

    public void setInputModels(ArrayList<cInputModel> inputModels) {
        this.inputModels = inputModels;
    }

    public ArrayList<cRaidModel> getRaidModels() {
        return raidModels;
    }

    public void setRaidModels(ArrayList<cRaidModel> raidModels) {
        this.raidModels = raidModels;
    }

    public ArrayList<cQuestionModel> getActivityQuestionModels() {
        return activityQuestionModels;
    }

    public void setActivityQuestionModels(ArrayList<cQuestionModel> activityQuestionModels) {
        this.activityQuestionModels = activityQuestionModels;
    }

    public ArrayList<cActivityOutputModel> getActivityOutputModels() {
        return activityOutputModels;
    }

    public void setActivityOutputModels(ArrayList<cActivityOutputModel> activityOutputModels) {
        this.activityOutputModels = activityOutputModels;
    }

    public ArrayList<cTaskModel> getTaskModels() {
        return taskModels;
    }

    public void setTaskModels(ArrayList<cTaskModel> taskModels) {
        this.taskModels = taskModels;
    }

    public ArrayList<cActivityAssignmentModel> getActivityAssignmentModels() {
        return activityAssignmentModels;
    }

    public void setActivityAssignmentModels(ArrayList<cActivityAssignmentModel> activityAssignmentModels) {
        this.activityAssignmentModels = activityAssignmentModels;
    }

    public ArrayList<cExpenditureModel> getExpenditureModels() {
        return expenditureModels;
    }

    public void setExpenditureModels(ArrayList<cExpenditureModel> expenditureModels) {
        this.expenditureModels = expenditureModels;
    }
}

