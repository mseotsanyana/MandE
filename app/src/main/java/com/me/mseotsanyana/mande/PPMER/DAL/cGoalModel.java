package com.me.mseotsanyana.mande.PPMER.DAL;

import java.lang.String;
import java.util.Date;

public class cGoalModel {
	private int goalID;
    private int organizationID;
    private int ownerID;
	private String goalName;
	private String goalDescription;
    private Date createDate;

    public cGoalModel(){

    }

	cGoalModel(int goalID, int organizationID, int ownerID,
               String goalName, String goalDescription, Date createDate){
		this.goalID          = goalID;
        this.organizationID  = organizationID;
        this.ownerID         = ownerID;
        this.goalName        = goalName;
        this.goalDescription = goalDescription;
        this.createDate      = createDate;

	}

    public int getGoalID() {
        return goalID;
    }

    public void setGoalID(int goalID) {
        this.goalID = goalID;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
