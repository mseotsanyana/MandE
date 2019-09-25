package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.Date;

public class cRiskConsequenceModel {
    private int riskConsequenceID;
	private int riskID;
	private int ownerID;
	private String riskName;
	private String riskDescription;
	private Date createDate;

    public int getRiskConsequenceID() {
        return riskConsequenceID;
    }

    public void setRiskConsequenceID(int riskConsequenceID) {
        this.riskConsequenceID = riskConsequenceID;
    }

    public int getRiskID() {
        return riskID;
    }

    public void setRiskID(int riskID) {
        this.riskID = riskID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
