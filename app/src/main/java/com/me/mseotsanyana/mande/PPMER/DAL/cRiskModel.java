package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.Date;

public class cRiskModel {
	private int riskID;
	private int riskRegisterID;
	private int ownerID;
	private int ownershipID;
	private String riskName;
	private String riskDescription;
	private int status;
	private int likelihood;
	private int impact;
	private Date createDate;

	public int getRiskID() {
		return riskID;
	}

	public void setRiskID(int riskID) {
		this.riskID = riskID;
	}

    public int getRiskRegisterID() {
        return riskRegisterID;
    }

    public void setRiskRegisterID(int riskRegisterID) {
        this.riskRegisterID = riskRegisterID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getOwnershipID() {
        return ownershipID;
    }

    public void setOwnershipID(int ownershipID) {
        this.ownershipID = ownershipID;
    }

	public String getRiskName() {
		return riskName;
	}

	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}

	public String getRiskDescription() {
		return riskDescription;
	}

	public void setRiskDescription(String riskDescription) {
		this.riskDescription = riskDescription;
	}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
