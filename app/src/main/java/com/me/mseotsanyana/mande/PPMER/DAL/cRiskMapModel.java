package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/07/28.
 */

public class cRiskMapModel {
    private int riskMapID;
    private int riskRegisterID;
    private int ownerID;
    private String riskMapName;
    private String riskMapDescription;
    private int lowerLimit;
    private int upperLimit;
    private Date createDate;

    public int getRiskMapID() {
        return riskMapID;
    }

    public void setRiskMapID(int riskMapID) {
        this.riskMapID = riskMapID;
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

    public String getRiskMapName() {
        return riskMapName;
    }

    public void setRiskMapName(String riskMapName) {
        this.riskMapName = riskMapName;
    }

    public String getRiskMapDescription() {
        return riskMapDescription;
    }

    public void setRiskMapDescription(String riskMapDescription) {
        this.riskMapDescription = riskMapDescription;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
