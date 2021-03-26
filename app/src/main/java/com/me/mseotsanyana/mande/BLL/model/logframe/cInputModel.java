package com.me.mseotsanyana.mande.BLL.model.logframe;

import com.me.mseotsanyana.mande.BLL.model.wpb.cJournalModel;

import java.util.HashSet;
import java.util.Set;

public class cInputModel extends cComponentModel{
	/*** incoming mappings ***/
	private cResourceTypeModel resourceTypeModel;
	private cActivityModel activityModel;
	/*** outgoing mappings ***/
	private Set<cJournalModel> journalModelSet;
	/* set of activity in a sub-logframe for the parent input */
	private Set<cActivityModel> childActivityModelSet;

	public cInputModel(){
		activityModel = new cActivityModel();
		resourceTypeModel = new cResourceTypeModel();

		journalModelSet = new HashSet<>();
		childActivityModelSet = new HashSet<>();
	}

	public cInputModel(cInputModel inputModel){
		setServerID(inputModel.getServerID());
		setOwnerID(inputModel.getOwnerID());
		setOrgID(inputModel.getOrgID());
		setGroupBITS(inputModel.getGroupBITS());
		setPermsBITS(inputModel.getPermsBITS());
		setStatusBITS(inputModel.getStatusBITS());
		setStartDate(inputModel.getStartDate());
		setEndDate(inputModel.getEndDate());
		setCreatedDate(inputModel.getCreatedDate());
		setModifiedDate(inputModel.getModifiedDate());
		setSyncedDate(inputModel.getSyncedDate());

		setResourceTypeModel(inputModel.getResourceTypeModel());
		setLogFrameModel(inputModel.getLogFrameModel());
		setActivityModel(inputModel.getActivityModel());
	}

	public cResourceTypeModel getResourceTypeModel() {
		return resourceTypeModel;
	}

	public void setResourceTypeModel(cResourceTypeModel resourceTypeModel) {
		this.resourceTypeModel = resourceTypeModel;
	}

	public cActivityModel getActivityModel() {
		return activityModel;
	}

	public void setActivityModel(cActivityModel activityModel) {
		this.activityModel = activityModel;
	}

	public Set<cJournalModel> getJournalModelSet() {
		return journalModelSet;
	}

	public void setJournalModelSet(Set<cJournalModel> journalModelSet) {
		this.journalModelSet = journalModelSet;
	}

	public Set<cActivityModel> getChildActivityModelSet() {
		return childActivityModelSet;
	}

	public void setChildActivityModelSet(Set<cActivityModel> childActivityModelSet) {
		this.childActivityModelSet = childActivityModelSet;
	}
}

