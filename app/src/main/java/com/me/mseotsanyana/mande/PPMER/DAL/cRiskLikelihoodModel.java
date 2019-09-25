package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/07/28.
 */

public class cRiskLikelihoodModel {
    private int riskLikelihoodID;
    private int ownerID;
    private String riskRegisterName;
    private String riskRegisterDescription;
    private int value;
    private Date createDate;

    public int getRiskLikelihoodID() {
        return riskLikelihoodID;
    }

    public void setRiskLikelihoodID(int riskLikelihoodID) {
        this.riskLikelihoodID = riskLikelihoodID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getRiskRegisterName() {
        return riskRegisterName;
    }

    public void setRiskRegisterName(String riskRegisterName) {
        this.riskRegisterName = riskRegisterName;
    }

    public String getRiskRegisterDescription() {
        return riskRegisterDescription;
    }

    public void setRiskRegisterDescription(String riskRegisterDescription) {
        this.riskRegisterDescription = riskRegisterDescription;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
