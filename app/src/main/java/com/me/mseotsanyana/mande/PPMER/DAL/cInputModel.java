package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.ArrayList;
import java.util.Date;

public class cInputModel {
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
    private cActivityModel activityModel;

    /* many to many */
	private cHumanSetModel humanSetModel;
	private cMaterialModel materialModel;
	private cBudgetModel budgetModel;
    private ArrayList<cInputQuestionModel> inputQuestionModels;

	private class cInputQuestionModel{
		private int inputID;
		private int questionID;
		private int criteriaID;

		public cInputQuestionModel(int inputID, int questionID, int criteriaID) {
			this.inputID = inputID;
			this.questionID = questionID;
			this.criteriaID = criteriaID;
		}
	}

	public cInputModel(){}

	public cInputModel(int ID, int serverID, int ownerID, int orgID,
					   int groupBITS, int permsBITS, int statusBITS,
					   String name, String description, Date createdDate,
					   Date modifiedDate, Date syncedDate, cLogFrameModel logFrameModel,
					   cActivityModel activityModel, cHumanSetModel humanSetModel,
					   cMaterialModel materialModel, cBudgetModel budgetModel,
					   ArrayList<cInputQuestionModel> inputQuestionModels) {
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
		this.activityModel = activityModel;
		this.humanSetModel = humanSetModel;
		this.materialModel = materialModel;
		this.budgetModel = budgetModel;
		this.inputQuestionModels = inputQuestionModels;
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

	public cActivityModel getActivityModel() {
		return activityModel;
	}

	public void setActivityModel(cActivityModel activityModel) {
		this.activityModel = activityModel;
	}

	public cHumanSetModel getHumanSetModel() {
		return humanSetModel;
	}

	public void setHumanSetModel(cHumanSetModel humanSetModel) {
		this.humanSetModel = humanSetModel;
	}

	public cMaterialModel getMaterialModel() {
		return materialModel;
	}

	public void setMaterialModel(cMaterialModel materialModel) {
		this.materialModel = materialModel;
	}

	public cBudgetModel getBudgetModel() {
		return budgetModel;
	}

	public void setBudgetModel(cBudgetModel budgetModel) {
		this.budgetModel = budgetModel;
	}

	public ArrayList<cInputQuestionModel> getInputQuestionModels() {
		return inputQuestionModels;
	}

	public void setInputQuestionModels(ArrayList<cInputQuestionModel> inputQuestionModels) {
		this.inputQuestionModels = inputQuestionModels;
	}
}

