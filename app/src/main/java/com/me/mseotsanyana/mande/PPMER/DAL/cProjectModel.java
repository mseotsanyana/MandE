package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.Date;

public class cProjectModel
{
    public int projectID;
    private int overallAimID;
    private int specificAimID;
    private int ownerID;
    private int projectManagerID;
    private String projectName;
    private String projectDescription;
    private String country;
    private String region;
    private int projectStatus;
    private Date createDate;
    private Date startDate;
    private Date closeDate;

    public cProjectModel(){}

    public cProjectModel(int projectID, int overallAimID, int specificAimID, int ownerID, int projectManagerID,
                         String projectName, String projectDescription, String country, String region, int projectStatus,
                         Date createDate, Date startDate, Date closeDate){
        this.projectID          = projectID;
        this.overallAimID       = overallAimID;
        this.specificAimID      = specificAimID;
        this.ownerID            = ownerID;
        this.projectManagerID   = projectManagerID;
        this.projectName        = projectName;
        this.projectDescription = projectDescription;
        this.country            = country;
        this.region             = region;
        this.projectStatus      = projectStatus;
        this.createDate         = createDate;
        this.startDate          = startDate;
        this.closeDate          = closeDate;
    }

	public int getProjectID()
    {
		return projectID;
	}
	
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public int getProjectManagerID() {
        return projectManagerID;
    }

    public void setProjectManagerID(int projectManagerID) {
        this.projectManagerID = projectManagerID;
    }

    public int getOverallAimID() {
        return overallAimID;
    }

    public void setOverallAimID(int overallAimID) {
        this.overallAimID = overallAimID;
    }

    public int getSpecificAimID() {
        return specificAimID;
    }

    public void setSpecificAimID(int specificAimID) {
        this.specificAimID = specificAimID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(int projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
