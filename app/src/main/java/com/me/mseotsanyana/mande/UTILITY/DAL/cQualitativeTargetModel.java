package com.me.mseotsanyana.mande.UTILITY.DAL;

import java.util.Date;

public class cQualitativeTargetModel {
    private int qualitativeID;
	private int indicatorID;
    private int ownerID;
    private String qualitativeTarget;
    private Date createDate;

    public int getQualitativeID() {
        return qualitativeID;
    }

    public void setQualitativeID(int qualitativeID) {
        this.qualitativeID = qualitativeID;
    }

    public int getIndicatorID() {
        return indicatorID;
    }

    public void setIndicatorID(int indicatorID) {
        this.indicatorID = indicatorID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getQualitativeTarget() {
        return qualitativeTarget;
    }

    public void setQualitativeTarget(String qualitativeTarget) {
        this.qualitativeTarget = qualitativeTarget;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
