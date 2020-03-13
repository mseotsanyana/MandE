package com.me.mseotsanyana.mande.UTIL.DAL;

import java.util.Date;

public class cRiskManagementModel {
	private int riskID;
	private int timeID;
	private int ownerID;
	private int likelihood;
	private int impact;
    private String comment;
	private Date createDate;

	public int getRiskID() {
		return riskID;
	}

	public void setRiskID(int riskID) {
		this.riskID = riskID;
	}

    public int getTimeID() {
        return timeID;
    }

    public void setTimeID(int timeID) {
        this.timeID = timeID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getLikelihood() {
        return likelihood;
    }

    public void setLikelihood(int likelihood) {
        this.likelihood = likelihood;
    }

    public int getImpact() {
        return impact;
    }

    public void setImpact(int impact) {
        this.impact = impact;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
