package com.me.mseotsanyana.mande.UTIL.DAL;


import java.util.Date;

public class cSpecificAimModel
{
    private int projectID;
    private int overallAimID;
    private int ownerID;
    private String specificAimName;
	private String specificAimDescription;
    private Date createDate;

    public cSpecificAimModel() {
        super();
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }


    public int getOverallAimID() {
        return overallAimID;
    }

    public void setOverallAimID(int overallAimID) {
        this.overallAimID = overallAimID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getSpecificAimName() {
        return specificAimName;
    }

    public void setSpecificAimName(String specificAimName) {
        this.specificAimName = specificAimName;
    }

    public String getSpecificAimDescription() {
        return specificAimDescription;
    }

    public void setSpecificAimDescription(String specificAimDescription) {
        this.specificAimDescription = specificAimDescription;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
