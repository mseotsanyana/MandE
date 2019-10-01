package com.me.mseotsanyana.mande.PPMER.DAL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class cPrecedingActivityModel implements Serializable{
	private int ID;
	private int serverID;
	private int ownerID;
	private int orgID;
	private int groupBITS;
	private int permsBITS;
	private int statusBITS;
	private Date createdDate;
	private Date modifiedDate;
	private Date syncedDate;

	private cActivityModel activityModel;
	private cActivityModel precedingActivity;

	public cPrecedingActivityModel(int ID, int serverID, int ownerID, int orgID,
								   int groupBITS, int permsBITS, int statusBITS,
								   Date createdDate, Date modifiedDate, Date syncedDate,
								   cActivityModel activityModel, cActivityModel precedingActivity) {
		this.ID = ID;
		this.serverID = serverID;
		this.ownerID = ownerID;
		this.orgID = orgID;
		this.groupBITS = groupBITS;
		this.permsBITS = permsBITS;
		this.statusBITS = statusBITS;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.syncedDate = syncedDate;
		this.activityModel = activityModel;
		this.precedingActivity = precedingActivity;
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

	public cActivityModel getActivityModel() {
		return activityModel;
	}

	public void setActivityModel(cActivityModel activityModel) {
		this.activityModel = activityModel;
	}

	public cActivityModel getPrecedingActivity() {
		return precedingActivity;
	}

	public void setPrecedingActivity(cActivityModel precedingActivity) {
		this.precedingActivity = precedingActivity;
	}
}

