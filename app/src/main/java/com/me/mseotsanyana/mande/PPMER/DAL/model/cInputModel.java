package com.me.mseotsanyana.mande.PPMER.DAL.model;

import com.me.mseotsanyana.mande.UTILITY.DAL.cBudgetModel;
import com.me.mseotsanyana.mande.UTILITY.DAL.cHumanSetModel;
import com.me.mseotsanyana.mande.UTILITY.DAL.cMaterialModel;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class cInputModel {
	private int inputID;
	private int activityPlanningID;
	private int logFrameID;
	private int resourceID;
	private int serverID;
	private int ownerID;
	private int orgID;
	private int groupBITS;
	private int permsBITS;
	private int statusBITS;
	//private String name;
	//private String description;
	private Date startDate;
	private Date endDate;
	private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    /* foreign key */
    private cLogFrameModel logFrameModel;
    private cActivityModel activityModel;

    /* one to one */
	private cHumanSetModel humanSetModel;
	private cMaterialModel materialModel;
	private cBudgetModel budgetModel;

	/* many to many*/
    private Set<cQuestionModel> questionModelSet;

	public cInputModel(){
		questionModelSet = new HashSet<>();
	}

	public int getInputID() {
		return inputID;
	}

	public void setInputID(int inputID) {
		this.inputID = inputID;
	}

	public int getActivityPlanningID() {
		return activityPlanningID;
	}

	public void setActivityPlanningID(int activityPlanningID) {
		this.activityPlanningID = activityPlanningID;
	}

	public int getLogFrameID() {
		return logFrameID;
	}

	public void setLogFrameID(int logFrameID) {
		this.logFrameID = logFrameID;
	}

	public int getResourceID() {
		return resourceID;
	}

	public void setResourceID(int resourceID) {
		this.resourceID = resourceID;
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

	public Set<cQuestionModel> getQuestionModelSet() {
		return questionModelSet;
	}

	public void setQuestionModelSet(Set<cQuestionModel> questionModelSet) {
		this.questionModelSet = questionModelSet;
	}
}

