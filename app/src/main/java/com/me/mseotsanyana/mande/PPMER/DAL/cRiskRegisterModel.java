package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/07/28.
 */

public class cRiskRegisterModel {
    private int riskRegisterID;
    private int projectID;
    private int ownerID;
    private String riskRegisterName;
    private String riskRegisterDescription;
    private Date createDate;

    public int getRiskRegisterID() {
        return riskRegisterID;
    }

    public void setRiskRegisterID(int riskRegisterID) {
        this.riskRegisterID = riskRegisterID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
