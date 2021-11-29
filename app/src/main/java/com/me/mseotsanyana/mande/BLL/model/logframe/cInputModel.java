package com.me.mseotsanyana.mande.BLL.model.logframe;

import com.me.mseotsanyana.mande.BLL.model.wpb.cJournalModel;

import java.util.ArrayList;
import java.util.List;

public class cInputModel extends cComponentModel{
	private String activityServerID;
	private cResourceTypeModel resourceTypeModel;

	private List<cJournalModel> journalModels;

	/* set of activity in a sub-logframe for the parent input */
	private List<cActivityModel> subactivityModels;

	public cInputModel(){
		activityServerID = null;
		resourceTypeModel = new cResourceTypeModel();
		journalModels = new ArrayList<>();
		subactivityModels = new ArrayList<>();
	}

	public String getActivityServerID() {
		return activityServerID;
	}

	public void setActivityServerID(String activityServerID) {
		this.activityServerID = activityServerID;
	}

	public cResourceTypeModel getResourceTypeModel() {
		return resourceTypeModel;
	}

	public void setResourceTypeModel(cResourceTypeModel resourceTypeModel) {
		this.resourceTypeModel = resourceTypeModel;
	}

	public List<cJournalModel> getJournalModels() {
		return journalModels;
	}

	public void setJournalModels(List<cJournalModel> journalModels) {
		this.journalModels = journalModels;
	}

	public List<cActivityModel> getSubactivityModels() {
		return subactivityModels;
	}

	public void setSubactivityModels(List<cActivityModel> subactivityModels) {
		this.subactivityModels = subactivityModels;
	}
}

