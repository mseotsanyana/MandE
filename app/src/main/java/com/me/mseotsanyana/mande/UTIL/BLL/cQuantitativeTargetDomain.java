package com.me.mseotsanyana.mande.UTIL.BLL;

import java.util.Date;

public class cQuantitativeTargetDomain {
    private int quantitativeID;
    private int indicatorID;
    private int ownerID;
    private int quantitativeTarget;
    private Date createDate;

    public int getQuantitativeID() {
        return quantitativeID;
    }

    public void setQuantitativeID(int quantitativeID) {
        this.quantitativeID = quantitativeID;
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

    public int getQuantitativeTarget() {
        return quantitativeTarget;
    }

    public void setQuantitativeTarget(int quantitativeTarget) {
        this.quantitativeTarget = quantitativeTarget;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}